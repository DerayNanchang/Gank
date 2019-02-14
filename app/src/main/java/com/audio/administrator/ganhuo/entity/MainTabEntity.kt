package com.audio.administrator.ganhuo.entity

import com.flyco.tablayout.listener.CustomTabEntity

/**
 * Author: Chris
 * Blog: https://www.jianshu.com/u/a3534a2292e8
 * Date: 2019/1/21
 * Description
 */
class MainTabEntity(var title: String) : CustomTabEntity {

    override fun getTabUnselectedIcon() = 0

    override fun getTabSelectedIcon() = 0

    override fun getTabTitle() = title
}