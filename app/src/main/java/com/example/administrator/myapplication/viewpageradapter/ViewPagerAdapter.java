package com.example.administrator.myapplication.viewpageradapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/7.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter{
    ArrayList<Fragment> arrayList;

    public ViewPagerAdapter(FragmentManager fm,ArrayList<Fragment> arrayList) {
        super(fm);
        this.arrayList=arrayList;
    }

    @Override
    public Fragment getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }
}
