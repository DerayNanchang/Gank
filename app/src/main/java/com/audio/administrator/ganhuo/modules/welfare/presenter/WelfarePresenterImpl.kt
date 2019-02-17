package com.audio.administrator.ganhuo.modules.welfare.presenter

import com.audio.administrator.ganhuo.base.BasePresenter
import com.audio.administrator.ganhuo.modules.android.bean.EveryDayEntity
import com.audio.administrator.ganhuo.modules.android.bean.ResultsBean
import com.audio.administrator.ganhuo.modules.welfare.adapter.WelfareAdapter
import com.audio.administrator.ganhuo.modules.welfare.contract.WelfareContract
import com.audio.administrator.ganhuo.modules.welfare.fragment.WelfareFragment
import com.audio.administrator.ganhuo.modules.welfare.model.WeflareModelImpl
import com.audio.administrator.ganhuo.ui.superier.ModelResponseAdapter
import io.reactivex.disposables.Disposable

/**
 * Author: Chris
 * Blog: https://www.jianshu.com/u/a3534a2292e8
 * Date: 2019/2/17
 * Description
 */
class WelfarePresenterImpl(view: WelfareContract.IWelfareView) : BasePresenter<WelfareContract.IWelfareView>(view),
    WelfareContract.IWelfarePresenter {

    private var model = WeflareModelImpl()

    companion object {
        const val MODE_UPDATE = "update"
        const val MODE_MORE = "more"
        const val PAGE_SIZE = 5
    }

    override fun getPeriPicture(mode: String, count: Int, page: Int) {
        model.getPeriPicture(count, page, object : ModelResponseAdapter<ResultsBean, EveryDayEntity, String>() {
            override fun onEmptyStatusResponse() {
                super.onEmptyStatusResponse()
                view?.onEmptyStatusResponse(mode)
            }

            override fun onRequesting(disposable: Disposable?, cache: MutableList<ResultsBean>) {
                super.onRequesting(disposable, cache)
                view?.onPeriPicture(mode, cache, true)
            }

            override fun onSuccess(key: String?, result: EveryDayEntity) {
                super.onSuccess(key, result)
                if (!result.isError) {
                    if (result.results.size > 0) {
                        // 有数据
                        if (setCache(key, result.results)) {
                            view?.onPeriPicture(mode, result.results, false)
                        }

                    } else {
                        // 无数据
                        view?.onEmptyStatusResponse(mode)
                    }
                }
            }
        })
    }


}