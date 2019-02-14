package com.audio.administrator.ganhuo.base

import android.text.TextUtils
import com.audio.administrator.ganhuo.db.manager.CacheManager
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


/**
 * Author: Chris
 * Blog: https://www.jianshu.com/u/a3534a2292e8
 * Date: 2018/12/16
 * Description  BasePresenterImpl 基础实现类
 */
abstract class BasePresenter<VIEW : IBaseView>(protected var view: VIEW?) {
    private var disposableList: CompositeDisposable? = null

    init {
        disposableList = CompositeDisposable()
    }

    protected open fun addDisposable(disposable: Disposable?) {
        disposable?.let {
            disposableList?.add(it)
        }
    }

    protected open fun <CACHE> setCache(key: String?, cache: CACHE): Boolean {
        // 缓存数据
        return if (!TextUtils.isEmpty(key)) {
            CacheManager.get.setCache(key!!, cache)
        } else {
            false
        }
    }

    open fun destroy() {
        // 1. 解除所有的订阅
        disposableList?.clear()
        disposableList = null
        // 2. 销毁 View 引用
        view = null
        // 3. 子类记得要销毁 model 层的引用
    }
}