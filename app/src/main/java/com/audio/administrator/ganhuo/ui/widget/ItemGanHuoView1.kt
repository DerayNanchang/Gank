package com.audio.administrator.ganhuo.ui.widget

import android.content.Context
import android.util.AttributeSet
import com.audio.administrator.ganhuo.R
import com.audio.administrator.ganhuo.annotation.LayoutResId
import com.audio.administrator.ganhuo.base.BaseItemView
import com.audio.administrator.ganhuo.modules.android.adapter.GanHuoAdapter
import com.audio.administrator.ganhuo.modules.android.bean.ResultsBean
import com.audio.administrator.ganhuo.utils.glide.GlideUtils
import kotlinx.android.synthetic.main.item_android1.view.*

@LayoutResId(R.layout.item_android1)
class ItemGanHuoView1 : BaseItemView<ResultsBean> {
    lateinit var adapter: GanHuoAdapter

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    override fun bindData(data: ResultsBean, position: Int) {
        val list = data.images

        list?.let {
            GlideUtils.simple(img, it[0])
        }

        desc.text = data.desc
        owner.text = data.who
        time.text = data.publishedAt?.substring(0, 10)

        llItemAndroid1Root.setOnClickListener {
            adapter.itemClickListener.onItemClick(position, data)
        }
    }
}