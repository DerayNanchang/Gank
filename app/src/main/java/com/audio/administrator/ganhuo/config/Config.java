package com.audio.administrator.ganhuo.config;

import android.os.Environment;
import com.audio.administrator.ganhuo.base.Constant;

import java.io.File;

/**
 * Author: Chris
 * Blog: https://www.jianshu.com/u/a3534a2292e8
 * Date: 2019/1/21
 * Description
 */
public class Config {
    public static final boolean isDebug = true;

    private Config() {
    }

    private static class Instance {
        private static Config config = new Config();
    }

    public static Config get(){
        return Instance.config;
    }

    public File createFile(File file) {
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }

    public File initGankDir() {
        return createFile(new File(Environment.getExternalStorageDirectory(), Constant.APP_NAME_EN));
    }

    public File initPhotoDir() {
        return createFile(new File(initGankDir(), Constant.PHOTO));
    }

    public File initApkDir() {
        return createFile(new File(initGankDir(), Constant.APK));
    }

    public File getDownLoadApkFile() {
        return new File(initApkDir().getAbsolutePath(), Constant.DOWNLOAD_NAME);
    }
}
