package com.example.myframework.classify;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myframework.R;

/**
 * Created by admin on 2017/6/21.
 */

public class Find_hotCollectionFragment extends Fragment{

    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if(mView == null) {
            mView = inflater.inflate(R.layout.fragment_tablayout, container, false);
        }
        TextView textView = (TextView) mView.findViewById(R.id.classify_text);
        textView.setText("热门收藏");

        return mView;
    }
}
