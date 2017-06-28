package com.example.mymvp.model;

import com.example.mymvp.bean.User;

/**
 * Created by admin on 2017/6/20.
 */

public class IUserBI implements IUser {
    @Override
    public void login(final String username, final String password, final OnLoginListener loginListener) {
        //模拟子线程耗时操作

        new Thread(){
            @Override
            public void run() {
                super.run();

                try{
                    //睡眠
                    Thread.sleep(1000);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }

                //模拟成功登陆
                if("liu".equals(username) && "123456".equals(password)){
                    User user = new User();
                    user.setPassword(password);
                    user.setUsername(username);
                    loginListener.loginSuccess(user);
                }else{
                    loginListener.loginFailed();
                }

            }
        }.start();
    }
}
