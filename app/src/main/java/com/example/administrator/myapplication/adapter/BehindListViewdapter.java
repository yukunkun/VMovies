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
import com.example.administrator.myapplication.bean.NewMessage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/12.
 */
public class BehindListViewdapter extends BaseAdapter {
    Context context;
    ArrayList<NewMessage> arrayList;


    public BehindListViewdapter(Context context,ArrayList<NewMessage> arrayList){
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=LayoutInflater.from(context).inflate(R.layout.behind_listview_item,null);
            viewHolder.imageView=(ImageView) convertView.findViewById(R.id.behind_img);
            viewHolder.textViewContent=(TextView) convertView.findViewById(R.id.behind_content);
            viewHolder.textViewLike=(TextView) convertView.findViewById(R.id.behind_like);
            viewHolder.textViewShare=(TextView) convertView.findViewById(R.id.behind_share);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
        }
        if(!TextUtils.isEmpty(arrayList.get(position).getImage())){
            Glide.with(context).load(arrayList.get(position).getImage()).into(viewHolder.imageView);
        }
        viewHolder.textViewContent.setText(arrayList.get(position).getTitle());
        viewHolder.textViewLike.setText(arrayList.get(position).getLike_num());
        viewHolder.textViewShare.setText(arrayList.get(position).getShare_num());
        return convertView;
    }

    class ViewHolder{
        ImageView imageView;
        TextView textViewContent,textViewLike,textViewShare;
    }
}
