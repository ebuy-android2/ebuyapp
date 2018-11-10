package com.example.admin.ebuy.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.admin.ebuy.util.Navigator;
import com.example.admin.ebuy.util.WriteLog;

import javax.inject.Inject;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * Created by tuan.nguyen on 23/6/18.
 */

public abstract class BaseFragment extends Fragment {
    protected abstract int getLayoutResourceId();
    protected abstract void onSetBodyView(View view, ViewGroup container, Bundle savedInstanceState);
    public abstract String getTagName();
    private View mBaseView;
    private ViewGroup mContainer;
    public ProgressDialog mSpinner;
    private boolean needRefresh = false;
    public BaseFragment self;
    @Inject
    public Navigator navigator;


    public boolean isNeedRefresh() {
        return needRefresh;
    }

    public void setNeedRefresh(boolean needRefresh) {
        this.needRefresh = needRefresh;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Loading icon
        mSpinner = new ProgressDialog(getActivity());
        mSpinner.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mSpinner.setMessage("Waiting...");
        mSpinner.setCanceledOnTouchOutside(false);


        // check refresh
        if (!needRefresh) {
            if (mBaseView!=null)
                return mBaseView;
        }

        // create new view layout fragment
        WriteLog.e("layout ", getLayoutResourceId()+"");
        mBaseView = inflater.inflate(getLayoutResourceId(), container, false);
        mContainer = container;
        onSetBodyView(mBaseView, mContainer, savedInstanceState);
        return mBaseView;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navigator = Navigator.getInstance();

        self = this;
    }


    public void alertError(String message, int type_error, String title) {
        new SweetAlertDialog(self.getActivity(), type_error)
                .setTitleText(title)
                .setContentText(message)
                .show();

    }


    @Override
    public void onResume() {
        super.onResume();
    }

    public void setLoading(boolean isLoading) {
        if (getActivity().isFinishing())
            return;

        if (this == null)
            return;

        if (!isAdded())
            return;

        if (isLoading)
            mSpinner.show();
        else
            mSpinner.dismiss();
    }

    public boolean checkExternal(){
        return true;
    }
}
