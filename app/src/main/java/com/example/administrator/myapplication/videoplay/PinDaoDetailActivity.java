package com.example.administrator.myapplication.videoplay;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.adapter.PinDaoAdapter;
import com.example.administrator.myapplication.bean.NewMessage;
import com.example.administrator.myapplication.utils.ActivityUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;

public class PinDaoDetailActivity extends AppCompatActivity {
    ImageView imageViewBack;
    TextView textViewTitle;
    String cateId;
    String cateName;
    private int page=1;
    ListView listView;
    PinDaoAdapter pinDaoAdapter;
    private PullToRefreshListView refreshListView;
    ArrayList<NewMessage> newMessages=new ArrayList<>();

    final  String PATH="http://app.vmoiver.com/apiv3/post/getPostInCate";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_dao_detail);
        Intent intent = getIntent();
        cateId = intent.getStringExtra("cateId");
        cateName=intent.getStringExtra("cateName");
        initView();
        getMessage();
        setListener();
        setAdapter();
    }


    //初始化View
    private void initView() {
        imageViewBack= (ImageView) findViewById(R.id.Pindao_img_back);
        textViewTitle= (TextView) findViewById(R.id.pindao_detail_text);
        //设置Title
        textViewTitle.setText(cateName);
        refreshListView= (PullToRefreshListView) findViewById(R.id.pull_to_refresh_list);
        listView=refreshListView.getRefreshableView();
        listView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        listView.setDividerHeight(0);
        listView.setOnItemClickListener(new List());
    }
    //点击的监听
    private void setListener() {
        imageViewBack.setOnClickListener(new Listener());
        refreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        //PullToRefreshListView的刷新的监听
        refreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page=1;
                newMessages.clear();
                getMessage();


            }
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                getMessage();

            }
        });
    }
        //各类点击事件
        private class Listener implements View.OnClickListener {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.Pindao_img_back:
                        finish();
                        break;
                }
            }
        }

    //网络获取数据
    private void getMessage() {
        OkHttpUtils.post()
                .url(PATH)
                .addParams("p",page+"")
                .addParams("size",10+"")
                .addParams("cateid",cateId)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(PinDaoDetailActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object=new JSONObject(response);
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
                            pinDaoAdapter.notifyDataSetChanged();
                            refreshListView.onRefreshComplete();
                        }
                    }else{
                        Toast.makeText(PinDaoDetailActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void setAdapter() {
        pinDaoAdapter=new PinDaoAdapter(getApplicationContext(),newMessages);
        listView.setAdapter(pinDaoAdapter);
    }
    class List implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            ActivityUtils.startVideoPlayActivity(PinDaoDetailActivity.this,newMessages.get(position - 1).getImage(),
                    newMessages.get(position-1).getPostid(),newMessages.get(position-1).getRequest_url());
//            Intent intent=new Intent(getApplicationContext(), VideoPlayActivity.class);
//            intent.putExtra("postId",newMessages.get(position-1).getPostid());
//            intent.putExtra("request",newMessages.get(position-1).getRequest_url());
//            intent.putExtra("position",position);
//            startActivity(intent);
            overridePendingTransition(R.anim.down_show,R.anim.down_hint);
        }
    }
}
