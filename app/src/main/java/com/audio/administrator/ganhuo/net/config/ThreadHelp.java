package com.audio.administrator.ganhuo.net.config;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Chris on 2018/4/18.
 */

public class ThreadHelp {

    public static <T> ObservableTransformer<T, T> toMain() {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
