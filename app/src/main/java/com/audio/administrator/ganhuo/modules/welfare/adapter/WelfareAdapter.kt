package com.audio.administrator.ganhuo.modules.welfare.adapter

import android.view.ViewGroup
import com.audio.administrator.ganhuo.base.BaseItemView
import com.audio.administrator.ganhuo.base.BaseViewHolder
import com.audio.administrator.ganhuo.base.SimpleAdapter
import com.audio.administrator.ganhuo.modules.android.bean.ResultsBean
import com.audio.administrator.ganhuo.ui.widget.ItemWelfareView

/**
 * Author: Chris
 * Blog: https://www.jianshu.com/u/a3534a2292e8
 * Date: 2019/2/17
 * Description
 */
class WelfareAdapter : SimpleAdapter<ResultsBean>() {
    override fun onCreateBodyViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*, out BaseItemView<*>> {
        val welfareView = ItemWelfareView(parent.context)
        welfareView.adapter = this
        return WelfareViewHolder(welfareView)
    }

    override fun isLoadingScrollAlpha() = false

    class WelfareViewHolder(itemView: ItemWelfareView) : BaseViewHolder<ResultsBean, ItemWelfareView>(itemView)

    lateinit var onImageViewClickListener: OnImageViewClickListener

    lateinit var onImageViewLongClickListener: OnImageViewLongClickListener


    fun setOnMImageViewClickListener(onImageViewClickListener: OnImageViewClickListener) {
        this.onImageViewClickListener = onImageViewClickListener
    }

    interface OnImageViewClickListener {
        fun onImageViewClick(position: Int, entity: ResultsBean)
    }

    fun setOnMImageViewLongClickListener(onImageViewLongClickListener: OnImageViewLongClickListener) {
        this.onImageViewLongClickListener = onImageViewLongClickListener
    }

    interface OnImageViewLongClickListener {
        fun onImageViewClick(position: Int, entity: ResultsBean)
    }
}