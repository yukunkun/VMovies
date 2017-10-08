package com.example.administrator.myapplication.mainfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.adapter.BehindViewPagerAdapter;
import com.example.administrator.myapplication.bean.BehindTablayout;
import com.example.administrator.myapplication.fragment.BehindViewPagerFragment;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/5/11.
 */
public class BehindFragment extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    ArrayList<BehindTablayout> titleArray=new ArrayList<>();
    ArrayList<Fragment> fragments=new ArrayList<>();
    final String PATH="http://app.vmoiver.com/apiv3/backstage/getCate";
    final String PATH_PAGER="http://app.vmoiver.com/apiv3/backstage/getPostByCate";
    BehindViewPagerAdapter adapter;
    int page=1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.behind_item, null);
        init(view);
        getMessage();

        setAdapter();
        setListener();
        return view;
    }

    //初始化数据
    private void init(View view) {
        tabLayout= (TabLayout) view.findViewById(R.id.behind_tablayout);
        viewPager= (ViewPager) view.findViewById(R.id.behind_viewpager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
//        tabLayout.setupWithViewPager(viewPager);
    }
    //获得数据
    private void getMessage() {
        OkHttpUtils.post()
                .url(PATH)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Toast.makeText(getContext(), "网络连接失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object=new JSONObject(response);
                            String s = object.optString("status");
                            if(s.equals("0")){
                                JSONArray data = object.optJSONArray("data");
                                for (int i = 0; i < data.length(); i++) {
                                    BehindTablayout layout=new BehindTablayout();
                                    JSONObject object1 = data.optJSONObject(i);
                                    layout.setCateid(object1.optString("cateid"));
                                    layout.setCatename(object1.optString("catename"));
                                    titleArray.add(layout);
                                }
                                getFragment();
                                for (int j = 0; j < titleArray.size(); j++) {
                                    tabLayout.addTab(tabLayout.newTab().setText(titleArray.get(j).getCatename()));
                                }
                            }else{
                                Toast.makeText(getContext(), "下载数据失败", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    //获取每个viewpager的fragment
    private void getFragment() {
        for (int i = 0; i < titleArray.size(); i++) {
            BehindViewPagerFragment pagerFragment=new BehindViewPagerFragment();
            fragments.add(pagerFragment);
            Bundle data=new Bundle();
            data.putString("cateid",titleArray.get(i).getCateid());
            pagerFragment.setArguments(data);
        }
        adapter.notifyDataSetChanged();
    }

    //设置ViewPager适配器
    private void setAdapter() {
        adapter=new BehindViewPagerAdapter(getChildFragmentManager(),fragments);
        viewPager.setAdapter(adapter);
    }


    //设置联动的监听
    private void setListener() {
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                tabLayout.setScrollPosition(position,0,true);
            }
        });
    }
}
