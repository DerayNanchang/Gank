package com.audio.administrator.ganhuo.utils;

import com.audio.administrator.ganhuo.app.GanHuoApplication;

/**
 * Author: Chris
 * Blog: https://www.jianshu.com/u/a3534a2292e8
 * Date: 2018/12/5
 * Description
 */
public class Toast {
    private static String oldMes = "";
    private static long timeMillis;
    private static final long DURATION = 2000;

    public static void show(String msg) {
        if (!msg.equals(oldMes)) {
            showToast(msg);
            timeMillis = System.currentTimeMillis();
        } else {
            if (System.currentTimeMillis() - timeMillis > DURATION) {
                showToast(msg);
                timeMillis = System.currentTimeMillis();
            }
        }
        oldMes = msg;
    }

    private static void showToast(String msg) {
        android.widget.Toast.makeText(GanHuoApplication.get, msg, android.widget.Toast.LENGTH_SHORT).show();
    }
}
