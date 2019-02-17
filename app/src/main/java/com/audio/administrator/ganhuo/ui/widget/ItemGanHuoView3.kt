package com.audio.administrator.ganhuo.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.audio.administrator.ganhuo.R
import com.audio.administrator.ganhuo.annotation.LayoutResId
import com.audio.administrator.ganhuo.base.BaseItemView
import com.audio.administrator.ganhuo.modules.android.adapter.GanHuoAdapter
import com.audio.administrator.ganhuo.modules.android.bean.ResultsBean
import com.audio.administrator.ganhuo.utils.glide.GlideUtils
import kotlinx.android.synthetic.main.item_android3.view.*

// 主构造方法
@LayoutResId(R.layout.item_android3)
class ItemGanHuoView3 : BaseItemView<ResultsBean> {
    lateinit var adapter: GanHuoAdapter


    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun bindData(data: ResultsBean, position: Int) {
        val list = data.images
        list?.let {
            GlideUtils.simple(img, it[0])
            GlideUtils.simple(img2, it[1])
            GlideUtils.simple(img3, it[2])
        }
        desc.text = data.desc
        owner.text = data.who
        time.text = data.publishedAt?.substring(0, 10)

        llItemAndroid3Root.setOnClickListener {
            adapter.itemClickListener.onItemClick(position, data)
        }

        img.setOnClickListener {
            adapter.onItemView3ImgListener?.itemImageListener(position, data, 0)
        }

        img2.setOnClickListener {
            adapter.onItemView3ImgListener?.itemImageListener(position, data, 1)
        }

        img3.setOnClickListener {
            adapter.onItemView3ImgListener?.itemImageListener(position, data, 2)
        }
    }

}