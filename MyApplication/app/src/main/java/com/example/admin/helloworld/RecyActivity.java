package com.example.admin.helloworld;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.helloworld.utils.DefaultItemTouchHelpCallback;

import java.util.ArrayList;

public class RecyActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private ArrayList<String> numbers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recy);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(RecyActivity.this));


        //设置数据
        for (int i = 0; i < 20; i++) {
            numbers.add("京东6.18惊天秘闻之趣事" + i);
        }

        MyAdapter myAdapter = new MyAdapter(numbers);
        mRecyclerView.setAdapter(myAdapter);

        myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View view , int position){
                Toast.makeText(RecyActivity.this, numbers.get(position)+"条目的详情页", Toast.LENGTH_SHORT).show();
            }
        });


    }

    /**
     * RecyclerView的适配器
     */
    public static class MyAdapter extends RecyclerView.Adapter implements View.OnClickListener {

        private View view;
        private OnItemClickListener mOnItemClickListener = null;
        private ArrayList<String> numbers;

        public MyAdapter(ArrayList<String> numbers) {
            this.numbers = numbers;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
            ViewHolder viewHolder = new ViewHolder(view);
            view.setOnClickListener(this);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((ViewHolder)holder).mTextView.setText(numbers.get(position));
            //将position保存在itemView的Tag中，以便点击时进行获取
            ((ViewHolder)holder).itemView.setTag(position);
        }

        @Override
        public int getItemCount() {
            return numbers.size();
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                //注意这里使用getTag方法获取position
                mOnItemClickListener.onItemClick(v,(int)v.getTag());
            }
        }

        //自定义的ViewHolder，持有每个Item的的所有界面元素
        public class ViewHolder extends RecyclerView.ViewHolder{
            public TextView mTextView;
            public int i = 0 ;

            public ViewHolder(View view){
                super(view);
                mTextView = (TextView) view.findViewById(R.id.tv_rv_text);
            }
        }

        /**
         * 定义item点击事件
         */
        public static interface OnItemClickListener {
            void onItemClick(View view , int position);
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.mOnItemClickListener = listener;
        }
    }
}
