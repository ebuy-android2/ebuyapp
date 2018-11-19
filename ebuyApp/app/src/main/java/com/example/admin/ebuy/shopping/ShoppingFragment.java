package com.example.admin.ebuy.shopping;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.ebuy.R;
import com.example.admin.ebuy.base.BaseActivity;
import com.example.admin.ebuy.base.BaseFragment;
import com.example.admin.ebuy.home.activity.HomeActivity;
import com.example.admin.ebuy.user.LoginFragment;
import com.example.admin.ebuy.util.Navigator;

public class ShoppingFragment extends BaseFragment {
    public static final String TAG="ShoppingFragment";
    @Override
    protected int getLayoutResourceId() {
        return R.layout.shopping_fragment;
    }

    @Override
    protected void onSetBodyView(View view, ViewGroup container, Bundle savedInstanceState) {

        ((BaseActivity)getActivity()).setVisibleFinish(false);
    }

    @Override
    public String getTagName() {
        return TAG;
    }
}
