package com.audio.administrator.ganhuo.manager;

import android.app.Activity;

import java.util.HashSet;


/**
 * Author: Chris
 * Blog: https://www.jianshu.com/u/a3534a2292e8
 * Date: 2018/12/3
 * Description
 */
public class ActivityManager {

    private HashSet<Activity> hashSet = new HashSet();

    private ActivityManager() {
    }

    private static class getInstance {
        private static ActivityManager manager = new ActivityManager();
    }

    public static ActivityManager get() {
        return getInstance.manager;
    }

    /**
     * 每一个Activity 在 onCreate 方法的时候，可以装入当前this
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        try {
            hashSet.add(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeActivity(Activity activity) {
        try {
            hashSet.remove(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void killActivity(Activity activity) {
        if (activity != null) {
            removeActivity(activity);
            activity.finish();
        }
    }

    public void killActivity(Class<?> cls) {
        Activity targetActivity = null;
        for (Activity activity : hashSet) {
            if (activity.getClass().equals(cls)) {
                targetActivity = activity;
            }
        }
        if (targetActivity != null) {
            killActivity(targetActivity);
        }
    }

    /**
     * 调用此方法用于销毁所有的Activity，然后我们在调用此方法之前，调到登录的Activity
     */
    public void exit() {
        try {
            for (Activity activity : hashSet) {
                activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
