package com.example.administrator.myapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.PinDao;
import com.example.administrator.myapplication.videoplay.PinDaoDetailActivity;
import com.example.administrator.myapplication.videoplay.VideoPlayActivity;
import com.example.administrator.myapplication.viewpageradapter.GridViewAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/5/7.
 */
public class PingdaoFragment extends Fragment implements AdapterView.OnItemClickListener {
    GridView gridView;
    ArrayList<PinDao> pinDaos;
    GridViewAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pindao_gridview, null);
        gridView= (GridView) view.findViewById(R.id.pindap_gridview);
        getMesage();
        pinDaos=new ArrayList<>();
        initAdapter();
        gridView.setOnItemClickListener(this);
        return view;
    }

    private void initAdapter() {
        adapter=new GridViewAdapter(getContext(),pinDaos);
        adapter.notifyDataSetChanged();
        gridView.setAdapter(adapter);
    }

    private void getMesage() {

        OkHttpUtils.post()
                .url("http://app.vmoiver.com/apiv3/cate/getList")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(getContext(), "网络连接失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                try {
//                    Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                    JSONObject object=new JSONObject(response);
                    JSONArray data = object.optJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        PinDao pinDao=new PinDao();
                        JSONObject object1 = data.optJSONObject(i);
                        pinDao.setOrderid(object1.optString("orderid"));
                        pinDao.setCateid(object1.optString("cateid"));
                        pinDao.setCatename(object1.optString("catename"));
                        pinDao.setAlias(object1.optString("alias"));
                        pinDao.setIcon(object1.optString("icon"));
                        pinDaos.add(pinDao);
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Intent intent=new Intent(getContext(), PinDaoDetailActivity.class);
        intent.putExtra("cateId",pinDaos.get(position).getCateid());
        intent.putExtra("cateName",pinDaos.get(position).getCatename());
        getContext().startActivity(intent);

    }
}
