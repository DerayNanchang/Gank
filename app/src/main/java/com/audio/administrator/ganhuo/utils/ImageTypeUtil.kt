package com.audio.administrator.ganhuo.utils

import com.audio.administrator.ganhuo.base.Constant

object ImageTypeUtil {
    fun getAndroidType(list: List<String>?): Int {

        return if (list != null) {
            when {
                list.size == Constant.TYPE_ANDROID_IMG1 -> Constant.TYPE_ANDROID_IMG1
                list.size == Constant.TYPE_ANDROID_IMG2 -> Constant.TYPE_ANDROID_IMG2
                list.size == Constant.TYPE_ANDROID_IMG3 -> Constant.TYPE_ANDROID_IMG3
                else -> Constant.TYPE_ANDROID_IMG3
            }
        } else {
            Constant.TYPE_ANDROID_IMG3
        }
    }
}