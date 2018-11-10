package com.example.admin.ebuy.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.admin.ebuy.EBApplication;


/**
 * Created by tuan.nguyen on 23/06/18.
 */

public class PrefUtils {
    private static PrefUtils prefUtils;
    private SharedPreferences preferences;
    public static final String PUSH_TOKEN = "PUSH_TOKEN";
    public static final String CACHE_USER_INFO = "CACHE_USER_INFO";
    public static final String CACHE_TEACHER_INFO = "CACHE_TEACHER_INFO";

    public static final String CACHE_CONFIG = "CACHE_CONFIG";


    public static void savePreferences(Context activity, String key, String value) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public static String readPreferences(Context activity, String key, String defaultValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        return sp.getString(key, defaultValue);
    }
    private PrefUtils() {
    }
    public static PrefUtils getInstance() {
        if (prefUtils == null) {
            prefUtils = new PrefUtils();
            prefUtils.preferences = PreferenceManager.getDefaultSharedPreferences(EBApplication.getAppContext());
        }
        return prefUtils;
    }
    public String getString(String key, String defaultValue) {
        if (preferences == null)
            return "";

        return preferences.getString(key, defaultValue);
    }
    /**
     * Get String value from SharedPreferences at 'key'. If key not found, return ""
     *
     * @param key SharedPreferences key
     * @return String value at 'key' or "" (empty String) if key not found
     */
    public String getString(String key) {
        if (preferences == null)
            return "";

        return preferences.getString(key, "");
    }
    /**
     * Put String value into SharedPreferences with 'key' and save
     *
     * @param key   SharedPreferences key
     * @param value String value to be added
     */
    public void putString(String key, String value) {
        checkForNullKey(key);
        checkForNullValue(value);
        preferences.edit().putString(key, value).apply();
    }

    /**
     * null keys would corrupt the shared pref file and make them unreadable this is a preventive measure
     *
     * @param key pref key
     */
    public void checkForNullKey(String key) {
        if (key == null)
            throw new NullPointerException();
    }

    /**
     * null keys would corrupt the shared pref file and make them unreadable this is a preventive measure
     *
     * @param value pref key
     */
    public void checkForNullValue(String value) {
        if (value == null)
            throw new NullPointerException();
    }
}
