package com.example.mymvp.presenter;

import android.os.Handler;

import com.example.mymvp.bean.User;
import com.example.mymvp.model.IUserBI;
import com.example.mymvp.model.OnLoginListener;
import com.example.mymvp.views.IUserLoginView;

/**
 * Created by admin on 2017/6/20.
 */

public class UserLoginPresenter {

    private IUserLoginView userLoginView;
    private IUserBI iUserBI;
    private Handler mHandler = new Handler();

    public UserLoginPresenter(IUserLoginView userLoginView)
    {
        this.userLoginView = userLoginView;
        this.iUserBI = new IUserBI();
    }

    public void login(){

        userLoginView.showLoading();
        iUserBI.login(userLoginView.getUsername(), userLoginView.getPassword(), new OnLoginListener() {
                @Override
            public void loginSuccess(final User user) {
                //需要在UI线程执行
                mHandler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        userLoginView.toMainActivity(user);
                        userLoginView.hideLoading();
                    }
                });
            }

            @Override
            public void loginFailed() {
                //需要在UI线程执行
                mHandler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        userLoginView.showFailedError();
                        userLoginView.hideLoading();
                    }
                });
            }
        });

    }

    public void clear()
    {
        userLoginView.clearUsername();
        userLoginView.clearPassword();
    }

}
