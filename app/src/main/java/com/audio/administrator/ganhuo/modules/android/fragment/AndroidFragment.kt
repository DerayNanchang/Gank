package com.audio.administrator.ganhuo.modules.android.fragment

import com.audio.administrator.ganhuo.R
import com.audio.administrator.ganhuo.annotation.LayoutResId
import com.audio.administrator.ganhuo.base.Constant
import com.audio.administrator.ganhuo.modules.gank.GankFragment

@LayoutResId(R.layout.fragment_gank)
class AndroidFragment : GankFragment() {

    override fun isLazyLoad() = true

    override fun getType() = Constant.ANDROID_TYPE

}



