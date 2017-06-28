package com.example.myframework.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/6/21.
 */

public class MyViewpager extends ViewPager  {

    private List<Fragment> fragments;
    private RadioGroup radioGroup;
    private FragmentManager fragmentManager;

    public MyViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MyViewpager(Context context, FragmentManager supportFragmentManager) {
        super(context);
        fragmentManager = supportFragmentManager;
        initView();
    }

    private void initView() {
    }


    @Override
    public boolean onTouchEvent(MotionEvent arg0) {

        return false;

    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        return  false;
    }



}
