package com.example.admin.ebuy.model;

import com.bluelinelabs.logansquare.LoganSquare;
import com.example.admin.ebuy.util.PrefUtils;



/**
 * Created by lamthinh on 2/20/17.
 */

public class CurrentUser {
    public static final String TOKEN_NO_LOGIN = "TOKEN_NO_LOGIN";

    private static User mUserInfo;
    /**
     * User info will be gotten from cache
     *
     * @return
     */
    public static User getUserInfo() {
        if (mUserInfo == null || mUserInfo.getAccessToken() == null || mUserInfo.getAccessToken().isEmpty()) {
            try {
                mUserInfo = LoganSquare.parse(PrefUtils.getInstance().getString(PrefUtils.CACHE_USER_INFO), User.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (mUserInfo == null) mUserInfo = new User();
        return mUserInfo;
    }
    public static boolean isLogin() {
        try {
            return getUserInfo() != null
                    && getUserInfo().getAccessToken() != null
                    && !getUserInfo().getAccessToken().isEmpty();
        } catch (Exception e) {

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

    public static void setUserInfo(User userInfo) {
        mUserInfo = userInfo;
    }
    public static String getToken()
    {
        if (isLogin())
        {
            return "Bearer "+getUserInfo().getAccessToken();
        }else {
            if (!PrefUtils.getInstance().getString(TOKEN_NO_LOGIN).isEmpty())
            {
                return "Basic "+PrefUtils.getInstance().getString(TOKEN_NO_LOGIN);
            }
        }
        return "";
    }
}
