package com.example.administrator.myapplication.videoplay;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.administrator.myapplication.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

public class BehindActvity extends AppCompatActivity {
    String postid;
    WebView webView;
    String request;
    String share;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.behind_actvity);
        Intent intent = getIntent();
        request=intent.getStringExtra("request");
        postid=intent.getStringExtra("postid");
        webView= (WebView) findViewById(R.id.webView);
        init();
        getMessage();
    }

    private void init() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        //定义客服端
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

        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(request);
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {
                //加载完成
                findViewById(R.id.progress).setVisibility(View.GONE);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //加载开始
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                //加载失败
                findViewById(R.id.progress).setVisibility(View.GONE);
//                Toast.makeText(BehindActvity.this, "加载失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getMessage() {
        OkHttpUtils.post().url("http://app.vmoiver.com/apiv3/post/view")
                .addParams("postid",postid)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
//                Toast.makeText(getApplicationContext(),"网络连接失败",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    String status = object.optString("status");
                    if(status.equals("0")){
                        JSONObject data = object.optJSONObject("data");
                        JSONObject json = data.optJSONObject("share_link");
                        share = json.optString("qq");

                    }else{
//                        Toast.makeText(getApplicationContext(),"下载数据失败",Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }else{
            super.onBackPressed();
        }

    }

    public void back(View view) {
        finish();
    }

    public void share(View view) {
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        intent.putExtra(Intent.EXTRA_TEXT,share);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, "分享"));
    }
}
