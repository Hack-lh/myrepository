package com.example.myjpush;

import android.app.Application;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by admin on 2017/6/22.
 */

public class MyAplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

    }
}
