package com.audio.administrator.ganhuo.annotation

/**
 * Author: Chris
 * Blog: https://www.jianshu.com/u/a3534a2292e8
 * Date: 2018/12/10
 * Description 设置状态栏颜色  默认为主题色
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class StatusBarColor(val value:Int)