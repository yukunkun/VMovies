package com.example.administrator.myapplication.videoplay;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.NewMessage;
import com.google.gson.JsonObject;
import com.jude.swipbackhelper.SwipeBackHelper;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import okhttp3.Call;

public class VideoPlayActivity extends Activity {
    WebView webView;
//    JCVideoPlayerStandard jcVideoPlayer;
    String videoUri;
    String postId;
    String request;
    String title;
    private ImageView mImageView;
    private TextView mTextView;
    private String mImage;
    private ImageView mImageViewBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBackHelper.onCreate(this);
        setContentView(R.layout.activity_video_play);
        Intent intent = getIntent();
        postId=intent.getStringExtra("postId");
        request=intent.getStringExtra("request");
        mImage = intent.getStringExtra("image");
        init();
        setlistener();
        OkHttpUtil();
        Glide.with(VideoPlayActivity.this).load(mImage).into(mImageView);
    }

    private void setlistener() {
        mImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init() {
//        jcVideoPlayer= (JCVideoPlayerStandard) findViewById(R.id.jiecao);
        webView= (WebView) findViewById(R.id.play_webview);
        mImageView = (ImageView) findViewById(R.id.iv_cover);
        mTextView = (TextView) findViewById(R.id.tv_title);
        mImageViewBack = (ImageView) findViewById(R.id.tv_back);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        //设置自适应屏幕，两者合用
        settings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //缩放操作
        settings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        settings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        settings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //其他细节操作
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        settings.setAllowFileAccess(true); //设置可以访问文件
        settings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        settings.setLoadsImagesAutomatically(true); //支持自动加载图片
        settings.setDefaultTextEncodingName("utf-8");//设置编码格式
        //设置客服端
        webView.setWebViewClient(new WebViewClient());
        //定义客服端
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(request);
    }

    private void OkHttpUtil() {
        OkHttpUtils.post()
                .url("https://app.vmoiver.com/apiv3/post/view?")
                .addParams("postid",postId)
                .build().execute(new StringCallback() {

            private String mCover;

            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(VideoPlayActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onResponse(String response) {
                Log.i("------",response);
                try {
                    JSONObject object=new JSONObject(response);
                    JSONObject object1 = object.optJSONObject("data");
                    JSONObject content = object1.getJSONObject("content");
                    JSONArray video = content.optJSONArray("video");
                    if(video!=null){
                        for (int i = 0; i < video.length(); i++) {
                            JSONObject object2 = video.optJSONObject(i);
                            title=object2.optString("title");
                            videoUri=object2.optString("qiniu_url");
                            mCover = object2.optString("image");
                        }
//                        jcVideoPlayer.setUp(videoUri,title);
                        Glide.with(VideoPlayActivity.this).load(mImage).into(mImageView);
                        mTextView.setText(title);
//                        jcVideoPlayer.ivThumb.setThumbInCustomProject("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onPause() {
//        jcVideoPlayer.releaseAllVideos();
        super.onPause();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        SwipeBackHelper.onPostCreate(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SwipeBackHelper.onDestroy(this);
    }
}