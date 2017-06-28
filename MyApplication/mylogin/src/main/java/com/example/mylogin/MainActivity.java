package com.example.mylogin;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;

import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Context context;
    //控件按钮
    private EditText mobile_login, yanzhengma;
    private Button getyanzhengma1, login_btn;

    //倒计时秒数
    private int countSeconds = 60;

    private Handler mCountHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (countSeconds > 0) {
                --countSeconds;
                getyanzhengma1.setText("(" + countSeconds + ")后获取验证码");
                mCountHandler.sendEmptyMessageDelayed(0, 1000);
            } else {
                countSeconds = 60;
                getyanzhengma1.setText("请重新获取验证码");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);

        //寻找控件
        initView();

        //控件点击
        initEvent();


    }

    /**
     * 点击初始化
     */
    private void initEvent() {
        getyanzhengma1.setOnClickListener(this);
        login_btn.setOnClickListener(this);
    }

    /**
     * 初始化各种控件
     */
    private void initView() {

        mobile_login = (EditText) findViewById(R.id.mobile_login);
        getyanzhengma1 = (Button) findViewById(R.id.getyanzhengma1);
        yanzhengma = (EditText) findViewById(R.id.yanzhengma);
        login_btn = (Button) findViewById(R.id.login_btn);
    }

    /**
     * 点击事件的内容
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击获取验证码的操作
            case R.id.getyanzhengma1:
                if (countSeconds == 60) {
                    String mobile = mobile_login.getText().toString();
                    Log.e("tag", "mobile==" + mobile);
                    getMobile(mobile);
                } else {
                    Toast.makeText(context, "不能重复发送验证码", Toast.LENGTH_SHORT).show();
                }
                break;
            //点击登录的操作
            case R.id.login_btn:
                login();
                break;
            //其他情况下
            default:
                break;
        }
    }

    private void getMobile(String mobile) {
        if("".equals(mobile)){
            Toast.makeText(context, "手机号不能为空", Toast.LENGTH_SHORT).show();
        }else if(new Validator().isMobile(mobile) == false){
            Toast.makeText(context, "手机号码输入有误，请重新输入！", Toast.LENGTH_SHORT).show();
        }else{
            requestVerifyCode(mobile);
        }

      /*  if ("".equals(mobile)) {
            Log.e("tag", "mobile=" + mobile);
            new AlertDialog.Builder(context).setTitle("提示").setMessage("手机号码不能为空").setCancelable(true).show();
        } else if (new Validator().isMobile(mobile) == false) {
            new AlertDialog.Builder(context).setTitle("提示").setMessage("请输入正确的手机号码").setCancelable(true).show();
        } else {
            Log.e("tag", "输入了正确的手机号");
            requestVerifyCode(mobile);
        }*/
    }


    /**
     * 登陆操作
     */
    private void login() {
        String url_mobile = "";
        OkHttpUtils.get(url_mobile)     // 请求方式和请求url
                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new JsonCallback<RequestInfo>(){
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        super.onSuccess(s, call, response);
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            Log.e("tag", "登陆的result=" + jsonObject);
                            String success = jsonObject.optString("success");
                            String data = jsonObject.optString("data");
                            String msg=jsonObject.optString("msg");
                            if ("true".equals(success)) {
                                Log.e("tag","登陆的data="+data);
                                JSONObject json = new JSONObject(data);
                                String token = json.optString("token");
                                String userId = json.optString("userId");
                                //我这里按照我的要求写的，你们也可以适当改动
                                //获取用户信息的状态
                                //getUserInfo();
                            }else{
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    /**
     * 验证码请求，和验证
     * @param mobile
     */
    private void requestVerifyCode(String mobile) {

        String url_mobile = "";
        OkHttpUtils.get(url_mobile)     // 请求方式和请求url
                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new JsonCallback<RequestInfo>(){
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        super.onSuccess(s, call, response);
                        try {
                            JSONObject jsonObject2 = new JSONObject(response.toString());
                            Log.e("tag", "jsonObject2" + jsonObject2);
                            String state = jsonObject2.getString("success");
                            String verifyCode = jsonObject2.getString("msg");
                            Log.e("tag", "获取验证码==" + verifyCode);
                            if ("true".equals(state)) {
                                Toast.makeText(context, verifyCode, Toast.LENGTH_SHORT).show();
                                startCountBack();//这里是用来进行请求参数的
                            } else {
                                Toast.makeText(context, verifyCode, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    //获取验证码信息,进行计时操作
    private void startCountBack() {
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getyanzhengma1.setText(countSeconds + "");
                mCountHandler.sendEmptyMessage(0);
            }
        });
    }
}
