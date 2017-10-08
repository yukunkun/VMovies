package com.example.administrator.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.adapter.RecyclerViewAdapter;
import com.example.administrator.myapplication.bean.Banner;
import com.example.administrator.myapplication.bean.NewMessage;
import com.example.administrator.myapplication.bean.SetOnLoadMore;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/5/7.
 */
public class NewFragment extends Fragment {
    PullLoadMoreRecyclerView recyclerView;
    private int page=1;
    ArrayList<Banner> img=new ArrayList<>();

    RecyclerViewAdapter adapter;
    boolean flag=true;
    ArrayList<NewMessage> newMessages =new ArrayList<NewMessage>();
    private  final String  URL="http://app.vmoiver.com/apiv3/index/getBanner";
    private final  String PATH="http://app.vmoiver.com/apiv3/post/getPostByTab";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_fragment_item, null);
        recyclerView = (PullLoadMoreRecyclerView) view.findViewById(R.id.refresh);
        getBanner();
        setAdapters();
        setListener();
        return view;
    }

    private void getBanner() {
        OkHttpUtils.post()
                .url(URL)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(getContext(), "网络连接失败", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    String status = object.optString("status");
                    if(status.equals("0")){
                        JSONArray data = object.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            Banner banner=new Banner();
                            JSONObject jsonObject = data.optJSONObject(i);
                            banner.setImage(jsonObject.optString("image"));
                            JSONObject extra_data = jsonObject.optJSONObject("extra_data");
                            String app_banner_param = extra_data.optString("app_banner_param");
                            banner.setDescription(app_banner_param);
                            img.add(banner);
                        }
                        adapter.notifyDataSetChanged();
                    }else{
                        Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getMessage();
            }
        });
    }


    private void setAdapters() {
        recyclerView.setLinearLayout();
        adapter=new RecyclerViewAdapter(getContext(),img,newMessages,flag);
        recyclerView.setAdapter(adapter);

    }

    private void getMessage() {

        OkHttpUtils.post()
                .url(PATH)
                .addParams("p",page+"")
                .addParams("size","10")
                .addParams("tab","lates")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(getContext(), "网络连接失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                Log.i("-----res",response);
                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                    String status = object.optString("status");
                    if(status.equals("0")){
                        JSONArray data = object.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            NewMessage message=new NewMessage();
                            JSONObject jsonObject = data.optJSONObject(i);
                            message.setPostid(jsonObject.optString("postid"));
                            message.setTitle(jsonObject.optString("title"));
                            message.setPid(jsonObject.optString("pid"));
                            message.setImage(jsonObject.optString("image"));
                            message.setRating(jsonObject.optString("rating"));
                            message.setDuration(jsonObject.optString("duration"));
                            message.setPublish_time(jsonObject.optString("publish_time"));
                            message.setLike_num(jsonObject.optString("like_num"));
                            message.setShare_num(jsonObject.optString("share_num"));
                            JSONArray cates=jsonObject.optJSONArray("cates");
                            for (int k = 0; k <cates.length() ; k++) {
                                JSONObject object1 = cates.optJSONObject(k);
                                String cateid = object1.optString("cateid");
                                String catename = object1.optString("catename");
                                message.setCateid(cateid);
                                message.setCatename(catename);
                            }
                            message.setRequest_url(jsonObject.optString("request_url"));
                            newMessages.add(message);
                            flag=false;
                            adapter.notifyDataSetChanged();
                            recyclerView.setPullLoadMoreCompleted();
                        }
                    }else{
                        Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
            }
    private void setListener() {
        recyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                newMessages.clear();
                getMessage();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onLoadMore() {
                page++;
                getMessage();
                adapter.notifyDataSetChanged();

            }
        });
    }
}
