package com.example.sjf.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.sjf.data.JokeData;
import com.example.sjf.okhttp.MyOkHttp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity {
    //http://v.juhe.cn/joke/content/list.php
    private static final String TAG = "MainActivity";
    private ArrayList<JokeData>item;
    private RecyclerView jokegoods;
    private BaseQuickAdapter<JokeData,BaseViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jokegoods = findViewById(R.id.main_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        jokegoods.setLayoutManager(manager);
        /**
         * adapter  適配器框架  減少代碼   在activity可以更好的交互
         */
          adapter = new BaseQuickAdapter<JokeData, BaseViewHolder>(R.layout.joke_item) {
            @Override
            protected void convert(BaseViewHolder helper, JokeData item) {
                helper.setText(R.id.content,item.getContent())
                        .setText(R.id.username,item.getHashId())
                        .setText(R.id.time_text,item.getUnixtime())
                        .setText(R.id.un_time_text,item.getUpdatetime());
            }
        };
        jokegoods.setAdapter(adapter);//設置adapter  可以放在初始化的時候

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(MainActivity.this,item.get(position).getHashId(),Toast.LENGTH_SHORT).show();
            }
        });
        listjokegoods();
    }

    private void listjokegoods() {
        RequestBody requestBody = new FormBody.Builder()
                .add("sort","1")
                .add("page","1")
                .add("pagesize","20")
                .add("time","1418816972")
                .add("key","6c13cb16278ab30f6ae7d20c313c8526")
                .build();
        MyOkHttp.getInstance().post("http://v.juhe.cn/joke/content/list.php", requestBody, new MyOkHttp.RequestCallBack() {
            @Override
            public void success(String data) {
                Log.e(TAG,"listjokegoods"+data);
                // JSONObject result = object.getJSONObject("result");
                item = null;
                try {
                    JSONObject object = new JSONObject(data);
                    String success = String.valueOf(object.get("reason"));
                    if (success.equals("Success")){//请求成功
                        if(item == null){
                            item = new ArrayList<JokeData>();
                        }
                    JSONObject result = object.getJSONObject("result");
                        JSONArray list  = result.getJSONArray("data");
                        for (int i=0 ;i<list.length();i++) {
                            JSONObject listdata = list.getJSONObject(i);
                            String listcontent = String.valueOf(listdata.get("content"));
                            String listhashId = String.valueOf(listdata.get("hashId"));
                            String listunixtime = String.valueOf(listdata.get("unixtime"));
                            String listupdatetime = String.valueOf(listdata.get("updatetime"));
                            JokeData jokeData = new JokeData(listcontent, listhashId, listunixtime, listupdatetime);
                            item.add(jokeData);
                        }
                    }

                }catch (JSONException e){
                    Log.e(TAG, "success: " + e.getMessage());
                    Toast.makeText(MainActivity.this,"刷新失败",Toast.LENGTH_SHORT).show();
                }
                if (item != null && item.size() > 0) {
                    setJokegoodsAdapter();
                }

            }

            @Override
            public void fail(Request request, Exception e) {

            }
        },null);
    }
    private void setJokegoodsAdapter(){
        Log.e(TAG, "setJokegoodsAdapter: "+String.valueOf(item.size()) );
        adapter.setNewData(item);//添加新數據
      //  adapter.addData(item);  這是加載分頁數據  就是在原有的數據上在添加數據
        adapter.loadMoreComplete();// 加載更多完成之後 調用這個方法
    }
}
