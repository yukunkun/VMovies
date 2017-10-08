package com.example.administrator.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

public class IndexActivity extends AppCompatActivity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        imageView= (ImageView) findViewById(R.id.the_first_img);
        final Intent intent=new Intent(this,MainActivity.class);
        //对imageview做一个动画
        Animation();
        //让页面跳转
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    //动画的方法
    private void Animation() {
        AnimationSet animationSet=new AnimationSet(true);
        ScaleAnimation scaleAnimation=new ScaleAnimation(
                imageView.getScaleX(),1.1f,imageView.getScaleY(),1.1f,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f
        );
        scaleAnimation.setDuration(2200);
        animationSet.addAnimation(scaleAnimation);
        imageView.setAnimation(animationSet);
    }
}
