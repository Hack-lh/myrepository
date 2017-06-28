package com.example.myframework.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myframework.R;

/**
 * Created by admin on 2017/6/20.
 */

public class CallFragment extends Fragment {

    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if(mView == null){
            mView = inflater.inflate(R.layout.fragment_call,container,false);
        }
        return mView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((ViewGroup)mView.getParent()).removeView(mView);
    }
}
