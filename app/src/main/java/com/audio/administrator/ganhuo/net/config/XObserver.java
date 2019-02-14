package com.audio.administrator.ganhuo.net.config;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.audio.administrator.ganhuo.app.GanHuoApplication;
import com.audio.administrator.ganhuo.db.manager.CacheManager;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by Chris on 2018/4/18.
 */

public abstract class XObserver<CACHE, ENTITY> implements Observer<ENTITY> {

    private String key;

    public XObserver(String key) {
        this.key = key;
    }

    @Override
    public void onSubscribe(Disposable d) {

        String cacheJson = CacheManager.Companion.getGet().getCache(key);
        // 判断是否有网络
        if (GanHuoApplication.JVM.getGet().isNetConnect()) {
            // 有网络 是否有缓存
            if (!TextUtils.isEmpty(cacheJson)) {
                // 有缓存,先加载缓存，后去请求最新数据
                cacheParse(d, cacheJson);
            }
            // 无缓存，请求最新数据

        } else {
            // 无网络 是否有缓存
            if (!TextUtils.isEmpty(cacheJson)) {
                // 有缓存,加载缓存，然后去做请求
                cacheParse(d, cacheJson);
                // 不用去做请求了
                d.dispose();
            } else {
                // 无网络 无缓存
                // 显示空状态
                onEmptyStatusResponse();
                // 不用去做请求了
                d.dispose();
            }
        }
    }

    private void cacheParse(Disposable d, String cacheJson) {
        Class<CACHE> cacheClazz = (Class<CACHE>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

        List<CACHE> cache = JSON.parseArray(cacheJson, cacheClazz);
        // 返回数据
        onRequesting(d, cache);
    }

    @Override
    public void onNext(ENTITY data) {
        onSuccess(key, data);
    }


    @Override
    public void onComplete() {
    }


    @Override
    public void onError(Throwable e) {
        onFailed(e);
    }

    // 呈现空状态
    protected abstract void onEmptyStatusResponse();

    // 加载中
    protected abstract void onRequesting(Disposable d, List<CACHE> cache);

    // 加载成功
    protected abstract void onSuccess(String key, @NonNull ENTITY entity);

    // 加载失败
    protected abstract void onFailed(@NonNull Throwable e);
}
