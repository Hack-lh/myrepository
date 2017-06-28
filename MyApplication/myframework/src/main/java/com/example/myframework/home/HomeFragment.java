package com.example.myframework.home;


import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myframework.R;
import com.example.myframework.utils.AppToastMgr;

import java.util.ArrayList;

/**
 * Created by admin on 2017/6/20.
 */

public class HomeFragment extends Fragment implements View.OnClickListener {

    private View mView;
    private ViewPager mViewPager;
    private ArrayList<Integer> mImages;
    private final int AUTO_MSG = 1;
    private final int HANDLE_MSG = AUTO_MSG + 1;
    private static final int PHOTO_CHANGE_TIME = 2000;//定时变量
    private int index = 0;
    private RecyclerView mRecyclerView;
    private ArrayList<String> numbers = new ArrayList<>();

    private Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case AUTO_MSG:
                    mViewPager.setCurrentItem(index++ % mImages.size(), false);//收到消息后设置当前要显示的图片
                    mHandler.sendEmptyMessageDelayed(AUTO_MSG, PHOTO_CHANGE_TIME);
                    break;
                case HANDLE_MSG:
                    mHandler.sendEmptyMessageDelayed(AUTO_MSG, PHOTO_CHANGE_TIME);
                    break;
                default:
                    break;
            }
        }

        ;
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_home, container, false);
        }
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initViewPager();
        mViewPager.setCurrentItem(1);
        mHandler.sendEmptyMessageDelayed(AUTO_MSG, PHOTO_CHANGE_TIME);

        initRecyclerView();

        //设置点击事件按钮
        initBtn();

    }

    private void initBtn() {
        Button mBtn1 = (Button) mView.findViewById(R.id.home_bt1);
        Button mBtn2 = (Button) mView.findViewById(R.id.home_bt2);
        Button mBtn3 = (Button) mView.findViewById(R.id.home_bt3);
        Button mBtn4 = (Button) mView.findViewById(R.id.home_bt4);
        Button mBtn5 = (Button) mView.findViewById(R.id.home_bt5);
        Button mBtn6 = (Button) mView.findViewById(R.id.home_bt6);
        Button mBtn7 = (Button) mView.findViewById(R.id.home_bt7);
        Button mBtn8 = (Button) mView.findViewById(R.id.home_bt8);

        mBtn1.setOnClickListener(this);
        mBtn2.setOnClickListener(this);
        mBtn3.setOnClickListener(this);
        mBtn4.setOnClickListener(this);
        mBtn5.setOnClickListener(this);
        mBtn6.setOnClickListener(this);
        mBtn7.setOnClickListener(this);
        mBtn8.setOnClickListener(this);

    }


    /**
     * 设置列表
     */
    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.rv_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //设置数据
        for (int i = 0; i < 20; i++) {
            numbers.add("京东6.18惊天秘闻之趣事" + i);
        }

        final MyAdapter myAdapter = new MyAdapter(numbers);
        mRecyclerView.setAdapter(myAdapter);

        myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View view , int position){
                Toast.makeText(getContext(), numbers.get(position)+"条目的详情页", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * 设置轮播图
     */
    private void initViewPager() {
        //设置轮播数据
        mImages = new ArrayList<>();
        mImages.add(R.drawable.ti6);
        mImages.add(R.drawable.ti4);
        mImages.add(R.drawable.ti5);
        mImages.add(R.drawable.ti6);
        mImages.add(R.drawable.ti4);


        mViewPager = (ViewPager) mView.findViewById(R.id.vp_viewpager);
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mImages.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                //getView
                View view = View.inflate(getContext(), R.layout.item, null);
                ImageView imageView = (ImageView) view.findViewById(R.id.item_vp);
                imageView.setImageResource(mImages.get(position));
                //添加到容器中
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((ViewGroup) mView.getParent()).removeView(mView);
    }

    /**
     * 按钮点击响应处理
     * @param v
     */
    @Override
    public void onClick(View v) {
        int i = 0;
        switch (v.getId()){
            case R.id.home_bt1:
                i = 1;
                AppToastMgr.Toast(getContext(),"第"+i+"个点击按钮");
                break;
            case R.id.home_bt2:
                i = 2;
                AppToastMgr.Toast(getContext(),"第"+i+"个点击按钮");
                break;
            case R.id.home_bt3:
                i = 3;
                AppToastMgr.Toast(getContext(),"第"+i+"个点击按钮");
                break;
            case R.id.home_bt4:
                i = 4;
                AppToastMgr.Toast(getContext(),"第"+i+"个点击按钮");
                break;
            case R.id.home_bt5:
                i = 5;
                AppToastMgr.Toast(getContext(),"第"+i+"个点击按钮");
                break;
            case R.id.home_bt6:
                i = 6;
                AppToastMgr.Toast(getContext(),"第"+i+"个点击按钮");
                break;
            case R.id.home_bt7:
                i = 7;
                AppToastMgr.Toast(getContext(),"第"+i+"个点击按钮");
                break;
            case R.id.home_bt8:
                i = 8;
                AppToastMgr.Toast(getContext(),"第"+i+"个点击按钮");
                break;
            default:
                break;
        }

    }


    /**
     * RecyclerView的适配器
     */
    public static class MyAdapter extends RecyclerView.Adapter implements View.OnClickListener {

        private View view;
        private OnItemClickListener mOnItemClickListener = null;
        private ArrayList<String> numbers = null;

        public MyAdapter(ArrayList<String> numbers) {
            this.numbers = numbers;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview,parent,false);
            ViewHolder viewHolder = new ViewHolder(view);
            view.setOnClickListener(this);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            ((ViewHolder)holder).mTextView.setText(numbers.get(position));
            ((ViewHolder)holder).mTextView2.setText(numbers.get(position));
            ((ViewHolder)holder).mTextView3.setText(numbers.get(position));
            ((ViewHolder)holder).mImageView.setImageResource(R.mipmap.ic_launcher);

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
            public TextView mTextView2;
            public TextView mTextView3;
            public ImageView mImageView;

            public ViewHolder(View view){
                super(view);
                mTextView = (TextView) view.findViewById(R.id.tv_rv_text);
                mTextView2 = (TextView) view.findViewById(R.id.tv_rv_text2);
                mTextView3 = (TextView) view.findViewById(R.id.tv_rv_text3);
                mImageView = (ImageView) view.findViewById(R.id.home_rv_iv);
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
