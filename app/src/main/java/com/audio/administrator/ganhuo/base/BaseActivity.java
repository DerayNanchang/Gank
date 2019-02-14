package com.audio.administrator.ganhuo.base;

import android.app.ProgressDialog;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import com.audio.administrator.ganhuo.annotation.YaoAnnotation;
import com.audio.administrator.ganhuo.manager.ActivityManager;
import com.audio.administrator.ganhuo.receiver.NetConnectReceiver;

/**
 * Author: Chris
 * Blog: https://www.jianshu.com/u/a3534a2292e8
 * Date: 2018/12/3
 * Description
 */
abstract public class BaseActivity extends AppCompatActivity {

    private ProgressDialog dialog;
    private NetConnectReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityManager.get().addActivity(this);
        initWindow();
        super.onCreate(savedInstanceState);
        registerNetReceiver();
        YaoAnnotation.INSTANCE.init(this);
        init();
    }

    protected void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 过渡动画
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
    }


    private void registerNetReceiver() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //实例化IntentFilter对象
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            receiver = new NetConnectReceiver();
            //注册广播接收
            registerReceiver(receiver, filter);
        }
    }


    protected abstract void init();



    protected void showDialog(String msg) {
        dialog = ProgressDialog.show(this, "", msg);
        dialog.show();
    }

    protected void showDialog() {
        String message = "正在加载数据";
        dialog = ProgressDialog.show(this, "", message);
        dialog.show();
    }


    protected void dismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissDialog();
        if (receiver != null) unregisterReceiver(receiver);
        ActivityManager.get().removeActivity(this);
    }
}
