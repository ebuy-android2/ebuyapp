package com.example.admin.ebuy;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.example.admin.ebuy.util.AppConfig;


/**
 * Created by kelvin on 3/7/18.
 */

public class EBApplication extends MultiDexApplication {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        // Set up Crashlytics, disabled for debug builds
//        Crashlytics crashlyticsKit = new Crashlytics.Builder()
//                .core(new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
//                .build();
//
//        // Initialize Fabric with the debug-disabled crashlytics.
//        Fabric.with(this, crashlyticsKit, new Crashlytics());
        EBApplication.context = getApplicationContext();
        AppConfig.loadConfig(this);
    }

    public static Context getAppContext(){
        return context;
    }
}
