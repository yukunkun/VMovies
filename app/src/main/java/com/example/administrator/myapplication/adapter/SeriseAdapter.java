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
import com.example.administrator.myapplication.bean.SeriseInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/11.
 */
public class SeriseAdapter extends BaseAdapter {
    ArrayList<SeriseInfo> seriseInfos;
    Context context;
    public SeriseAdapter(Context context,ArrayList<SeriseInfo> seriseInfos){
        this.context=context;
        this.seriseInfos=seriseInfos;
    }
    @Override
    public int getCount() {
        return seriseInfos.size();
    }

    @Override
    public Object getItem(int i) {
        return seriseInfos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View currentview, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(currentview==null){
            viewHolder=new ViewHolder();
            currentview = LayoutInflater.from(context).inflate(R.layout.serise_listview_item, parent, false);
            viewHolder.imageView= (ImageView) currentview.findViewById(R.id.serise_img);
            viewHolder.textViewtitle= (TextView) currentview.findViewById(R.id.serise_titlename);
            viewHolder.textViewOrder= (TextView) currentview.findViewById(R.id.serise_order);
            viewHolder.textViewNum= (TextView) currentview.findViewById(R.id.serise_num);
            viewHolder.textViewContent= (TextView) currentview.findViewById(R.id.serise_content);
            currentview.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) currentview.getTag();
        }
        if(!TextUtils.isEmpty(seriseInfos.get(position).getImage())) {
            Glide.with(context).load(seriseInfos.get(position).getImage()).into(viewHolder.imageView);
        }
        viewHolder.textViewtitle.setText(seriseInfos.get(position).getTitle());
        viewHolder.textViewNum.setText("已经更新至"+seriseInfos.get(position).getUpdate_to()+"集");
        viewHolder.textViewOrder.setText(seriseInfos.get(position).getFollower_num()+"人已订阅");
        viewHolder.textViewContent.setText(seriseInfos.get(position).getContent());
        return currentview;
    }

   static class ViewHolder{
       ImageView imageView;
       TextView textViewtitle,textViewNum,textViewOrder,textViewContent;
    }
}
