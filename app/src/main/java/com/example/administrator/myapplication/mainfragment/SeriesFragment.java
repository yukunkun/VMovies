package com.example.administrator.myapplication.mainfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.adapter.SeriseAdapter;
import com.example.administrator.myapplication.bean.SeriseInfo;
import com.example.administrator.myapplication.videoplay.SerisePlayActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/5/11.
 */
public class SeriesFragment extends Fragment {
    PullToRefreshListView refreshListView;
    private String PATH="http://app.vmoiver.com/apiv3/series/getList";
    private int page=1;
    ArrayList<SeriseInfo>  seriseInfos=new ArrayList<>();
    ListView listView;
    SeriseAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.serise_fragment, null);
        initView(view);
        getMessage();
        setAdapter();
        setListener();
        return view;
    }

    //初始化View
    private void initView(View view) {
        refreshListView= (PullToRefreshListView) view.findViewById(R.id.serise_pull_to_refresh_listView);
        refreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        //被这句话搞死了
        listView=refreshListView.getRefreshableView();
    }

    //获取接口的数据
    private void getMessage() {
        OkHttpUtils.post()
                .url(PATH)
                .addParams("p",page+"")
                .addParams("size",10+"")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(getContext(), "网络连接失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    String status = object.optString("status");
                    if(status.equals("0")){
                        JSONArray data = object.optJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            SeriseInfo seriseInfo=new SeriseInfo();
                            JSONObject object1 = data.optJSONObject(i);
                            seriseInfo.setSeriesid(object1.optString("seriesid"));
                            seriseInfo.setTitle(object1.optString("title"));
                            seriseInfo.setImage(object1.optString("image"));
                            seriseInfo.setWeekly(object1.optString("weekly"));
                            seriseInfo.setContent(object1.optString("content"));
                            seriseInfo.setApp_image(object1.optString("app_image"));
                            seriseInfo.setUpdate_to(object1.optString("update_to"));
                            seriseInfo.setFollower_num(object1.optString("follower_num"));
                            seriseInfos.add(seriseInfo);
                        }
                        adapter.notifyDataSetChanged();
                        refreshListView.onRefreshComplete();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //listView的设置适配器
    private void setAdapter() {

        adapter=new SeriseAdapter(getContext(),seriseInfos);
        listView.setAdapter(adapter);
    }
    //各种监听
    private void setListener() {
        //listView的监听,控制每个Item的跳转
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent=new Intent(getContext(), SerisePlayActivity.class);
                intent.putExtra("seriesId",seriseInfos.get(position-1).getSeriesid());
                intent.putExtra("image",seriseInfos.get(position-1).getImage());
                getContext().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.down_show, 0);
            }
        });
        //refreshListView的监听,控制刷新
        refreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                seriseInfos.clear();
                getMessage();

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                    page++;
                    getMessage();
            }
        });
    }
}
