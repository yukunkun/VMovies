package com.example.administrator.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.History;
import com.example.administrator.myapplication.bean.NewMessage;
import com.example.administrator.myapplication.videoplay.SerisePlayActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/12.
 */
public class HistoryListViewAdapter extends BaseAdapter {
    Context context;
    ArrayList<History> arrayList;


    public HistoryListViewAdapter(Context context, ArrayList<History> arrayList){
        this.context=context;
        this.arrayList=arrayList;
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=LayoutInflater.from(context).inflate(R.layout.histort_listview_item,null);
            viewHolder.imageView=(ImageView) convertView.findViewById(R.id.behind_img);
            viewHolder.textViewContent=(TextView) convertView.findViewById(R.id.behind_content);
            viewHolder.mTextViewTitle=(TextView) convertView.findViewById(R.id.behind_like);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
        }
        if(!TextUtils.isEmpty(arrayList.get(position).getImage())){
            Glide.with(context).load(arrayList.get(position).getImage()).into(viewHolder.imageView);
            viewHolder.mTextViewTitle.setText(arrayList.get(position).getContent());
            viewHolder.textViewContent.setText(arrayList.get(position).getTitle());
        }

        return convertView;
    }

    class ViewHolder{
        ImageView imageView;
        TextView textViewContent,mTextViewTitle;
    }
}
