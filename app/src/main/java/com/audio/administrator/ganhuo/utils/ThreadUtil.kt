package com.audio.administrator.audio.util

import android.os.Handler
import android.os.Looper

object ThreadUtil{
    private val handle  = Handler(Looper.getMainLooper())


    fun runOnUiThread(runnable: Runnable){
        handle.post(runnable)
    }


    fun runUiThread(f:() -> Unit){
        handle.post{f()}
    }
}