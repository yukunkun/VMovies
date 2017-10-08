package com.example.administrator.myapplication.fragment;

import android.content.Context;
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
import com.example.administrator.myapplication.adapter.BehindListViewdapter;
import com.example.administrator.myapplication.adapter.SeriseAdapter;
import com.example.administrator.myapplication.bean.NewMessage;
import com.example.administrator.myapplication.bean.SeriseInfo;
import com.example.administrator.myapplication.videoplay.BehindActvity;
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
 * Created by Administrator on 2016/5/12.
 */
public class BehindViewPagerFragment extends Fragment {
    PullToRefreshListView refreshListView;
    ListView listView;
    BehindListViewdapter adapter;
    final String PATH="https://app.vmovier.com/apiv3/backstage/getPostByCate";
    int page=1;
    ArrayList<NewMessage> arrayList;
    String Cateid;

    @Override
    public void onAttach(Context context) {
        Bundle data = getArguments();
        Cateid=data.getString("cateid");
        super.onAttach(context);
    }

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

    private void initView(View view) {
        refreshListView= (PullToRefreshListView) view.findViewById(R.id.serise_pull_to_refresh_listView);
        refreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        //被这句话搞死了
        listView=refreshListView.getRefreshableView();
    }
    //获取接口的数据
    private void getMessage() {
        Log.i("-----",page+"----"+Cateid);
        OkHttpUtils.post()
                .url(PATH)
                .addParams("p",page+"")
                .addParams("size",10+"")
                .addParams("cateid",Cateid+"")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Log.i("-----e",e.toString());
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
                            NewMessage newMessage=new NewMessage();
                            JSONObject object1 = data.optJSONObject(i);
                            newMessage.setPostid(object1.optString("postid"));
                            newMessage.setImage(object1.optString("image"));
                            newMessage.setTitle(object1.optString("title"));
                            newMessage.setDuration(object1.optString("duration"));
                            newMessage.setPublish_time(object1.optString("public_time"));
                            newMessage.setLike_num(object1.optString("like_num"));
                            newMessage.setShare_num(object1.optString("share_num"));
                            JSONArray cates = object1.optJSONArray("cates");
                            for (int j = 0; j < cates.length(); j++) {
                                JSONObject object2 = cates.optJSONObject(j);
                                newMessage.setCateid(object2.optString("cateid"));
                                newMessage.setCatename(object2.optString("catename"));
                            }
                            newMessage.setRequest_url(object1.optString("request_url"));
                            arrayList.add(newMessage);
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
        arrayList=new ArrayList<>();
        adapter=new BehindListViewdapter(getContext(),arrayList);
        listView.setAdapter(adapter);
    }

    private void setListener() {
        refreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page=1;
                arrayList.clear();
                getMessage();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                getMessage();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent=new Intent(getContext(), BehindActvity.class);
                intent.putExtra("request",arrayList.get(position-1).getRequest_url());
                intent.putExtra("postid",arrayList.get(position-1).getPostid());
                getContext().startActivity(intent);
            }
        });
    }

}
