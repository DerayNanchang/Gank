package com.audio.administrator.ganhuo.utils;

import android.os.Handler;
import android.os.Looper;

/**
 * Author: Chris
 * Blog: https://www.jianshu.com/u/a3534a2292e8
 * Date: 2019/2/17
 * Description
 */
public class JThreadUtil {
    public static void runOnUiThread(Runnable runnable) {
        Handler handle = new Handler(Looper.getMainLooper());
        handle.post(runnable);
    }
}
