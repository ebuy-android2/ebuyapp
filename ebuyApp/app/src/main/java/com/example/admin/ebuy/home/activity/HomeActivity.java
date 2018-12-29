package com.example.admin.ebuy.home.activity;

import android.os.Bundle;
import android.support.annotation.RequiresPermission;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.admin.ebuy.R;
import com.example.admin.ebuy.adapter.HomeAdapter;
import com.example.admin.ebuy.base.BaseActivity;
import com.example.admin.ebuy.util.WriteLog;

public class HomeActivity extends BaseActivity implements View.OnClickListener,ViewPager.OnPageChangeListener {
    public ViewPager contentView;
    private HomeAdapter homeAdapter;
    private LinearLayout btnHome,btnShopping,btnUser,btnList;
    ImageView ivhome,ivlist,ivshopping,ivuser;
    public  RelativeLayout rlHeader;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_home;
    }

    @Override
    public void loadControl(Bundle savedInstanceState) {
        homeAdapter = new HomeAdapter(getSupportFragmentManager(), this);
        contentView = (ViewPager)findViewById(R.id.contentView);
        contentView.setAdapter(homeAdapter);
        contentView.setOffscreenPageLimit(4);

        rlHeader = (RelativeLayout)findViewById(R.id.rlHeader);
        ivhome = (ImageView)findViewById(R.id.ivHome);
        ivlist = (ImageView)findViewById(R.id.ivList);
        ivshopping = (ImageView)findViewById(R.id.ivShopping);
        ivuser = (ImageView)findViewById(R.id.ivUser);


        btnHome= (LinearLayout) findViewById(R.id.btnHome);
        btnHome.setOnClickListener(this);
        btnList=(LinearLayout) findViewById(R.id.btnList);
        btnList.setOnClickListener(this);
        btnShopping=(LinearLayout) findViewById(R.id.btnShopping);
        btnShopping.setOnClickListener(this);
        btnUser=(LinearLayout) findViewById(R.id.btnUser);
        btnUser.setOnClickListener(this);

        ivhome.setImageResource(R.drawable.ic_home_checked);
//        checkPager();

        savedInstanceState = getIntent().getExtras();
        WriteLog.e("Toan",""+savedInstanceState.getInt("data"));
        int conten =savedInstanceState.getInt("data");
        checkPager(conten);

        contentView.addOnPageChangeListener(this);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().setStatusBarColor(getResources().getColor(R.color.color_main));
        setTitle(true,"trang chủ");
    }

    @Override
    public int getFragmentContainerViewId() {
        return R.id.contentView;
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnHome:
                setTitle(true,"trang chủ");
                this.setVisibleToolbar(true);
                ivhome.setImageResource(R.drawable.ic_home_checked);
                ivlist.setImageResource(R.drawable.ic_list);
                ivshopping.setImageResource(R.drawable.ic_shopping);
                ivuser.setImageResource(R.drawable.ic_user);
                contentView.setCurrentItem(0);
                break;
            case R.id.btnList:
                setTitle(true,"danh sách cửa hàng");
                this.setVisibleToolbar(true);
                ivlist.setImageResource(R.drawable.ic_list_checked);
                ivhome.setImageResource(R.drawable.ic_home);

                ivshopping.setImageResource(R.drawable.ic_shopping);
                ivuser.setImageResource(R.drawable.ic_user);
                contentView.setCurrentItem(1);
                break;
            case R.id.btnShopping:
                setTitle(true,"giỏ hàng");
                this.setVisibleToolbar(true);
                ivshopping.setImageResource(R.drawable.ic_shopping_checked);
                ivhome.setImageResource(R.drawable.ic_home);
                ivlist.setImageResource(R.drawable.ic_list);

                ivuser.setImageResource(R.drawable.ic_user);
                contentView.setCurrentItem(2);
                break;
            case R.id.btnUser:
                this.setVisibleToolbar(false);
                ivhome.setImageResource(R.drawable.ic_home);
                ivlist.setImageResource(R.drawable.ic_list);
                ivshopping.setImageResource(R.drawable.ic_shopping);
                ivuser.setImageResource(R.drawable.ic_user_checked);
                contentView.setCurrentItem(3);
                break;
        }
    }

    private void checkPager(int conten)
    {
        contentView.setCurrentItem(conten);
        switch (conten){
            case 0:

                ivhome.setImageResource(R.drawable.ic_home_checked);
                ivlist.setImageResource(R.drawable.ic_list);
                ivshopping.setImageResource(R.drawable.ic_shopping);
                ivuser.setImageResource(R.drawable.ic_user);
                break;
            case 1:

                ivlist.setImageResource(R.drawable.ic_list_checked);
                ivhome.setImageResource(R.drawable.ic_home);

                ivshopping.setImageResource(R.drawable.ic_shopping);
                ivuser.setImageResource(R.drawable.ic_user);

                break;
            case 2:
                ivshopping.setImageResource(R.drawable.ic_shopping_checked);
                ivhome.setImageResource(R.drawable.ic_home);
                ivlist.setImageResource(R.drawable.ic_list);

                ivuser.setImageResource(R.drawable.ic_user);

                break;
            case 3:
                ivhome.setImageResource(R.drawable.ic_home);
                ivlist.setImageResource(R.drawable.ic_list);
                ivshopping.setImageResource(R.drawable.ic_shopping);
                ivuser.setImageResource(R.drawable.ic_user_checked);
                break;
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        switch (i){
            case 0:
                setTitle(true,"trang chủ");
                this.setVisibleToolbar(true);
                ivhome.setImageResource(R.drawable.ic_home_checked);
                ivlist.setImageResource(R.drawable.ic_list);
                ivshopping.setImageResource(R.drawable.ic_shopping);
                ivuser.setImageResource(R.drawable.ic_user);
                contentView.setCurrentItem(0);
                break;
            case 1:
                setTitle(true,"cửa hàng");
                this.setVisibleToolbar(true);
                ivlist.setImageResource(R.drawable.ic_list_checked);
                ivhome.setImageResource(R.drawable.ic_home);

                ivshopping.setImageResource(R.drawable.ic_shopping);
                ivuser.setImageResource(R.drawable.ic_user);
                contentView.setCurrentItem(1);
                break;
            case 2:
                setTitle(true,"giỏ hàng");
                this.setVisibleToolbar(true);
                ivshopping.setImageResource(R.drawable.ic_shopping_checked);
                ivhome.setImageResource(R.drawable.ic_home);
                ivlist.setImageResource(R.drawable.ic_list);

                ivuser.setImageResource(R.drawable.ic_user);
                contentView.setCurrentItem(2);
                break;
            case 3:

                this.setVisibleToolbar(false);
                ivhome.setImageResource(R.drawable.ic_home);
                ivlist.setImageResource(R.drawable.ic_list);
                ivshopping.setImageResource(R.drawable.ic_shopping);
                ivuser.setImageResource(R.drawable.ic_user_checked);
                contentView.setCurrentItem(3);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
