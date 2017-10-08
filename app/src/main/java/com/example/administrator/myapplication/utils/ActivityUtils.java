package com.example.administrator.myapplication.utils;

import android.content.Context;
import android.content.Intent;

import com.example.administrator.myapplication.videoplay.VideoPlayActivity;

/**
 * Created by Administrator on 2017/10/8.
 */

public class ActivityUtils {

    public static void startVideoPlayActivity(Context context,String image, String postId, String request){
        Intent intent=new Intent(context, VideoPlayActivity.class);
        intent.putExtra("postId" ,postId);
        intent.putExtra("image" ,image);
        intent.putExtra("request" ,request);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
