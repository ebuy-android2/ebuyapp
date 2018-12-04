package com.example.admin.ebuy.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.admin.ebuy.base.BaseActivity;
import com.example.admin.ebuy.home.HomeFragment;
import com.example.admin.ebuy.list.ListProductFragment;
import com.example.admin.ebuy.model.CurrentUser;
import com.example.admin.ebuy.shopping.ShoppingFragment;
import com.example.admin.ebuy.user.LoginFragment;
import com.example.admin.ebuy.user.UserFragment;


/**
 * Created by kelvin on 3/7/18.
 */

public class HomeAdapter extends FragmentPagerAdapter {
    private BaseActivity baseActivity;

    public HomeAdapter(FragmentManager fm, BaseActivity baseActivity) {
        super(fm);
        this.baseActivity = baseActivity;

    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
               return new HomeFragment();

            case 1:
                return new ListProductFragment();

            case 2:
                return new ShoppingFragment();

            case 3:
                return new UserFragment();

            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

}
