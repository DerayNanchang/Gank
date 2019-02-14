package com.audio.administrator.ganhuo.annotation

import android.app.Activity
import android.content.Context
import com.audio.administrator.ganhuo.R
import com.audio.administrator.ganhuo.utils.StatusBarUtils

/**
 * Author: Chris
 * Blog: https://www.jianshu.com/u/a3534a2292e8
 * Date: 2018/12/9
 * Description    注解逻辑处理类
 */
object YaoAnnotation {


    fun init(context: Context) {
        initView(context)
        initStatusColor(context)
    }



    /**
     *  setContentView() 逻辑处理
     */
    private fun initView(context: Context) {
        val clazz = context::class.java
        val method = clazz.getMethod("setContentView", Int::class.java)
        val layoutResID = clazz.getAnnotation(LayoutResId::class.java)
        layoutResID?.let {
            method.invoke(context as Activity, layoutResID.value)
        }
    }

    private fun initStatusColor(context: Context) {
        val activity = context as Activity
        val clazz = activity::class.java
        val annotation = clazz.getAnnotation(StatusBarColor::class.java)
        if (annotation != null) {
            StatusBarUtils.setStatusBarColor(activity, activity.resources.getColor(annotation.value))
        } else {
            // 默认设置灰色
            StatusBarUtils.setStatusBarColor(activity, activity.resources.getColor(R.color.colorAccent))
        }
    }
}