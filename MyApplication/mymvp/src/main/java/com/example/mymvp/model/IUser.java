package com.example.mymvp.model;

/**
 * Created by admin on 2017/6/20.
 */

public interface IUser {

    public void login(String username,String password, OnLoginListener loginListener);
}
