package com.mumayi.cameraphotodemo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.mumayi.cameraphotodemo.R;

import java.util.ArrayList;

/**
 * author: zzc-1990
 * created on: 2016/12/23 19:10
 */
public class MyGridAdapter extends BaseAdapter {


    private final ArrayList<Bitmap> list;
    private final Context context;

    public MyGridAdapter(ArrayList<Bitmap> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(R.layout.gridview_item, null);
            ImageView la_iv = (ImageView) convertView.findViewById(R.id.la_iv);

            la_iv.setImageBitmap(list.get(position));
        }


        return convertView;
    }
}
