package com.audio.administrator.ganhuo.app

import android.app.Application
import android.content.Context
import com.audio.administrator.ganhuo.db.manager.DBManager
import com.audio.administrator.ganhuo.utils.NetUtil

/**
 * Author: Chris
 * Blog: https://www.jianshu.com/u/a3534a2292e8
 * Date: 2019/1/21
 * Description
 */
class GanHuoApplication : Application() {

    companion object JVM {
        lateinit var get: GanHuoApplication
    }

    override fun onCreate() {
        super.onCreate()
        get = this
        DBManager.get.init(get)
    }


    fun isNetConnect(): Boolean {
        return NetUtil.isNetworkConnected(get)
    }

}