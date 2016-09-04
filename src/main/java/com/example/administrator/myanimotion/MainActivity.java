package com.example.administrator.myanimotion;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyListView.OnRefreshListener {
    MyListView lv;
    ArrayAdapter<String> ad;
    List<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("这是:"+i);
        }
        lv = (MyListView) findViewById(R.id.lv);
        ad = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        lv.setAdapter(ad);
        lv.setOnRefreshListenr(this);
        ListView lv ;

    }

    @Override
    public void onRefresh() {
        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    list.add("刷新后的 101条数据");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ad.notifyDataSetChanged();
                            lv.RefreshComplete();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
