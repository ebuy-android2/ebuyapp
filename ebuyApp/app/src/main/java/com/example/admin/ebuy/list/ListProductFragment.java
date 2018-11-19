package com.example.admin.ebuy.list;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.ebuy.R;
import com.example.admin.ebuy.base.BaseActivity;
import com.example.admin.ebuy.base.BaseFragment;

public class ListProductFragment extends BaseFragment {
    public static  final  String TAG="ListProductFragment";
    @Override
    protected int getLayoutResourceId() {
        return R.layout.list_product_fragment;
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
