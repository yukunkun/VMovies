package com.example.administrator.myapplication.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.adapter.MyListViewAdapter;
import com.example.administrator.myapplication.bean.SeriseListView;
import com.example.administrator.myapplication.selfview.MyListView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/15.
 */
public class SeriesVideoFragment extends Fragment{
    MyListView myListView;
    MyListViewAdapter adapter;
    ArrayList<SeriseListView> listViews;

    @Override
    public void onAttach(Context context) {
        Bundle data = getArguments();
        listViews= (ArrayList<SeriseListView>) data.getSerializable("listViews");
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.series_list_view, null);
        myListView= (MyListView) view.findViewById(R.id.mylistview);
        adapter=new MyListViewAdapter(getContext(),listViews);
        myListView.setAdapter(adapter);
        return view;
    }
}
