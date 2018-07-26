package com.example.sjf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sjf.data.JokeData;
import com.example.sjf.myapplication.R;

import java.util.List;

public class JokeAdapter extends BaseAdapter{
    private LayoutInflater layoutInflater;
    private Context context;
    private List<JokeData> jokeDatalist;
    public JokeAdapter(Context context,List<JokeData> jokeData){
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.jokeDatalist = jokeData;
    }

    @Override
    public int getCount() {
        return jokeDatalist.size();
    }

    @Override
    public Object getItem(int position) {
        return jokeDatalist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        VeiwHolder veiwHolder = null;
        if (view ==null){
            view = LayoutInflater.from(context).inflate(R.layout.joke_item,null);
            veiwHolder = new VeiwHolder();
            veiwHolder.layout = (LinearLayout) view.findViewById(R.id.joke_layout);
            veiwHolder.content = (TextView)view.findViewById(R.id.username);
            veiwHolder.user = (TextView)view.findViewById(R.id.username) ;
            veiwHolder.time = (TextView)view.findViewById(R.id.time_text);
            veiwHolder.untime = (TextView)view.findViewById(R.id.un_time_text);
            view.setTag(view);
        }else {
            veiwHolder = (VeiwHolder) view.getTag();
        }
        final JokeData list = jokeDatalist.get(position);
        veiwHolder.content.setText(list.getContent());
        veiwHolder.user.setText(list.getHashId());
        veiwHolder.time.setText(list.getUnixtime());
        veiwHolder.untime.setText(list.getUnixtime());
        return view;
    }

    private static class VeiwHolder {
        LinearLayout layout;
        TextView content;
        TextView user;
        TextView time;
        TextView untime;

    }
}
