package com.audio.administrator.ganhuo.modules.android.model

import com.audio.administrator.ganhuo.R
import com.audio.administrator.ganhuo.annotation.LayoutResId
import com.audio.administrator.ganhuo.base.Constant
import com.audio.administrator.ganhuo.modules.android.bean.EveryDayEntity
import com.audio.administrator.ganhuo.modules.android.bean.ResultsBean
import com.audio.administrator.ganhuo.modules.android.contract.IGankContract
import com.audio.administrator.ganhuo.net.api.Api
import com.audio.administrator.ganhuo.net.config.HttpManager
import com.audio.administrator.ganhuo.net.config.XObserver
import com.audio.administrator.ganhuo.net.config.ThreadHelp
import com.audio.administrator.ganhuo.ui.superier.ModelResponseAdapter
import io.reactivex.disposables.Disposable

/**
 * Author: Chris
 * Blog: https://www.jianshu.com/u/a3534a2292e8
 * Date: 2019/1/22
 * Description
 */
class GankModelImpl : IGankContract.IGankModel {
    override fun getGank(
        type: String,
        count: Int,
        page: Int,
        response: ModelResponseAdapter<ResultsBean, EveryDayEntity, String>
    ) {


        when (type) {
            Constant.ANDROID_TYPE -> HttpManager.request().get(Api::class.java).getAndroid(count, page)
            Constant.IOS_TYPE -> HttpManager.request().get(Api::class.java).getIOS(count, page)
            Constant.WELFARE_TYPE -> HttpManager.request().get(Api::class.java).getWelfare(count, page)
            else -> HttpManager.request().get(Api::class.java).getVideo(count, page)
        }
            .compose(ThreadHelp.toMain())
            .subscribe(object :
                XObserver<ResultsBean, EveryDayEntity>(getCacheUrl(type, count, page)) {

                override fun onEmptyStatusResponse() {
                    response.onEmptyStatusResponse()
                }

                override fun onRequesting(d: Disposable?, cache: MutableList<ResultsBean>) {
                    response.onRequesting(d, cache)
                }

                override fun onSuccess(key: String?, entity: EveryDayEntity) {
                    response.onSuccess(key, entity)
                }

                override fun onFailed(e: Throwable) {
                    response.onFailed(e.message)
                }
            })
    }

    private fun getCacheUrl(type: String, count: Int, page: Int): String {
        return when (type) {
            Constant.ANDROID_TYPE -> HttpManager.request().baseUrl + HttpManager.Interfaces.ANDROID + "/" + count + "/" + page
            Constant.IOS_TYPE -> HttpManager.request().baseUrl + HttpManager.Interfaces.IOS + "/" + count + "/" + page
            Constant.WELFARE_TYPE -> HttpManager.request().baseUrl + HttpManager.Interfaces.WELFARE + "/" + count + "/" + page
            else -> HttpManager.request().baseUrl + HttpManager.Interfaces.VIDEO + "/" + count + "/" + page
        }
    }
}