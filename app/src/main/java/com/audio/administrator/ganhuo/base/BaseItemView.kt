package com.audio.administrator.ganhuo.base

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.audio.administrator.ganhuo.annotation.LayoutResId

/**
 * Author: Chris
 * Blog: https://www.jianshu.com/u/a3534a2292e8
 * Date: 2018/11/22
 * Description  所有 ItemView 的基类
 */

abstract class BaseItemView<DATA> : RelativeLayout {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        View.inflate(context, javaClass.getAnnotation(LayoutResId::class.java).value, this)
    }

    abstract fun bindData(data: DATA, position: Int)

    fun getEntitySize(){

    }

    private var itemClickListener: OnItemViewClickListener<DATA>? = null
    private var itemLongClickListener: OnItemViewLongClickListener? = null

    interface OnItemViewClickListener<DATA> {
        fun onItemViewClick(position: Int, data: DATA)
    }

    fun setOnItemViewClickListener(itemClickListener: OnItemViewClickListener<DATA>) {
        this.itemClickListener = itemClickListener
    }

    interface OnItemViewLongClickListener {
        fun onItemViewLongClick(position: Int)
    }

    fun setOnItemViewLongClickListener(itemLongClickListener: OnItemViewLongClickListener) {
        this.itemLongClickListener = itemLongClickListener
    }

}