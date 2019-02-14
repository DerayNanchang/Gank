package com.audio.administrator.ganhuo.modules.android.fragment

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.audio.administrator.ganhuo.R
import com.audio.administrator.ganhuo.annotation.LayoutResId
import com.audio.administrator.ganhuo.ui.view.CustomHeadOrFootView
import kotlinx.android.synthetic.main.view_default_head.view.*

/**
 * Author: Chris
 * Blog: https://www.jianshu.com/u/a3534a2292e8
 * Date: 2019/1/25
 * Description
 */
@LayoutResId(R.layout.view_default_head)
class MyFootView : CustomHeadOrFootView {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun setMsg(size: Int) {
        if (size == 0) {
            pbBar.visibility = View.GONE
            tvMsg.text = "___ 我是有底线的 ___"

        } else {
            pbBar.visibility = View.VISIBLE
            tvMsg.text = " 正在加载数据 ..."
        }
    }
}