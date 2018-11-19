package com.example.admin.ebuy.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.admin.ebuy.R;
import com.example.admin.ebuy.util.AppConfig;
import com.example.admin.ebuy.util.FragmentProvider;
import com.example.admin.ebuy.util.Navigator;
import com.example.admin.ebuy.view.EBCustomFont;


/**
 * Created by tuan.nguyen on 23/6/18.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{
    public abstract int getLayoutResource();
    public abstract void loadControl(Bundle savedInstanceState);
    public abstract int getFragmentContainerViewId();
    private FragmentManager fragmentManager;
    public ProgressDialog mSpinner;

    public void setLoading(boolean isLoading) {
        if (isFinishing())
            return;

        if (this == null)
            return;

        if (isLoading)
            mSpinner.show();
        else
            mSpinner.dismiss();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mSpinner = new ProgressDialog(this);
        mSpinner.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mSpinner.setMessage("Waiting...");
        mSpinner.setCanceledOnTouchOutside(false);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        loadControl(savedInstanceState);
        IntentFilter filter = new IntentFilter();
        filter.addAction(AppConfig.BROADCAST_RECEIVE_ORDER);

        IntentFilter filter1 = new IntentFilter();
        filter1.addAction(AppConfig.BROADCAST_CLOSE_POPUP);

        if((ImageView)findViewById(R.id.imgBack)!=null)
        {
            ((ImageView)findViewById(R.id.imgBack)).setOnClickListener(this);
        }



    }
    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    public static boolean isAvailableActivity(Activity activity) {
        if (activity != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (!activity.isDestroyed()) {
                    return true;
                }
            }
            if (!activity.isFinishing()) {
                return true;
            }
        }
        return false;
    }

    public BaseFragment startFirstFragment() {
        Bundle data = getIntent().getExtras();
        BaseFragment fragment = null;
        if (data != null) {
            String fragmentName = data.getString(Navigator.FRAGMENT_CLASS_NAME_START, "");
            if (!fragmentName.isEmpty()) {
                fragment = FragmentProvider.getFragmentNewInstance(fragmentName, data);
                replaceFragment(fragment);
            }
        }
        return fragment;
    }


    public void addFragment(BaseFragment fragment) {
        if (fragment == null || getFragmentContainerViewId() == 0 || !isAvailableActivity(this))
            return;
        if (fragmentManager == null) fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(getFragmentContainerViewId(), fragment, fragment.getTagName());
        ft.addToBackStack(fragment.getTagName());
        ft.commit();
    }

    public void replaceFragment(BaseFragment fragment) {
        if (fragment == null) return;
        if (fragmentManager == null) fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(getFragmentContainerViewId(), fragment, fragment.getTagName());
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    public void setTitle(boolean allCap, String title) {
        if (findViewById(R.id.txtTitleHeader) != null) {
            ((TextView) findViewById(R.id.txtTitleHeader)).setText(title);
            ((TextView) findViewById(R.id.txtTitleHeader)).setAllCaps(allCap);
        }
    }
    public void setVisibleFinish(boolean allCap){
        if (findViewById(R.id.txtFinish)!=null){
            if(allCap ==true){
                ((EBCustomFont)findViewById(R.id.txtFinish)).setVisibility(View.VISIBLE);
            }
            else
                ((EBCustomFont)findViewById(R.id.txtFinish)).setVisibility(View.GONE);
        }
    }
    public void setVisibleBack(boolean b)
    {
        if (findViewById(R.id.imgBack)!=null){
            if(b==true){
                ((ImageView)findViewById(R.id.imgBack)).setVisibility(View.VISIBLE);
            }
            else
            ((ImageView)findViewById(R.id.imgBack)).setVisibility(View.GONE);
        }
    }

    public void setVisibleToolbar(boolean allCap){

        if (findViewById(R.id.rlHeader)!=null){
            if(allCap ==true){
                ((RelativeLayout)findViewById(R.id.rlHeader)).setVisibility(View.VISIBLE);
            }
            else
                ((RelativeLayout)findViewById(R.id.rlHeader)).setVisibility(View.GONE);
        }
    }
    public String getDeviceId() {
        String android_id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return android_id;
    }

    public void setToolBarTittle(String title, String subTitle, boolean allCapTitle, boolean allCapSub) {
        ((TextView) findViewById(R.id.txtHeader)).setText(title);
        ((TextView) findViewById(R.id.txtSubHeader)).setText(subTitle);
        ((TextView) findViewById(R.id.txtHeader)).setAllCaps(allCapTitle);
        ((TextView) findViewById(R.id.txtSubHeader)).setAllCaps(allCapSub);


    }

    public String getToolBarTittle() {
        return ((TextView) findViewById(R.id.txtHeader)).getText().toString();



    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {

            case R.id.imgBack:
                onBackPressed();
                break;


        }

    }
}
