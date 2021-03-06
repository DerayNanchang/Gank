package com.audio.administrator.ganhuo.ui.view

import android.content.Context
import android.util.AttributeSet
import com.audio.administrator.ganhuo.R
import com.audio.administrator.ganhuo.annotation.LayoutResId
import com.audio.administrator.ganhuo.base.HeadOrFootItemView
import kotlinx.android.synthetic.main.view_default_head.view.*

/**
 * Author: Chris
 * Blog: https://www.jianshu.com/u/a3534a2292e8
 * Date: 2019/1/22
 * Description
 */
@LayoutResId(R.layout.view_default_head)
class DefaultHeadView : HeadOrFootItemView<String> {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun setMsg(size: Int) {
        if (size == 0) {
            tvMsg.text = " 我也是有顶线的 "
        } else {
            tvMsg.text = " 正在加载数据 ... "
        }
    }
}