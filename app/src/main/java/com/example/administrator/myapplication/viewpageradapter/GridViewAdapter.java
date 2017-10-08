package com.example.administrator.myapplication.viewpageradapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.PinDao;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/8.
 */
public class GridViewAdapter extends BaseAdapter {
    Context context;
    ArrayList<PinDao> pinDaos;

    public GridViewAdapter(Context context, ArrayList<PinDao> pinDaos) {
        this.context = context;
        this.pinDaos = pinDaos;
    }

    @Override
    public int getCount() {
        return pinDaos.size();
    }

    @Override
    public Object getItem(int i) {
        return pinDaos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder=new ViewHolder();
            view=LayoutInflater.from(context).inflate(R.layout.grid_view_item ,viewGroup,false);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.grid_view_img);
            viewHolder.textView= (TextView) view.findViewById(R.id.grid_view_text);
            view.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) view.getTag();
        }
        if(!TextUtils.isEmpty(pinDaos.get(position).getIcon())){
            Glide.with(context).load(pinDaos.get(position).getIcon()).error(R.drawable.love).into(viewHolder.imageView);
        }
            viewHolder.textView.setText("#"+pinDaos.get(position).getCatename()+"#");
        return view;
    }

    static class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}