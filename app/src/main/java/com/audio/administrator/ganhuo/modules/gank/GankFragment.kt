package com.audio.administrator.ganhuo.modules.gank

import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.audio.administrator.ganhuo.R
import com.audio.administrator.ganhuo.base.BaseFragment
import com.audio.administrator.ganhuo.base.Constant
import com.audio.administrator.ganhuo.modules.android.adapter.GanHuoAdapter
import com.audio.administrator.ganhuo.modules.android.bean.ResultsBean
import com.audio.administrator.ganhuo.modules.android.contract.IGankContract
import com.audio.administrator.ganhuo.modules.android.fragment.MyFootView
import com.audio.administrator.ganhuo.modules.android.presenter.GankPresenterImpl
import com.audio.administrator.ganhuo.modules.img.activity.PicasaViewerActivity
import com.audio.administrator.ganhuo.modules.webview.WebViewActivity
import com.audio.administrator.ganhuo.ui.view.InconstantView
import com.audio.administrator.ganhuo.utils.glide.GlideUtils
import com.bumptech.glide.Glide
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.fragment_gank.*
import org.jetbrains.anko.support.v4.startActivity

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
        initBody(spvContent)
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

        // 点击条目事件
        mAdapter.setOnItemClickListener { position, data ->
            WebViewActivity.loadUrl(activity, data.url, data.desc)
        }

        // 1张图片样式的点击事件
        mAdapter.setOnMItemView1ImgListener(object : GanHuoAdapter.OnItemView1ImgListener {
            override fun itemImageListener(position: Int, data: ResultsBean) {
                startActivity<PicasaViewerActivity>(Constant.SIZE to data.images.size, Constant.IMGS to data.images)
            }
        })

        mAdapter.setOnMItemView1ImgLongListener(object : GanHuoAdapter.OnItemView1ImgLongListener {
            override fun itemImageListener(position: Int, data: ResultsBean) {
                GlideUtils.downLoadImageNew(data.images[position])
            }
        })

        // 2张图片样式的点击事件
        mAdapter.setOnMItemView2ImgListener(object : GanHuoAdapter.OnItemView2ImgListener {
            override fun itemImageListener(position: Int, data: ResultsBean, imgPosition: Int) {

                startActivity<PicasaViewerActivity>(
                    Constant.SIZE to data.images.size,
                    Constant.POSITION to imgPosition,
                    Constant.IMGS to data.images
                )
            }
        })

        mAdapter.setOnMItemView2ImgLongListener(object : GanHuoAdapter.OnItemView2ImgLongListener {
            override fun itemImageListener(position: Int, data: ResultsBean, imgPosition: Int) {
                GlideUtils.downLoadImageNew(data.images[position])
            }
        })

        // 3张图片样式的点击事件
        mAdapter.setOnMItemView3ImgListener(object : GanHuoAdapter.OnItemView3ImgListener {
            override fun itemImageListener(position: Int, data: ResultsBean, imgPosition: Int) {
                startActivity<PicasaViewerActivity>(
                    Constant.SIZE to data.images.size,
                    Constant.POSITION to imgPosition,
                    Constant.IMGS to data.images
                )
            }
        })


        1111111
        mAdapter.setOnMItemView3ImgLongListener(object : GanHuoAdapter.OnItemView3ImgLongListener {
            override fun itemImageListener(position: Int, data: ResultsBean, imgPosition: Int) {
                GlideUtils.downLoadImageNew(data.images[position])
            }
        })


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