package com.allydata.ymc.ymcapplication.base;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Administrator on 2016/5/5 0005.
 * 易买车 配置类
 */
public class YmcConfig {
    /**
     * true 为debug状态，打印日志;false为上线发布状态
     */
    public static boolean IS_DEBUG = true;

    /**
     * 测试环境
     */
    public static final int TEST_TYPE = 0;
    /**
     * 生产环境
     */
    public static final int PRODUCT_TYPE = 1;
    /**
     * 通过修改该常量改变测试|生产环境
     */
    public static int RELEASE_TYPE = PRODUCT_TYPE;

    /**
     * 测试环境根路径
     */
    public static final String SERVER_ROOT_TEST = "";
    /**
     * 生产版本根路径
     */
    public static final String SERVER_ROOT_PRODUCT = "";

    public static String getServerUrl(String func) {
        StringBuilder serverUrl = new StringBuilder("");
        if (RELEASE_TYPE == PRODUCT_TYPE) {// 正式环境
            serverUrl.append(SERVER_ROOT_PRODUCT);
            serverUrl.append(func);
        } else if (RELEASE_TYPE == TEST_TYPE) {// 测试环境
            serverUrl.append(SERVER_ROOT_TEST);
            serverUrl.append(func);
        }
        return serverUrl.toString();

    }


    /***
     * 判断网络连接是否可用     true:网络可用  ; false:网络不可用。
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
        } else {
            //如果仅仅是用来判断网络连接
            //则可以使用 cm.getActiveNetworkInfo().isAvailable();
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
