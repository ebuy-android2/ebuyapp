package com.example.admin.ebuy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.admin.ebuy.base.BaseActivity;

public class MainActivity extends BaseActivity {


    @Override
    public int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    public void loadControl(Bundle savedInstanceState) {

    }

    @Override
    public int getFragmentContainerViewId() {
        return R.id.contentView;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
