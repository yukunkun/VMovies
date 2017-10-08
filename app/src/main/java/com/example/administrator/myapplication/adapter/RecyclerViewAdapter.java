package com.example.administrator.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.Banner;
import com.example.administrator.myapplication.bean.NewMessage;
import com.example.administrator.myapplication.bean.SetOnLoadMore;
import com.example.administrator.myapplication.utils.ActivityUtils;
import com.example.administrator.myapplication.videoplay.BehindActvity;
import com.example.administrator.myapplication.videoplay.VideoPlayActivity;
import com.example.administrator.myapplication.viewpageradapter.BannerViewPagerAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/5/8.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    int Type_1=0;
    int page;
    private final  String PATH="http://app.vmoiver.com/apiv3/post/getPostByTab";
    Context context;
    int i = 0;
    static boolean isShow;
    ArrayList<Banner> img;
    ArrayList<NewMessage> newMessages;
    boolean flag;//控制线程的数量
    public RecyclerViewAdapter(Context context, ArrayList<Banner> img, ArrayList<NewMessage> newMessages, boolean flag){
        this.context=context;
        this.img=img;
        this.newMessages=newMessages;
        this.flag=flag;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        View view = null;
        if (viewType == 0) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewpager_item, null);
            holder = new Holder1(view, context, viewType, newMessages);
            return holder;
        }

        if (viewType != 0) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, null);
            holder = new Holder2(view, context, viewType, newMessages);
            return holder;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof Holder1){
            //Banner运动起来
            if(flag) {
                if(isShow||img.size()==0){
                    return;
                }
                isShow=true;
                ((Holder1) holder).mConvenientBanner.setPages(
                        new CBViewHolderCreator<LocalImageHolderView>() {
                            @Override
                            public LocalImageHolderView createHolder() {
                                return new LocalImageHolderView();
                            }
                        }, img)
                        .startTurning(3000)
                        //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                        .setPageIndicator(new int[]{R.drawable.shape_banner_unchecked, R.drawable.shape_banner_checked})
                        //设置指示器的方向
                        .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                        .setCanLoop(true)
                        ;
                ((Holder1) holder).mConvenientBanner.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        if(img.get(position).getDescription().startsWith("http")){
                            Intent intent=new Intent(context, BehindActvity.class);
                            intent.putExtra("request",img.get(position).getDescription());
                            intent.putExtra("postid","");
                            context.startActivity(intent);
                        }else {
                            Toast.makeText(context, "没有链接", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
        if(holder instanceof Holder2){
            if(newMessages.size()!=0) {
                Glide.with(context).load(newMessages.get(position - 1).getImage()).into(((Holder2) holder).imageView);
                ((Holder2) holder).textViewTitle.setText(newMessages.get(position - 1).getTitle());
                ((Holder2) holder).textViewClass.setText(newMessages.get(position - 1).getCatename());
                String duration = newMessages.get(position - 1).getDuration();
                int time = Integer.valueOf(duration);
                int min = (int) time / 60;
                int sec = (int) time % 60;
                ((Holder2) holder).textViewTime.setText(min + "'" + sec + "''");
                ((Holder2) holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityUtils.startVideoPlayActivity(context,newMessages.get(position - 1).getImage(),
                                newMessages.get(position-1).getPostid(),newMessages.get(position-1).getRequest_url());

                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        if(newMessages==null){
            return 1;
        }
        return 1+newMessages.size();
    }
    @Override
    public int getItemViewType(int position) {
        switch(position){
            case 0:
                return Type_1;
           default:
                return position;
        }
    }


class Holder1 extends RecyclerView.ViewHolder {
    ConvenientBanner mConvenientBanner;
    public Holder1(View itemView, final Context context, final int position, final ArrayList<NewMessage> newMessage) {
        super(itemView);
        mConvenientBanner= (ConvenientBanner) itemView.findViewById(R.id.conventionbanner);
    }
}
    class Holder2 extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewTitle,textViewClass,textViewTime;

        public Holder2(View itemView, final Context context, final int position, final ArrayList<NewMessage> newMessage) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_recyclerview);
            textViewTitle= (TextView) itemView.findViewById(R.id.img_title);
            textViewClass= (TextView) itemView.findViewById(R.id.img_class);
            textViewTime= (TextView) itemView.findViewById(R.id.img_video_time);
        }
    }
    public class LocalImageHolderView implements Holder<Banner> {
        private ImageView imageView;
        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, Banner data) {
            Glide.with(context).load(data.getImage()).into(imageView);
        }
    }
}
