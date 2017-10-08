package com.example.administrator.myapplication.mainfragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.example.administrator.myapplication.MainActivity;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.fragment.NewFragment;
import com.example.administrator.myapplication.fragment.PingdaoFragment;
import com.example.administrator.myapplication.viewpageradapter.ViewPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/10.
 */
public class MainFragment extends Fragment {
        ViewPager viewPager;
        SetOnPosition onPosition;
        ArrayList<Fragment> fragments;
        int tabCurrentPosition;

    public void CallBackPosition(SetOnPosition onPosition){
        this.onPosition=onPosition;
    }
    @Override
    public void onAttach(Context context) {
        Bundle data = getArguments();
        tabCurrentPosition=data.getInt("tabCurrentPosition");
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, null);
        init(view);
        getMessage();
        setListener();
        return view;
    }

    private void init(View view) {
        viewPager= (ViewPager)view.findViewById(R.id.first_viewpager);
    }

    //给集合装上数据,ViewPager的数据来源.两个Fragment
    private void getMessage() {
        fragments=new ArrayList<>();
        NewFragment newFragment=new NewFragment();
        fragments.add(newFragment);
        PingdaoFragment pingdaoFragment=new PingdaoFragment();
        fragments.add(pingdaoFragment);

    }
    //监听和适配器
    private void setListener() {
        ViewPagerAdapter adapter=new ViewPagerAdapter(getChildFragmentManager(),fragments);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(tabCurrentPosition);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                onPosition.onPosition(position);
            }
        });
    }
    public interface SetOnPosition{
       void onPosition(int position);
    }

    @Override
    public void onAttach(Activity activity) {
        MainActivity activity1;
        activity1 = (MainActivity) getActivity();
        activity1.setOnMethod(new MainActivity.SetOnMovePosition() {
            @Override
            public void onMove(int position) {
                viewPager.setCurrentItem(position);
            }
        });
        super.onAttach(activity);
    }

}
