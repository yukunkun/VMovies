package com.example.administrator.myapplication.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.SeriseListView;
import com.example.administrator.myapplication.utils.ActivityUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/15.
 */
public class MyListViewAdapter extends BaseAdapter {
    ArrayList<SeriseListView> listViews;
    Context context;
    public  MyListViewAdapter(Context context,ArrayList<SeriseListView> listViews){
        this.context=context;
        this.listViews=listViews;
    }

    @Override
    public int getCount() {
        return listViews.size();
    }

    @Override
    public Object getItem(int i) {
        return listViews.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View currentView, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if(currentView==null){
            viewHolder=new ViewHolder();
            currentView=LayoutInflater.from(context).inflate(R.layout.mylistview_item,viewGroup,false);
            viewHolder.imageView= (ImageView) currentView.findViewById(R.id.mylistview_img);
            viewHolder.textViewTime= (TextView) currentView.findViewById(R.id.mylistview_time);
            viewHolder.textViewTitle= (TextView) currentView.findViewById(R.id.mylistview_title);
            viewHolder.textViewUpdate= (TextView) currentView.findViewById(R.id.mylistview_updatetime);
            currentView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) currentView.getTag();
        }
        if(!TextUtils.isEmpty(listViews.get(position).getThumbnail())){
            Glide.with(context).load(listViews.get(position).getThumbnail()).into(viewHolder.imageView);
            String duration = listViews.get(position).getDuration();
            int min=Integer.valueOf(duration)/60;
            int sec=Integer.valueOf(duration)%60;
            viewHolder.textViewTime.setText(min+"'"+sec+"''");
            viewHolder.textViewTitle.setText("第"+listViews.get(position).getNumber()+"集:"+listViews.get(position).getTitle());
            viewHolder.textViewUpdate.setText("更新时间:"+listViews.get(position).getAddtime());
        }
        return currentView;
    }
    private class ViewHolder{
        ImageView imageView;
        TextView textViewTime,textViewTitle,textViewUpdate;
    }
}
