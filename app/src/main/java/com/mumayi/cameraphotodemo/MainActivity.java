package com.mumayi.cameraphotodemo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.mumayi.cameraphotodemo.adapter.MyGridAdapter;
import com.mumayi.cameraphotodemo.utils.ImageUtil;
import com.mumayi.cameraphotodemo.view.RoundedImageView;

import java.util.ArrayList;

/**
 * UI 方面的Demo练习
 */

public class MainActivity extends Activity implements View.OnClickListener {

    private Button bt_submit;
    private RoundedImageView iv_submit_avatar;
    private ImageView iv_submit_pic;
    private GridView rl_gridview;
    private ArrayList<Bitmap> list = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


    }

    private void initView() {

        iv_submit_avatar = (RoundedImageView) findViewById(R.id.iv_submit_avatar);
        EditText ed_submit_nickname = (EditText) findViewById(R.id.ed_submit_nickname);
        EditText ed_submit_content = (EditText) findViewById(R.id.ed_submit_content);
        iv_submit_pic = (ImageView) findViewById(R.id.iv_submit_pic);
        bt_submit = (Button) findViewById(R.id.bt_submit);
        rl_gridview = (GridView) findViewById(R.id.rl_gridview);
        iv_submit_pic.setOnClickListener(this);
        iv_submit_avatar.setOnClickListener(this);
        bt_submit.setOnClickListener(this);
        setRoundeImageView();

    }

    /**
     * 头像回显
     **/
    private void setRoundeImageView() {

        Bitmap iv_submit_avatar_bitmap = ImageUtil.getInstance().getBitmap("iv_submit_avatar");
        Log.e("tag", "有啥---" + iv_submit_avatar);
        iv_submit_avatar.setImageBitmap(iv_submit_avatar_bitmap);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.iv_submit_avatar://点击头像
                replaceHeadPhoto(100);
                break;
            case R.id.iv_submit_pic:
                replaceHeadPhoto(200);
                break;
            case R.id.bt_submit:
                Toast.makeText(this, "未实现~~", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    private void replaceHeadPhoto(int code) {

        Intent intent = new Intent(Intent.ACTION_PICK);//相册
        intent.setType("image/*");//类型很重要啊~~~
        startActivityForResult(intent, code);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        try {
            Bitmap bitmap = ImageUtil.getInstance().decodeBitmapFromFile(getRealPathFromURI(data.getData()), 500, 500);

            if (requestCode == 100) {

                iv_submit_avatar.setImageBitmap(bitmap);

                //保存头像到本地
                ImageUtil.getInstance().saveBitmap("iv_submit_avatar", bitmap);

            } else if (requestCode == 200) {

                list.add(bitmap);
                if (list.size() != 0 && list.size() <= 6) {

                    rl_gridview.setAdapter(new MyGridAdapter(list, this));
                } else {
                    Toast.makeText(this, "最多添加6张哦~~", Toast.LENGTH_SHORT).show();
                }

                for (int i = 0; i < list.size(); i++) {
                    ImageUtil.getInstance().saveBitmap("iv_submit_pic" + i, bitmap);

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }
}
