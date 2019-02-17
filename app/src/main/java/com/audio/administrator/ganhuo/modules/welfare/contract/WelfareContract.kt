package com.audio.administrator.ganhuo.modules.welfare.contract

import com.audio.administrator.ganhuo.base.IBaseView
import com.audio.administrator.ganhuo.modules.android.bean.EveryDayEntity
import com.audio.administrator.ganhuo.modules.android.bean.ResultsBean
import com.audio.administrator.ganhuo.ui.superier.ModelResponseAdapter

/**
 * Author: Chris
 * Blog: https://www.jianshu.com/u/a3534a2292e8
 * Date: 2019/2/17
 * Description
 */
interface WelfareContract {

    interface IWelfareView : IBaseView {
        fun onEmptyStatusResponse(mode: String)
        fun onPeriPicture(mode: String, everyDayEntity: MutableList<ResultsBean>, isCache: Boolean)

    }

    interface IWelfarePresenter {
        fun getPeriPicture(mode: String, count: Int, page: Int)
    }

    interface IWelfareMode {
        fun getPeriPicture(
            count: Int,
            page: Int,
            response: ModelResponseAdapter<ResultsBean, EveryDayEntity, String>
        )
    }
}