package com.audio.administrator.ganhuo.ui.widget

import android.content.Context
import android.util.AttributeSet
import com.alibaba.fastjson.JSON
import com.audio.administrator.ganhuo.R
import com.audio.administrator.ganhuo.annotation.LayoutResId
import com.audio.administrator.ganhuo.base.BaseItemView
import com.audio.administrator.ganhuo.modules.android.bean.ResultsBean
import com.audio.administrator.ganhuo.modules.welfare.adapter.WelfareAdapter
import com.audio.administrator.ganhuo.utils.glide.GlideUtils
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_welfare_view.view.*

/**
 * Author: Chris
 * Blog: https://www.jianshu.com/u/a3534a2292e8
 * Date: 2019/2/17
 * Description
 */
@LayoutResId(R.layout.item_welfare_view)
class ItemWelfareView : BaseItemView<ResultsBean> {
    lateinit var adapter: WelfareAdapter

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun bindData(data: ResultsBean, position: Int) {
        GlideUtils.noDefault(ivPeri, data.url)
        ivPeri.setOnClickListener {
            adapter.onImageViewClickListener.onImageViewClick(position, data)
        }
    }
}