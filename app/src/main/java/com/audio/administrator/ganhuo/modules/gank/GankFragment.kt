package com.audio.administrator.ganhuo.modules.gank

import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.webkit.WebView
import com.audio.administrator.ganhuo.R
import com.audio.administrator.ganhuo.base.BaseFragment
import com.audio.administrator.ganhuo.base.Constant
import com.audio.administrator.ganhuo.modules.android.adapter.GanHuoAdapter
import com.audio.administrator.ganhuo.modules.android.bean.ResultsBean
import com.audio.administrator.ganhuo.modules.android.contract.IGankContract
import com.audio.administrator.ganhuo.modules.android.fragment.MyFootView
import com.audio.administrator.ganhuo.modules.android.presenter.GankPresenterImpl
import com.audio.administrator.ganhuo.modules.webview.WebViewActivity
import com.audio.administrator.ganhuo.ui.view.InconstantView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.fragment_gank.*

/**
 * Author: Chris
 * Blog: https://www.jianshu.com/u/a3534a2292e8
 * Date: 2019/2/14
 * Description
 */
abstract class GankFragment : BaseFragment(), IGankContract.IGankView {

    private lateinit var presenter: GankPresenterImpl
    private lateinit var mAdapter: GanHuoAdapter
    private lateinit var refreshLayoutView: SmartRefreshLayout
    private var androidPage = 1
    private var iosPage = 1
    private var welfarePage = 1
    private var videoPage = 1

    override fun isLazyLoad(): Boolean {
        return true
    }

    override fun init() {
        initView()
        initData()
    }

    private fun initView() {
        initBody()
        inRefresh()
    }


    private fun inRefresh() {
        refreshLayout?.let {
            refreshLayoutView = refreshLayout
            refreshLayoutView.setEnableLoadMore(false)
            refreshLayoutView.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white)
            refreshLayoutView.autoRefresh()
            refreshLayoutView.setOnRefreshListener {
                updateData()
            }
        }
    }

    private fun initData() {


        presenter = GankPresenterImpl(this)
        mAdapter = GanHuoAdapter()
        mAdapter.addFootView(MyFootView(context))
        spvContent?.let {
            val view = spvContent.getContent()
            view?.apply {
                val recyclerView = view as RecyclerView
                recyclerView.apply {
                    adapter = mAdapter
                    layoutManager = LinearLayoutManager(context)
                    itemAnimator = DefaultItemAnimator()
                    mAdapter.scrollMoreEntity(this) {
                        when (getType()) {
                            Constant.ANDROID_TYPE -> presenter.getGank(
                                getType(),
                                GankPresenterImpl.MODE_MORE,
                                GankPresenterImpl.PAGE_SIZE,
                                ++androidPage
                            )
                            Constant.IOS_TYPE -> presenter.getGank(
                                getType(),
                                GankPresenterImpl.MODE_MORE,
                                GankPresenterImpl.PAGE_SIZE,
                                ++iosPage
                            )
                            Constant.WELFARE_TYPE -> presenter.getGank(
                                getType(),
                                GankPresenterImpl.MODE_MORE,
                                GankPresenterImpl.PAGE_SIZE,
                                ++welfarePage
                            )
                            Constant.VIDEO_TYPE -> presenter.getGank(
                                getType(),
                                GankPresenterImpl.MODE_MORE,
                                GankPresenterImpl.PAGE_SIZE,
                                ++videoPage
                            )
                        }
                    }
                }
            }
            updateData()
        }

        mAdapter.setOnItemClickListener { position, data ->
            WebViewActivity.loadUrl(activity, data.url, data.desc)
        }
    }

    private fun updateData() {

        when (getType()) {
            Constant.ANDROID_TYPE -> presenter.getGank(
                getType(),
                GankPresenterImpl.MODE_UPDATE,
                GankPresenterImpl.PAGE_SIZE,
                androidPage
            )
            Constant.IOS_TYPE -> presenter.getGank(
                getType(),
                GankPresenterImpl.MODE_UPDATE,
                GankPresenterImpl.PAGE_SIZE,
                iosPage
            )
            Constant.WELFARE_TYPE -> presenter.getGank(
                getType(),
                GankPresenterImpl.MODE_UPDATE,
                GankPresenterImpl.PAGE_SIZE,
                welfarePage
            )
            Constant.VIDEO_TYPE -> presenter.getGank(
                getType(),
                GankPresenterImpl.MODE_UPDATE,
                GankPresenterImpl.PAGE_SIZE,
                videoPage
            )
        }
    }


    override fun getGankSuccess(mode: String, everyDayEntity: MutableList<ResultsBean>, isCache: Boolean) {
        refreshLayoutView.finishRefresh()
        if (mode == GankPresenterImpl.MODE_UPDATE) {
            // 更新数据
            mAdapter.updateData(everyDayEntity)
        } else {
            // 下拉加载更多
            mAdapter.moreData(everyDayEntity)
        }
        spvContent?.let {
            spvContent.setBodyTransform(InconstantView.Type.CONTENT)
        }
    }

    override fun msg(msg: Int): String {
        return getString(msg)
    }

    private fun initBody() {
        // 添加空状态与无网络
        spvContent?.let {
            spvContent.addContent(R.layout.view_default_content)
            spvContent.addEmptyState(R.layout.view_default_empty_state)
            spvContent.addNoConnect(R.layout.view_default_no_connect)
            spvContent.addLoading(R.layout.view_custom_wrap_progress)
            spvContent.setBodyTransform(InconstantView.Type.LOADING)
        }

    }

    override fun onEmptyStatusResponse(mode: String) {
        refreshLayoutView.finishRefresh()
        if (mode == GankPresenterImpl.MODE_UPDATE) {
            spvContent.let {
                spvContent.setBodyTransform(InconstantView.Type.EMPTY_STATE)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.destroy()
    }

    protected abstract fun getType(): String
}