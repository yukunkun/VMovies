package com.example.administrator.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.NewMessage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/9.
 */
public class PinDaoAdapter extends BaseAdapter {
    ArrayList<NewMessage> newMessages;
    Context context;
    public PinDaoAdapter(Context context,ArrayList<NewMessage> newMessages){
        this.context=context;
        this.newMessages=newMessages;
    }

    @Override
    public int getCount() {
        return newMessages.size();
    }

    @Override
    public Object getItem(int i) {
        return newMessages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        MyViewHolder holder=null;
        if(view==null){
            holder=new MyViewHolder();
            view=LayoutInflater.from(context).inflate(R.layout.list_item_,viewGroup,false);
            holder.imageView = (ImageView) view.findViewById(R.id.image_recyclerview);
            holder.textViewTitle= (TextView) view.findViewById(R.id.img_title);
            holder.textViewClass= (TextView) view.findViewById(R.id.img_class);
            holder.textViewTime= (TextView) view.findViewById(R.id.img_video_time);
            view.setTag(holder);
        }else{
            holder= (MyViewHolder) view.getTag();
        }
        Glide.with(context).load(newMessages.get(position).getImage()).into(holder.imageView);
        holder.textViewTitle.setText(newMessages.get(position).getTitle());
        holder.textViewClass.setText(newMessages.get(position).getCatename());
        String duration = newMessages.get(position).getDuration();
        int time=Integer.valueOf(duration);
        int min=(int)time/60;
        int sec=time%60;
        holder.textViewTime.setText(min+"'"+sec+"''");
        return view;
    }
 private class MyViewHolder{
        ImageView imageView;
        TextView textViewTitle,textViewTime,textViewClass;
    }
}
