package com.audio.administrator.ganhuo.db.manager

import android.text.TextUtils
import com.alibaba.fastjson.JSON
import com.audio.administrator.ganhuo.db.bean.Cache
import com.audio.administrator.ganhuo.db.dao.CacheDao
import com.audio.administrator.ganhuo.modules.android.bean.ResultsBean
import retrofit2.http.GET

/**
 * Author: Chris
 * Blog: https://www.jianshu.com/u/a3534a2292e8
 * Date: 2019/1/21
 * Description
 */
class CacheManager private constructor() {

    companion object {
        val get = CacheManager()
        private const val DAY_MILLISCOND = 24 * 60 * 60 * 1000
        private const val FOR_EVER = -1L                        // 永久
        private const val THREE_DAYS = 3 * DAY_MILLISCOND       // 3 天
        private const val Seven_Days = 7 * DAY_MILLISCOND       // 7 天
        private const val THIRTY_DAYS = 30 * DAY_MILLISCOND      // 30 天
        private const val M_180_DAYS = 180 * DAY_MILLISCOND     // 180 天
    }

    private fun getDayMilliscond() = 24 * 60 * 60 * 1000

    fun forEver(): Long {
        return FOR_EVER
    }

    fun <ENTITY> setCache(key: String, entity: ENTITY): Boolean {
        val cache = Cache(key, JSON.toJSONString(entity), System.currentTimeMillis(), FOR_EVER)
        val cacheJSON = getCache(key)
        // 返回是否插入了，前台凭这个判断是否需要更新数据
        return if (!TextUtils.isEmpty(cacheJSON)) {
            if (cacheJSON == cache.jsonEntity) {
                // 如果两次的数据一致，说明没有数据更新，就不插入了，也不刷新页面了
                false
            } else {
                // 数据不一致,就更新插入数据
                DBManager.get.getCacheDao().insertOrReplace(cache)
                true
            }
        } else {
            // 第一次插入
            DBManager.get.getCacheDao().insertOrReplace(cache)
            true
        }
    }

    fun getCache(key: String): String {
        val cache = DBManager.get.getCacheDao().queryBuilder().where(CacheDao.Properties.Key.eq(key)).unique()
        return if (cache != null) {
            // 有缓存
            cache.jsonEntity
        } else {
            // 无缓存
            ""
        }
    }

}