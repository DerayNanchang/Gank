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


    override fun isLoadingScrollAlpha(): Boolean {
        return true
    }

    override fun onCreateBodyViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*, out BaseItemView<*>> {

        return when {
            viewType <= 0 -> {
                val itemGanHuoView = ItemGanHuoView(parent.context)
                itemGanHuoView.adapter = this
                GanHuoViewHolder(itemGanHuoView)
            }
            viewType == 1 -> {
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

    var onItemView1ImgListener: OnItemView1ImgListener? = null
    var onItemView2ImgListener: OnItemView2ImgListener? = null
    var onItemView3ImgListener: OnItemView3ImgListener? = null

    var onItemView1ImgLongListener: OnItemView1ImgLongListener? = null
    var onItemView2ImgLongListener: OnItemView2ImgLongListener? = null
    var onItemView3ImgLongListener: OnItemView3ImgLongListener? = null

    fun setOnMItemView1ImgListener(onItemView1ImgListener: OnItemView1ImgListener) {
        this.onItemView1ImgListener = onItemView1ImgListener
    }

    fun setOnMItemView2ImgListener(onItemView2ImgListener: OnItemView2ImgListener) {
        this.onItemView2ImgListener = onItemView2ImgListener
    }

    fun setOnMItemView3ImgListener(onItemView3ImgListener: OnItemView3ImgListener) {
        this.onItemView3ImgListener = onItemView3ImgListener
    }

    interface OnItemView1ImgListener {
        fun itemImageListener(position: Int, data: ResultsBean)
    }

    interface OnItemView2ImgListener {
        fun itemImageListener(position: Int, data: ResultsBean, imgPosition: Int)
    }

    interface OnItemView3ImgListener {
        fun itemImageListener(position: Int, data: ResultsBean, imgPosition: Int)
    }


    fun setOnMItemView1ImgLongListener(onItemView1ImgListener: OnItemView1ImgLongListener) {
        this.onItemView1ImgLongListener = onItemView1ImgLongListener
    }

    fun setOnMItemView2ImgLongListener(onItemView2ImgListener: OnItemView2ImgLongListener) {
        this.onItemView2ImgLongListener = onItemView2ImgLongListener
    }

    fun setOnMItemView3ImgLongListener(onItemView3ImgListener: OnItemView3ImgLongListener) {
        this.onItemView3ImgLongListener = onItemView3ImgLongListener
    }

    interface OnItemView1ImgLongListener {
        fun itemImageListener(position: Int, data: ResultsBean)
    }

    interface OnItemView2ImgLongListener {
        fun itemImageListener(position: Int, data: ResultsBean, imgPosition: Int)
    }

    interface OnItemView3ImgLongListener {
        fun itemImageListener(position: Int, data: ResultsBean, imgPosition: Int)
    }


}