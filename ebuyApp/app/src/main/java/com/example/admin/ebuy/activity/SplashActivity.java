package com.example.admin.ebuy.activity;

import android.os.Bundle;
import android.os.Handler;

import com.example.admin.ebuy.R;
import com.example.admin.ebuy.base.BaseActivity;
import com.example.admin.ebuy.home.HomeFragment;
import com.example.admin.ebuy.home.activity.HomeActivity;
import com.example.admin.ebuy.model.CurrentUser;
import com.example.admin.ebuy.model.respon.ConfigResponse;
import com.example.admin.ebuy.network.EBServices;
import com.example.admin.ebuy.network.ServiceFactory;
import com.example.admin.ebuy.util.AppConfig;
import com.example.admin.ebuy.util.Navigator;
import com.example.admin.ebuy.util.PrefUtils;
import com.example.admin.ebuy.util.WriteLog;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SplashActivity extends BaseActivity {
    @Override
    public int getLayoutResource() {
        return R.layout.activity_splash;
    }

    @Override
    public void loadControl(Bundle savedInstanceState) {
        getConfig();
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
    private void getConfig(){
        ServiceFactory.createRetrofitService(EBServices.class, AppConfig.getApiEndpoint())
                .getConfig()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ConfigResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        WriteLog.e("TAG", e.getMessage().toString());
                    }

                    @Override
                    public void onNext(ConfigResponse configResponse) {

                        WriteLog.e("TAG", configResponse.toString());
                        if(configResponse.getReplyCode()!= AppConfig.SUCCESS_CODE){

                        }else {
                            PrefUtils.getInstance().putString(CurrentUser.TOKEN_NO_LOGIN, configResponse.getData().getApiKey());

                        }
                    }
                });
    }
}
