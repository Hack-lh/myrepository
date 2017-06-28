package com.example.admin.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.admin.helloworld.utils.DensityUtils;

public class TestActivity extends AppCompatActivity {

    private ProgressBar pb;
    private Button mStart;
    private TextView mText;
    private int i =0;
    private TextView mText2;
    private Button mBack;
    private ImageView mImage;
    private Button mSkip;

    Handler handler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);


            pb.setProgress(msg.arg1);

            if(msg.arg1 == 100){
                mText.setText("下载完成");
                mText2.setVisibility(View.GONE);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mText.getLayoutParams();
                params.setMargins(0, 0, 0, 0);// 通过自定义坐标来放置你的控件left, top, right, bottom
                mText.setLayoutParams(params);
                pb.setVisibility(View.GONE);
                i = 0;
                return;
            }else{
                mText.setText(msg.arg1+"%");
            }

            initData();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        pb = (ProgressBar) findViewById(R.id.pb_progressBar);
        mImage = (ImageView) findViewById(R.id.iv_image);
        mStart = (Button) findViewById(R.id.bt_start);
        mText = (TextView) findViewById(R.id.tv_text);
        mText2 = (TextView) findViewById(R.id.tv_text2);
        mBack = (Button) findViewById(R.id.bt_back);
        mSkip = (Button) findViewById(R.id.bt_skip);

        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TestActivity.this, "点击事件", Toast.LENGTH_LONG).show();
                pb.setVisibility(View.VISIBLE);
                pb.setProgress(0);

                mText2.setVisibility(View.VISIBLE);

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mText.getLayoutParams();
                // 通过自定义坐标来放置你的控件left, top, right, bottom
                params.setMargins(DensityUtils.dip2px(TestActivity.this,90), 0, 0, 0);
                mText.setLayoutParams(params);
                mText.setText(0+"%");

                initData();
            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

      Glide.with(this)
              .load("http://pic.35pic.com/normal/09/19/05/14151964_200536838000_2.jpg")
              .fitCenter()
              .error(R.drawable.padata)
              .into(mImage);

      mSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestActivity.this,RecyActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initData() {

        //开启一个线程模拟处理耗时的操作
        new Thread(new Runnable() {
            @Override
            public void run() {
                i += 50;
                Message msg = handler.obtainMessage();
                msg.arg1 = i;
                SystemClock.sleep(1000);
                //通过Handler发送一个消息切换回主线程（mHandler所在的线程）
                handler.sendMessage(msg);
            }
        }).start();

    }
}
