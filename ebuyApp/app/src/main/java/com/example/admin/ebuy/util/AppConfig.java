package com.example.admin.ebuy.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by tuan.nguyen on 23/06/18.
 */

public class AppConfig {
    public static String apiEndpoint = "http://google.com";
    public static String AloAppFolderImageEdited="";
    public static final int DEFAULT_FIRST_PAGE = 1;
    public static final int SUCCESS_CODE = 200;
    public static final int AUT_CODE = 401;
    public static final String BROADCAST_RECEIVE_ORDER="BROADCAST_RECEIVE_ORDER";
    public static final String BROADCAST_CLOSE_POPUP="BROADCAST_CLOSE_POPUP";

    public static final String ADDRESS = "ADDRESS";
    public static final String ORDER_ID = "ORDER_ID";
    public static final String CODE = "CODE";
    public static final String NOTIFICATION_ID = "NOTIFICATION_ID";
    public static final String BROADCAST_UPDATE_PAGE = "UPDATE_PAGE";
    public static final String BROADCAST_NOTIFICATION = "BROADCAST_NOTIFICATION";

    public static void loadConfig(Context context) {
        Resources resources = context.getResources();
        AssetManager assetManager = resources.getAssets();

        try {
            InputStream inputStream = assetManager.open("appconfig.properties");

            Properties properties = new Properties();
            properties.load(inputStream);
            apiEndpoint = properties.getProperty("API_ENDPOINT");
            AloAppFolderImageEdited=properties.getProperty("FRAME_IMG_EDITED");
            WriteLog.e("TTT","open");
        } catch (IOException e) {
            System.err.println("Failed to open property file");
            WriteLog.e("TTT","erro open");
            e.printStackTrace();
        }
    }

    public static String getApiEndpoint() {
        return apiEndpoint;
    }

    public static String getAloAppFolderImageEdited() {
        return AloAppFolderImageEdited;
    }

    public static void setAloAppFolderImageEdited(String aloAppFolderImageEdited) {
        AloAppFolderImageEdited = aloAppFolderImageEdited;
    }
}
