package com.example.mymvp.model;

import com.example.mymvp.bean.User;

/**
 * Created by admin on 2017/6/20.
 */

public interface OnLoginListener {

    void loginSuccess(User user);
    void loginFailed();
}
