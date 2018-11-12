package com.example.admin.ebuy.util;

import com.bluelinelabs.logansquare.LoganSquare;
import com.example.admin.ebuy.model.User;
import com.example.admin.ebuy.model.Config;

import java.io.IOException;



/**
 * Created by tuan.nguyen on 23/06/18.
 */

public class ManagementCacheObject {
    private static User mUserInfo;

    /**
     * User info will be gotten from cache
     *
     * @return
     */
    public static User getUserInfo(){
        if(mUserInfo == null || mUserInfo.getToken() == null || mUserInfo.getToken().isEmpty()){
            try {
                mUserInfo = LoganSquare.parse(PrefUtils.getInstance().getString(PrefUtils.CACHE_USER_INFO),User.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(mUserInfo == null) mUserInfo = new User();
        return mUserInfo;
    }

    /**
     * set information of current user login app
     * @param userInfo
     */
    public static void setUserInfo(User userInfo) {
        mUserInfo = userInfo;
    }

    /**
     * Check User login
     */
    public static boolean isLogin(){
        try {
            return getUserInfo() != null && getUserInfo().getToken() != null && !getUserInfo().getToken().isEmpty();
        }catch (Exception e){

        }
        return false;
    }

    /**
     * Store new user info into cache
     */
    public static void saveUserInfo(User userInfo) {
        try {
            //Luu thong tin user vao cache
            if(userInfo!=null)
                PrefUtils.getInstance().putString(PrefUtils.CACHE_USER_INFO, LoganSquare.serialize(userInfo));
            else
                PrefUtils.getInstance().putString(PrefUtils.CACHE_USER_INFO, "");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Store new config   into cache
     */
    public static void saveConfig(Config config) {
        try {
            if(config!=null)
                PrefUtils.getInstance().putString(PrefUtils.CACHE_CONFIG, LoganSquare.serialize(config));
            else
                PrefUtils.getInstance().putString(PrefUtils.CACHE_CONFIG, "");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Config getConfig(){
            try {
                return  LoganSquare.parse(PrefUtils.getInstance().getString(PrefUtils.CACHE_CONFIG), Config.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        return new Config();
    }

}
