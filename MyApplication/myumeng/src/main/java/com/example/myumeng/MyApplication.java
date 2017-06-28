package com.example.myumeng;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

/**
 * Created by admin on 2017/6/23.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                Log.d("mytoken", deviceToken);
                Toast.makeText(getApplicationContext(),deviceToken,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(String s, String s1) {

            }
        });
    }
}
