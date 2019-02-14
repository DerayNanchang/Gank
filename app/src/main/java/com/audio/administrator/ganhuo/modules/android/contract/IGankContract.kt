package com.audio.administrator.ganhuo.modules.android.contract

import com.audio.administrator.ganhuo.base.IBaseView
import com.audio.administrator.ganhuo.modules.android.bean.EveryDayEntity
import com.audio.administrator.ganhuo.modules.android.bean.ResultsBean
import com.audio.administrator.ganhuo.ui.superier.ModelResponseAdapter

/**
 * Author: Chris
 * Blog: https://www.jianshu.com/u/a3534a2292e8
 * Date: 2019/1/17
 * Description
 */
interface IGankContract {


    interface IGankView : IBaseView {
        fun onEmptyStatusResponse(mode: String)
        fun getGankSuccess(mode: String, everyDayEntity: MutableList<ResultsBean>, isCache: Boolean)
    }

    interface IGankPresenter {
        fun getGank(type: String, mode: String, count: Int, page: Int)
    }

    interface IGankModel {
        fun getGank(type: String,count: Int, page: Int, response: ModelResponseAdapter<ResultsBean, EveryDayEntity, String>)
    }
}