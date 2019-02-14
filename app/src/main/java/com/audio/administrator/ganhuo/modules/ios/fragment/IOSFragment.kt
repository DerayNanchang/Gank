package com.audio.administrator.ganhuo.modules.ios.fragment

import com.audio.administrator.ganhuo.R
import com.audio.administrator.ganhuo.annotation.LayoutResId
import com.audio.administrator.ganhuo.base.Constant
import com.audio.administrator.ganhuo.modules.gank.GankFragment

@LayoutResId(R.layout.fragment_gank)
class IOSFragment : GankFragment() {

    override fun isLazyLoad() = true

    override fun getType() = Constant.IOS_TYPE

}