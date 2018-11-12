package com.example.admin.ebuy.activity;

import android.os.Bundle;
import android.os.Handler;

import com.example.admin.ebuy.R;
import com.example.admin.ebuy.base.BaseActivity;
import com.example.admin.ebuy.home.HomeFragment;
import com.example.admin.ebuy.home.activity.HomeActivity;
import com.example.admin.ebuy.util.Navigator;

public class SplashActivity extends BaseActivity {
    @Override
    public int getLayoutResource() {
        return R.layout.activity_splash;
    }

    @Override
    public void loadControl(Bundle savedInstanceState) {
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Navigator.getInstance().startFragment(SplashActivity.this,HomeFragment.TAG,HomeActivity.class,null);
                finish();
            }
        },2000);
    }

    @Override
    public int getFragmentContainerViewId() {
        return 0;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
