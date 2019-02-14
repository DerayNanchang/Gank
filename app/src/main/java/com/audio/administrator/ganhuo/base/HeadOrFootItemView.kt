package com.audio.administrator.ganhuo.base

import android.content.Context
import android.util.AttributeSet

/**
 * Author: Chris
 * Blog: https://www.jianshu.com/u/a3534a2292e8
 * Date: 2019/1/22
 * Description
 */

abstract class HeadOrFootItemView<DATA> : BaseItemView<DATA> {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    abstract fun setMsg(size: Int)

    override fun bindData(data: DATA, position: Int) {
        // 复写bindData 对于 head 与 foot 是没有数据内容填充的,他们不用关心数据内容，只关心有没有数据（size）
    }
}