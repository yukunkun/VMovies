package com.example.administrator.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.adapter.HistoryListViewAdapter;
import com.example.administrator.myapplication.bean.History;
import com.example.administrator.myapplication.fragment.PingdaoFragment;
import com.example.administrator.myapplication.videoplay.SerisePlayActivity;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MyLoadedActivity extends AppCompatActivity {

    private ListView mListView;
    private View mViewById;
    private HistoryListViewAdapter mHistoryListViewAdapter;
    private List<History> mHistoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_loaded);
        mViewById = findViewById(R.id.tv_his);
        mListView = (ListView) findViewById(R.id.load_listview);
        init();
    }

    private void init() {
        mHistoryList = DataSupport.findAll(History.class);
        Collections.reverse(mHistoryList);
        if(mHistoryList.size()!=0){
            mViewById.setVisibility(View.GONE);
        }
        mHistoryListViewAdapter = new HistoryListViewAdapter(this,(ArrayList<History>) mHistoryList);
        mListView.setAdapter(mHistoryListViewAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MyLoadedActivity.this, SerisePlayActivity.class);
                intent.putExtra("seriesId",mHistoryList.get(position).getSerId());
                intent.putExtra("image",mHistoryList.get(position).getImage());
                startActivity(intent);
                overridePendingTransition(R.anim.down_show, 0);
            }
        });
    }

    public void LoadBack(View view) {
        finish();
    }

    public void ClearHistory(View view) {
        new AlertDialog.Builder(this).setTitle("清除历史记录")
                .setMessage("请问是否清除历史记录？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataSupport.deleteAll(History.class);
                        mHistoryList.clear();
                        mHistoryListViewAdapter.notifyDataSetChanged();
                        mViewById.setVisibility(View.VISIBLE);

                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }
}
