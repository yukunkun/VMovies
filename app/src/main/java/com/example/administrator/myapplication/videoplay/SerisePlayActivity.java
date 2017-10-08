package com.example.administrator.myapplication.videoplay;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.History;
import com.example.administrator.myapplication.bean.SeriseListView;
import com.example.administrator.myapplication.bean.SerisePlay;
import com.example.administrator.myapplication.fragment.SeriesVideoFragment;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

//import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import cn.jzvd.JZVideoPlayerStandard;
import okhttp3.Call;

public class SerisePlayActivity extends AppCompatActivity {
//    JCVideoPlayerStandard jcPlayer;
    TextView textViewNum,textViewName,textViewUpdate,textViewJi,textViewClass,textViewContent;
    TabLayout tabLayout;
    final String PATH="http://app.vmoiver.com/apiv3/series/view";
    final String VIDEPOPLAY="http://app.vmoiver.com/apiv3/series/getVideo";
    String id;
    EditText mEditText;
    ArrayList<SeriseListView> listViews=new ArrayList<>();
    ArrayList<Fragment> fragments=new ArrayList<>();
    SerisePlay serisePlay=new SerisePlay();
    ArrayList<String> strings=new ArrayList<>();
    private JZVideoPlayerStandard mJzVideoPlayerStandard;
    private String mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serise_play);
        Intent intent = getIntent();
        id = intent.getStringExtra("seriesId");
        mImage = intent.getStringExtra("image");
        init();
        getMessage();

    }
    private void init() {
        mJzVideoPlayerStandard = (JZVideoPlayerStandard) findViewById(R.id.jiecaos);
        textViewNum= (TextView) findViewById(R.id.video_num);
        textViewName= (TextView) findViewById(R.id.video_name);
        textViewUpdate= (TextView) findViewById(R.id.video_update);
        textViewJi= (TextView) findViewById(R.id.video_play_num);
        textViewClass= (TextView) findViewById(R.id.video_class);
        textViewContent= (TextView) findViewById(R.id.video_content);
        tabLayout= (TabLayout) findViewById(R.id.video_tablayout);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mEditText= (EditText) findViewById(R.id.edit_content);
        mEditText.clearFocus();
        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //隐藏软键盘
                Toast.makeText(SerisePlayActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                mEditText.setText("");
                InputMethodManager imm = (InputMethodManager) getApplication().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mEditText.getWindowToken() , 0);
                return true;
            }});
    }

    private void getMessage() {
        OkHttpUtils.post()
                .url(PATH)
                .addParams("seriesid",id)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e){
                Toast.makeText(SerisePlayActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    String status = object.getString("status");
                    if(status.equals("0")){
                        JSONObject data = object.optJSONObject("data");
                        serisePlay.setSeriesid(data.optString("seriesid"));
                        serisePlay.setTitle(data.optString("title"));
                        serisePlay.setImage(data.optString("image"));
                        serisePlay.setContent(data.optString("content"));
                        serisePlay.setWeekly(data.optString("weekly"));
                        serisePlay.setCount_follow(data.getString("count_follow"));
                        serisePlay.setTag_name(data.optString("tag_name"));
                        serisePlay.setUpdate_to(data.optString("update_to"));
                        serisePlay.setPost_num_per_seg("post_num_per_seg");
                        JSONArray posts = data.optJSONArray("posts");
                        for (int i = 0; i < posts.length(); i++) {
                            JSONObject object1 = posts.optJSONObject(i);
                            strings.add(object1.optString("from_to"));
                            JSONArray list = object1.optJSONArray("list");
                            for (int j = 0; j < list.length(); j++) {
                                SeriseListView listViewObj=new SeriseListView();
                                JSONObject object2 = list.optJSONObject(j);
                                listViewObj.setSeries_postid(object2.optString("series_postid"));
                                listViewObj.setNumber(object2.optString("number"));
                                listViewObj.setTitle(object2.optString("title"));
                                listViewObj.setAddtime(object2.optString("addtime"));
                                listViewObj.setDuration(object2.optString("duration"));
                                listViewObj.setThumbnail(object2.optString("thumbnail"));
                                listViews.add(listViewObj);
                            }
                        }
                        setContent(serisePlay);
                        setTabLayout(strings);
                        setFragment(strings,listViews);
                        playVideoMessage( listViews.get(0).getSeries_postid());
                    }else{
                        Toast.makeText(SerisePlayActivity.this, "数据下载失败", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }



    private void setContent(SerisePlay serisePlay) {
        textViewNum.setText("第"+serisePlay.getUpdate_to()+"集");
        textViewName.setText(serisePlay.getTitle());
        textViewUpdate.setText("更新:"+serisePlay.getWeekly());
        textViewJi.setText("集数:共"+serisePlay.getUpdate_to()+"集");
        textViewClass.setText("类型:"+serisePlay.getTag_name());
        textViewContent.setText(serisePlay.getContent());

    }
    private void setTabLayout(ArrayList<String> strings) {
        for (int i = 0; i < strings.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(strings.get(i)));
        }
    }

    private void setFragment(ArrayList<String> strings, ArrayList<SeriseListView> listViews) {
        for (int i = 0; i <strings.size() ; i++) {
            SeriesVideoFragment videoFragment=new SeriesVideoFragment();
            fragments.add(videoFragment);
            Bundle data=new Bundle();
            data.putSerializable("listViews",listViews);
            videoFragment.setArguments(data);
        }
        getSupportFragmentManager().beginTransaction()
                .add(R.id.video_frament_contains,fragments.get(0))
                .commit();
    }

    private void playVideoMessage(String series_postid) {
        OkHttpUtils.post()
                .url(VIDEPOPLAY)
                .addParams("series_postid",series_postid)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(SerisePlayActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                JSONObject object= null;
                try {
                    object = new JSONObject(response);
                    String status = object.getString("status");
                    if(status.equals("0")){
                        JSONObject data = object.optJSONObject("data");
                        String title = data.optString("title");
                        String url = data.optString("qiniu_url");
                        String thumbnail=data.optString("thumbnail");
                        mJzVideoPlayerStandard.setUp(url,JZVideoPlayerStandard.SCREEN_LAYOUT_NORMAL,title);
                        mJzVideoPlayerStandard.thumbImageView.setImageURI(Uri.parse(mImage));
//                        jcPlayer.setUp(url,title);
                        //存储数据库
                        History history=new History();
                        history.setImage(mImage);
                        history.setSerId(id);
                        history.setTitle(serisePlay.getTitle());
                        history.setContent(title);
                        history.save();
                    }else{
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        overridePendingTransition(0,R.anim.down_hint);
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onPause() {
        super.onPause();
        mJzVideoPlayerStandard.releaseAllVideos();
    }
}
