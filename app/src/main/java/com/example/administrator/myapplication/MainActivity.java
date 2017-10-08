package com.example.administrator.myapplication;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication.mainfragment.BehindFragment;
import com.example.administrator.myapplication.mainfragment.MainFragment;
import com.example.administrator.myapplication.mainfragment.SeriesFragment;


import java.util.ArrayList;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class MainActivity extends AppCompatActivity {
    ImageView imageViewMenu,imageViewBack,imageViewBackGround,imageViewHead,imageViewShare;
    LinearLayout linearLayoutMenu;
    RelativeLayout relativeLayout,menuMyLoad,menumyLike;
    RadioGroup radioGroup;
    TextView textViewTitle;
    ViewPager viewPager;
    TabLayout tabLayout;
    ArrayList<Fragment> fragments;
    private String tab1Name="最新";
    private String tab2Name="频道";
    private boolean temp=true;
    int tabCurrentPosition=0;
    boolean flag=true;
    boolean Tag=false;
    SetOnMovePosition onMovePosition;
    MainFragment mainFragment;
    Handler handler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        AnimationBackGround();
        initBackGround();
        initAnimation();
        getMessage();
        tabListener();
        setListener();
    }

    public void setOnMethod(SetOnMovePosition onMovePosition){
        this.onMovePosition=onMovePosition;
    }

    //初始化View
    private void initView() {
        imageViewMenu= (ImageView) findViewById(R.id.first_menu);
        imageViewBack= (ImageView) findViewById(R.id.first_main_close);
        imageViewShare= (ImageView) findViewById(R.id.first_share);
        linearLayoutMenu= (LinearLayout) findViewById(R.id.first_linearlayout);
        tabLayout= (TabLayout) findViewById(R.id.first_tablayout);
        viewPager= (ViewPager) findViewById(R.id.first_viewpager);
        imageViewBackGround=(ImageView) findViewById(R.id.img_background);
        relativeLayout=(RelativeLayout) findViewById(R.id.relativelayout);
        radioGroup= (RadioGroup) findViewById(R.id.radiogroup);
        textViewTitle= (TextView) findViewById(R.id.menu_title);
//        menuMyOrder= (RelativeLayout) findViewById(R.id.my_order);
        menuMyLoad= (RelativeLayout) findViewById(R.id.my_load);
        menumyLike= (RelativeLayout) findViewById(R.id.my_like);
        imageViewHead= (ImageView) findViewById(R.id.first_main_head);
    }
    //设置监听
    private void setListener() {
        imageViewMenu.setOnClickListener(new Listener());
        imageViewBack.setOnClickListener(new Listener());
        imageViewShare.setOnClickListener(new Listener());
        radioGroup.setOnCheckedChangeListener(new  RadioListener());
//        menuMyOrder.setOnClickListener(new MenuListener());
        menuMyLoad.setOnClickListener(new MenuListener());
        menumyLike.setOnClickListener(new MenuListener());
        imageViewHead.setOnClickListener(new MenuListener());

    }
    private void tabListener() {
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabCurrentPosition = tab.getPosition();
                onMovePosition.onMove(tabCurrentPosition);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    //背景的动画消失时间
    private void initBackGround() {
        if(temp){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2800);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                               relativeLayout.setVisibility(View.GONE);
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
    //背景的变大动画
    private void AnimationBackGround() {
        AnimationSet animationSet=new AnimationSet(true);
        ScaleAnimation scaleAnimation=new ScaleAnimation(
                relativeLayout.getScaleX(),1.1f,relativeLayout.getScaleY(),1.1f,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f
        );
        scaleAnimation.setDuration(3000);
        animationSet.addAnimation(scaleAnimation);
        relativeLayout.setAnimation(animationSet);
    }
    //给集合添加数据,主页面的数据来源.三个Fragment
    private void getMessage() {
        fragments=new ArrayList<>();
        mainFragment=new MainFragment();
        fragments.add(mainFragment);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container,mainFragment)
                .commit();
        setTabLayoutTab();
        Bundle data=new Bundle();
        data.putInt("tabCurrentPosition",tabCurrentPosition);
        mainFragment.setArguments(data);

        //添加系列和幕后的Fragment
        SeriesFragment seriesFragment=new SeriesFragment();
        fragments.add(seriesFragment);
        BehindFragment behindFragment=new BehindFragment();
        fragments.add(behindFragment);

        //回调得到ViewPager的点击的值(连动的position)
        mainFragment.CallBackPosition(new MainFragment.SetOnPosition() {
            @Override
            public void onPosition(int position) {
                tabLayout.setScrollPosition(position,0,true);
            }
        });
    }

    //设置tab
    private void  setTabLayoutTab(){
        tabLayout.addTab(tabLayout.newTab().setText(tab1Name));
        tabLayout.addTab(tabLayout.newTab().setText(tab2Name));
//        tabLayout.setupWithViewPager(viewPager);
    }
    //menu的动画
    private void initAnimation() {
        if(flag){
            AnimationSet animationSet=new AnimationSet(true);
            TranslateAnimation translateAnimation1=new TranslateAnimation(

                    Animation.RELATIVE_TO_SELF,0f,
                    Animation.RELATIVE_TO_SELF,0f,
                    Animation.RELATIVE_TO_SELF,-getWindowManager().getDefaultDisplay().getWidth(),
                    Animation.RELATIVE_TO_SELF,-getWindowManager().getDefaultDisplay().getHeight()
//                    Animation.RELATIVE_TO_SELF,getWindowManager().getDefaultDisplay().getWidth(),
//                    Animation.RELATIVE_TO_SELF,getWindowManager().getDefaultDisplay().getHeight(),
//                    Animation.RELATIVE_TO_SELF,0f,
//                    Animation.RELATIVE_TO_SELF,0f
            );
            translateAnimation1.setDuration(1);
            animationSet.addAnimation(translateAnimation1);
            linearLayoutMenu.setAnimation(animationSet);
            flag=false;
        }
    }
    //各种监听
    private class Listener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.first_menu:
                    linearLayoutMenu.setVisibility(View.VISIBLE);
//                    Animation();
                    break;
                case R.id.first_main_close:
                    linearLayoutMenu.setVisibility(View.GONE);
                    break;
                case R.id.first_share:
                    showShare();
                    break;
            }
        }
    }
    //启动页面的动画
    private void Animation() {
        AnimationSet animationSet=new AnimationSet(true);
        TranslateAnimation translateAnimation=new TranslateAnimation(
                Animation.RELATIVE_TO_SELF,0f,
                Animation.RELATIVE_TO_SELF,0f,
                Animation.RELATIVE_TO_SELF,getWindowManager().getDefaultDisplay().getWidth(),
                Animation.RELATIVE_TO_SELF,getWindowManager().getDefaultDisplay().getHeight()
        );
        translateAnimation.setDuration(500);
        animationSet.addAnimation(translateAnimation);
        linearLayoutMenu.setAnimation(animationSet);
    }
    class RadioListener implements RadioGroup.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int position) {
            linearLayoutMenu.setVisibility(View.GONE);
            switch (radioGroup.getCheckedRadioButtonId()){
                case R.id.index:
                    show(0);
                    tabLayout.setVisibility(View.VISIBLE);
                    textViewTitle.setVisibility(View.GONE);
                    break;
                case R.id.xilie:
                    show(1);
                    textViewTitle.setVisibility(View.VISIBLE);
                    tabLayout.setVisibility(View.GONE);
                    textViewTitle.setText("系列");
                    break;
                case R.id.muhou:
                    show(2);
                    tabLayout.setVisibility(View.GONE);
                    textViewTitle.setVisibility(View.VISIBLE);
                    textViewTitle.setText("幕后");
                    break;
            }
        }
    }

    int lastPosition=0;
    private void show(int position){
        //显示当前的fragment,隐藏上一个的fragment
        Fragment fragment = fragments.get(position);
        //开始一个事务,要么做完
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.hide(fragments.get(lastPosition));
        if(fragment.isAdded()){
            fragmentTransaction.show(fragment);
        }else {
            fragmentTransaction.add(R.id.fragment_container,fragment);
        }
        //提交
        fragmentTransaction.commit();
        lastPosition=position;
    }
    //连动时的回调接口
  public interface SetOnMovePosition{
       void onMove(int position);
    }
    class MenuListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch ( view.getId()){
               /* case R.id.my_order:
                    Intent intent1=new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent1);
                    break;*/
                case R.id.my_load:
                    Intent intent2=new Intent(MainActivity.this,MyLoadedActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.my_like:
                    Intent intent3=new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent3);
                    break;
                case R.id.first_main_head:
                    Intent intent4=new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent4);
                    break;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(linearLayoutMenu.getVisibility()==View.VISIBLE){
                linearLayoutMenu.setVisibility(View.GONE);
                return false;
            }
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
    private void exit() {
        if (!Tag) {
            Tag = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Tag = false;
        }
    };
   private void showShare() {
        ShareSDK.initSDK(MainActivity.this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是来自V视角的分享");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
       oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是来自V视角的分享");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(MainActivity.this);
    }
}
