package com.example.mylogin;

import android.support.annotation.Nullable;

import com.lzy.okhttputils.callback.StringCallback;
import com.lzy.okhttputils.request.BaseRequest;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by admin on 2017/6/22.
 */

public class JsonCallback<R> extends StringCallback {

    //请求成功返回
    @Override
    public void onSuccess(String s, Call call, Response response) {

    }

    //请求失败
    @Override
    public void onError(Call call, Response response, Exception e) {
        super.onError(call, response, e);
    }

    //之前
    @Override
    public void onBefore(BaseRequest request) {
        super.onBefore(request);
    }

    //之后
    @Override
    public void onAfter(@Nullable String s, @Nullable Exception e) {
        super.onAfter(s, e);
    }


}
