package com.example.admin.ebuy.activity;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.admin.ebuy.R;
import com.example.admin.ebuy.base.BaseActivity;

public class SupportActivity extends BaseActivity {



    @Override
    public int getLayoutResource() {
        return R.layout.support_activity;
    }

    @Override
    public void loadControl(Bundle savedInstanceState) {
        this.getWindow().setStatusBarColor(getResources().getColor(R.color.color_main));
        ((BaseActivity)this).setVisibleBack(true);
        ((BaseActivity)this).setVisibleFinish(false);

        startFirstFragment();
    }


    @Override
    public int getFragmentContainerViewId() {
        return R.id.frameSupport;
    }
}
