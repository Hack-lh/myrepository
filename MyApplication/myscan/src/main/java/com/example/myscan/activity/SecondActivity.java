package com.example.myscan.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.myscan.R;

public class SecondActivity extends AppCompatActivity {
    private static final String TAG = "SecondActivity";
    private WebView mWebView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_second);
        initView();
        initData();
    }

    private void initView() {
        mWebView = (WebView) findViewById(R.id.webView);
    }

    private void initData() {
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        Log.i(TAG, "success:" + url);
        // 获取WebView配置
        WebSettings webSettings = mWebView.getSettings();
        // 启用JavaScript
        webSettings.setJavaScriptEnabled(true);
        //加载webView
        mWebView.loadUrl(url);
        //点击新的链接以后就不会跳转到系统浏览器了，而是在本WebView中显示
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                return false;
            }
        });

        mWebView.setDownloadListener(new MyDownloadListenter());

        //优先使用缓存
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    }

    //webview渲染的界面中含有可以下载文件的链接，点击该链接后，应该开始执行下载的操作并保存文件到本地中
    class MyDownloadListenter implements DownloadListener {
        @Override
        public void onDownloadStart(String url, String userAgent,String contentDisposition, String mimetype, long contentLength) {
            //下载任务...，主要有两种方式
            //（1）自定义下载任务
            //（2）调用系统的download的模块
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //其中webView.canGoBack()在webView含有一个可后退的浏览记录时返回true
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
