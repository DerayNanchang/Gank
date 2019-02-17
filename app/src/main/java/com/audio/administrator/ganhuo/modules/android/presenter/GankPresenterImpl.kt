package com.audio.administrator.ganhuo.modules.android.presenter

import com.audio.administrator.ganhuo.base.BasePresenter
import com.audio.administrator.ganhuo.modules.android.bean.EveryDayEntity
import com.audio.administrator.ganhuo.modules.android.bean.ResultsBean
import com.audio.administrator.ganhuo.modules.android.contract.IGankContract
import com.audio.administrator.ganhuo.modules.android.model.GankModelImpl
import com.audio.administrator.ganhuo.ui.superier.ModelResponseAdapter
import io.reactivex.disposables.Disposable


/**
 * Author: Chris
 * Blog: https://www.jianshu.com/u/a3534a2292e8
 * Date: 2019/1/17
 * Description
 */
class GankPresenterImpl(view: IGankContract.IGankView) : BasePresenter<IGankContract.IGankView>(view),
    IGankContract.IGankPresenter {
    private var model = GankModelImpl()

    companion object {
        const val MODE_UPDATE = "update"
        const val MODE_MORE = "more"
        const val PAGE_SIZE = 10
    }


    override fun getGank(type: String, mode: String, count: Int, page: Int) {

        model.getGank(type, count, page, object : ModelResponseAdapter<ResultsBean, EveryDayEntity, String>() {
            override fun onEmptyStatusResponse() {
                super.onEmptyStatusResponse()
                view?.onEmptyStatusResponse(mode)
            }

            override fun onRequesting(disposable: Disposable?, cache: MutableList<ResultsBean>) {
                super.onRequesting(disposable, cache)
                view?.getGankSuccess(mode, cache, true)
            }

            override fun onSuccess(key: String?, result: EveryDayEntity) {
                super.onSuccess(key, result)
                if (!result.isError) {
                    if (result.results != null) {
                        if (result.results.size > 0){
                            if (setCache(key, result.results)){
                                view?.getGankSuccess(mode, result.results, false)
                            }
                        }else{
                            // 暂无数据
                            view?.onEmptyStatusResponse(mode)
                        }
                    }
                }
            }
        })
    }
}