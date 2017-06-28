package com.example.mymvp.views;

import com.example.mymvp.bean.User;

/**
 * Created by admin on 2017/6/20.
 * 该操作需要什么？（getUserName, getPassword）
 * 该操作的结果，对应的反馈？(toMainActivity, showFailedError)
 * 该操作过程中对应的友好的交互？(showLoading, hideLoading)
 */

public interface IUserLoginView {

    //获取用户名和密码
    String getUsername();
    String getPassword();

    //耗时操作的提醒
    void showLoading();
    void hideLoading();

    //登陆成功或者失败
    void toMainActivity(User user);
    void showFailedError();

    //清除密码或者用户名
    void clearUsername();
    void clearPassword();

}
