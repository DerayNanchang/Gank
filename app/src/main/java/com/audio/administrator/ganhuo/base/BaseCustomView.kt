package com.audio.administrator.ganhuo.base

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.audio.administrator.ganhuo.annotation.LayoutResId

/**
 * Author: Chris
 * Blog: https://www.jianshu.com/u/a3534a2292e8
 * Date: 2018/12/4
 * Description
 */
abstract class BaseCustomView : RelativeLayout {

    protected var mOnItemListener: OnItemListener? = null

    constructor(context: Context?) : super(context) {
        initialize()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initialize()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialize()
    }

    private fun initialize() {
        View.inflate(context, javaClass.getAnnotation(LayoutResId::class.java).value, this)
        init()
    }



    protected abstract fun init()

    open fun setOnItemListener(onItemListener: OnItemListener) {
        this.mOnItemListener = onItemListener
    }

    interface OnItemListener {
        fun onClick(position: Int)
    }


}