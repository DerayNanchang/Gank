package com.audio.administrator.ganhuo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * Author: Chris
 * Blog: https://www.jianshu.com/u/a3534a2292e8
 * Date: 2018/12/28
 * Description
 */
public class NetUtil {

    /**
     * 判断当前网络类型-1为未知网络0为没有网络连接1网络断开或关闭2为以太网3为WiFi4为2G5为3G6为4G
     */
    public static int getNetworkType(Context context) {
        ConnectivityManager connectMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectMgr.getActiveNetworkInfo();
        if (networkInfo == null) {
            /** 没有任何网络 */
            return 0;
        }
        if (!networkInfo.isConnected()) {
            /** 网络断开或关闭 */
            return 1;
        }
        if (networkInfo.getType() == ConnectivityManager.TYPE_ETHERNET) {
            /** 以太网网络 */
            return 2;
        } else if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            /** wifi网络，当激活时，默认情况下，所有的数据流量将使用此连接 */
            return 3;
        } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            /** 移动数据连接,不能与连接共存,如果wifi打开，则自动关闭 */
            switch (networkInfo.getSubtype()) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                case TelephonyManager.NETWORK_TYPE_IDEN:
                    /** 2G网络 */
                    return 4;
                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                case TelephonyManager.NETWORK_TYPE_EHRPD:
                case TelephonyManager.NETWORK_TYPE_HSPAP:
                    /** 3G网络 */
                    return 5;
                case TelephonyManager.NETWORK_TYPE_LTE:
                    /** 4G网络 */
                    return 6;
            }
        }
        /** 未知网络 */
        return -1;
    }

    private static String getDescByStatus(int status){
        switch (status){
            case 0:
                return  "暂无网络";
            case 1:
                return  "网络断开或关闭";
            case 2:
                return  "正在使用以太网络";
            case 3:
                return  "正在使用 WIFI 网络";
            case 4:
                return  "正在使用 2G 网络";
            case 5:
                return  "正在使用 3G 网络";
            case 6:
                return  "正在使用 4G 网络";
            default:
                return  "未知网络";
        }
    }

    public static String getNetworkDesc(Context context){
        return getDescByStatus(getNetworkType(context));
    }

    private static String getSimpleNetworkDesc(int status){
        switch (status){
            case 0: return  "暂无网络";
            case 1: return  "网络断开或关闭";
                default: return  "";
        }
    }

    private static int getSimpleNetworkStatus(int status){
        switch (status){
            case 0: case 1: return 404;   // 无网络
            case 2:case 3:case 4:case 5:case 6: return 200; // 有网络
            default:return  404;
        }
    }

    public static String getSimpleNetDesc(Context context){
        return getSimpleNetworkDesc(getNetworkType(context));
    }

    public static int getSimpleNetStatus(Context context){
        return getSimpleNetworkStatus(getNetworkType(context));
    }

    public static boolean isNetworkConnected(Context context){
        if (getSimpleNetworkStatus(getNetworkType(context)) == 200){
            return true;
        }else {
            return false;
        }
    }
}
