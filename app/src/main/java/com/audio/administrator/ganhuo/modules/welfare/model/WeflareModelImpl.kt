package com.audio.administrator.ganhuo.modules.welfare.model

import com.audio.administrator.ganhuo.modules.android.bean.EveryDayEntity
import com.audio.administrator.ganhuo.modules.android.bean.ResultsBean
import com.audio.administrator.ganhuo.modules.welfare.contract.WelfareContract
import com.audio.administrator.ganhuo.net.api.Api
import com.audio.administrator.ganhuo.net.config.HttpManager
import com.audio.administrator.ganhuo.net.config.ThreadHelp
import com.audio.administrator.ganhuo.net.config.XObserver
import com.audio.administrator.ganhuo.ui.superier.ModelResponseAdapter
import io.reactivex.disposables.Disposable
import org.jetbrains.annotations.Contract

/**
 * Author: Chris
 * Blog: https://www.jianshu.com/u/a3534a2292e8
 * Date: 2019/2/17
 * Description
 */
class WeflareModelImpl : WelfareContract.IWelfareMode {
    override fun getPeriPicture(
        count: Int,
        page: Int,
        response: ModelResponseAdapter<ResultsBean, EveryDayEntity, String>
    ) {
        HttpManager.request().get(Api::class.java)
            .getWelfare(count, page)
            .compose(ThreadHelp.toMain())
            .subscribe(object :
                XObserver<ResultsBean, EveryDayEntity>(HttpManager.request().baseUrl + HttpManager.Interfaces.WELFARE + "/" + count + "/" + page) {
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

}