package com.example.admin.ebuy.model.user;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.ebuy.R;
import com.example.admin.ebuy.base.BaseFragment;

public class UserFragment extends BaseFragment {
    public final static String TAG="UserFragment";
    @Override
    protected int getLayoutResourceId() {
        return R.layout.user_fragment;
    }

    @Override
    protected void onSetBodyView(View view, ViewGroup container, Bundle savedInstanceState) {

    }

    @Override
    public String getTagName() {
        return TAG;
    }
}
