package com.audio.administrator.ganhuo.modules.android.adapter

import android.view.ViewGroup
import com.alibaba.fastjson.JSON
import com.audio.administrator.ganhuo.base.BaseAdapter
import com.audio.administrator.ganhuo.base.BaseItemView
import com.audio.administrator.ganhuo.base.BaseViewHolder
import com.audio.administrator.ganhuo.modules.android.bean.ResultsBean
import com.audio.administrator.ganhuo.ui.widget.*

class GanHuoAdapter : BaseAdapter<ResultsBean>() {

    override fun getEntityViewType(position: Int): Int {
        val entity = body[position]
        entity.images?.apply {
            return when {
                size <= 0 -> //  没有图片
                    size
                size == 1 -> //  一张图片
                    size
                size == 2 -> //  两张图片
                    size
                else -> // 三张及三张以上的图片
                    3
            }
        }
        return super.getEntityViewType(position)
    }

    override fun onCreateBodyViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*, out BaseItemView<*>> {

        return when {
            viewType <= 0 -> {
                val itemGanHuoView = ItemGanHuoView(parent.context)
                itemGanHuoView.adapter = this
                GanHuoViewHolder(itemGanHuoView)
            }
            viewType == 1 ->{
                val itemGanHuoView = ItemGanHuoView1(parent.context)
                itemGanHuoView.adapter = this
                GanHuoView1Holder(itemGanHuoView)
            }
            viewType == 2 -> {
                val itemGanHuoView = ItemGanHuoView2(parent.context)
                itemGanHuoView.adapter = this
                GanHuoView2Holder(itemGanHuoView)
            }

            else -> {
                val itemGanHuoView = ItemGanHuoView3(parent.context)
                itemGanHuoView.adapter = this
                GanHuoView3Holder(itemGanHuoView)
            }
        }
    }

    override fun onBindBodyViewHolder(
        itemView: BaseItemView<ResultsBean>?,
        position: Int,
        viewType: Int
    ) {
        return when {
            viewType <= 0 -> {
                val itemAndroidView = itemView as ItemGanHuoView
                itemAndroidView.bindData(body[position], position)
            }
            viewType == 1 -> {
                val itemAndroidView = itemView as ItemGanHuoView1
                itemAndroidView.bindData(body[position], position)
            }
            viewType == 2 -> {
                val itemAndroidView = itemView as ItemGanHuoView2
                itemAndroidView.bindData(body[position], position)
            }
            else -> {
                val itemAndroidView = itemView as ItemGanHuoView3
                itemAndroidView.bindData(body[position], position)
            }
        }
    }


    class GanHuoViewHolder(itemView: ItemGanHuoView) : BaseViewHolder<ResultsBean, ItemGanHuoView>(itemView)
    class GanHuoView1Holder(itemView: ItemGanHuoView1) : BaseViewHolder<ResultsBean, ItemGanHuoView1>(itemView)
    class GanHuoView2Holder(itemView: ItemGanHuoView2) : BaseViewHolder<ResultsBean, ItemGanHuoView2>(itemView)
    class GanHuoView3Holder(itemView: ItemGanHuoView3) : BaseViewHolder<ResultsBean, ItemGanHuoView3>(itemView)
}