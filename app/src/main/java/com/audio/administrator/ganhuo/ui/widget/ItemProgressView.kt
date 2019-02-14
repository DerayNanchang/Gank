package com.audio.administrator.ganhuo.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.audio.administrator.ganhuo.R
import kotlinx.android.synthetic.main.item_progress.view.*

class ItemProgressView : RelativeLayout{
    private val view = View.inflate(context, R.layout.item_progress, this)

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun setMsg(msg : String){
        view.msg.text = msg
    }
}