package com.audio.administrator.ganhuo.utils

import android.content.Context
import android.view.View
import com.audio.administrator.ganhuo.ui.widget.ItemGanHuoView
import com.audio.administrator.ganhuo.ui.widget.ItemGanHuoView2
import com.audio.administrator.ganhuo.ui.widget.ItemGanHuoView3

object ItemViewManager{
    fun getAndroidItemView(type: Int, context: Context?) : View{
        return when(type){
            1-> ItemGanHuoView(context)
            2-> ItemGanHuoView2(context)
            3-> ItemGanHuoView3(context)
            else -> ItemGanHuoView3(context)
        }
    }
}