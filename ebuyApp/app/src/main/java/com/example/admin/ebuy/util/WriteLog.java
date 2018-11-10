package com.example.admin.ebuy.util;

import android.os.Debug;
import android.util.Log;

import com.example.admin.ebuy.BuildConfig;


/**
 * Created by tuan.nguyen on 23/06/18.
 */

public class WriteLog {
    public static boolean debug = BuildConfig.DEBUG;

    public static void d(String tag, String msg) {
        if (!debug)
            return;
        if(msg != null)
            Log.d(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (!debug)
            return;
        if(msg != null)
            Log.e(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (!debug)
            return;
        if(msg != null)
            Log.i(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (!debug)
            return;
        if(msg != null)
            Log.v(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (!debug)
            return;
        if(msg != null)
            Log.w(tag, msg);
    }

    /**
     * @param str Created by tuan.nguyen on 9/09/2016.
     */
    public static void LogMem(String str) {
        int usedMegs2 = (int) (Debug.getNativeHeapAllocatedSize() / 1048576L);
        int useMemKB = (int) (Debug.getNativeHeapAllocatedSize() / 1024L);
        WriteLog.e("NUS:", str + " memory :  " + usedMegs2 + "(" + useMemKB + " KB)");
    }
}
