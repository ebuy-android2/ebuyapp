package com.example.admin.ebuy.user.activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.admin.ebuy.R;
import com.example.admin.ebuy.base.BaseActivity;


/**
 * Created by apple on 1/20/17.
 */

public class UserActivity extends BaseActivity {

    @Override
    public int getLayoutResource() {
        return R.layout.user_activity;
    }

    @Override
    public void loadControl(Bundle savedInstanceState) {

        startFirstFragment();
    }

    @Override
    public int getFragmentContainerViewId() {
        return R.id.container;
    }
}
