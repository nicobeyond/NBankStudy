package com.nbank.study;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import ico.ico.adapter.BaseAdapter;
import ico.ico.ico.BaseFragActivity;
import ico.ico.util.log;

public class DialogActivity extends BaseFragActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//            }
//        }, 8000);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        MyAdapter adapter = new MyAdapter(mActivity, 99);
        recyclerView.setAdapter(adapter);
    }


    class MyAdapter extends BaseAdapter<String, BaseAdapter.BaseViewHolder> {

        public MyAdapter(Context context) {
            super(context, R.layout.item);
        }

        public MyAdapter(Context context, int count) {
            super(context, R.layout.item, count);
        }

        public MyAdapter(Context context, List<String> strings) {
            super(context, R.layout.item, strings);
        }

        @Override
        public void onBindViewHolder(BaseAdapter.BaseViewHolder holder, final int position) {
            super.onBindViewHolder(holder, position);
            Object obj = holder.getView(R.id.text).getTag();
            BaseAdapter.BaseViewHolder tagHolder;
            if (obj != null) {
                tagHolder = (BaseViewHolder) obj;
                log.w("===" + (tagHolder == holder));
            }
//            log.w("===" + position + "|" + (obj == null ? "null" : ((BaseAdapter.BaseViewHolder) obj).getView(R.)));
            holder.setText(R.id.text, position + "dasdsad");
            holder.getView(R.id.text).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    log.w("===" + position + "|" + v.getTag().toString());
                }
            });
        }
    }
}
