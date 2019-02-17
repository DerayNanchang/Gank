package com.audio.administrator.ganhuo.modules.welfare.fragment

import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.audio.administrator.ganhuo.R
import com.audio.administrator.ganhuo.annotation.LayoutResId
import com.audio.administrator.ganhuo.base.BaseFragment
import com.audio.administrator.ganhuo.base.Constant
import com.audio.administrator.ganhuo.modules.android.bean.ResultsBean
import com.audio.administrator.ganhuo.modules.img.activity.PicasaViewerActivity
import com.audio.administrator.ganhuo.modules.welfare.adapter.WelfareAdapter
import com.audio.administrator.ganhuo.modules.welfare.contract.WelfareContract
import com.audio.administrator.ganhuo.modules.welfare.presenter.WelfarePresenterImpl
import com.audio.administrator.ganhuo.ui.view.InconstantView
import com.audio.administrator.ganhuo.utils.glide.GlideUtils
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.fragment_welfare.*
import org.jetbrains.anko.support.v4.startActivity

@LayoutResId(R.layout.fragment_welfare)
class WelfareFragment : BaseFragment(), WelfareContract.IWelfareView {


    private lateinit var welfareAdapter: WelfareAdapter
    private lateinit var mPresenter: WelfarePresenterImpl
    private lateinit var refreshLayoutView: SmartRefreshLayout
    private lateinit var mAdapter: WelfareAdapter
    private var page = 1

    override fun init() {
        initView()
        initData()
        initEvent()
    }

    private fun initEvent() {
        mAdapter.setOnMImageViewClickListener(object : WelfareAdapter.OnImageViewClickListener {
            override fun onImageViewClick(position: Int, entity: ResultsBean) {
                val img = ArrayList<String>()
                img.add(entity.url)
                startActivity<PicasaViewerActivity>(Constant.SIZE to img.size, Constant.IMGS to img)
            }
        })

        mAdapter.setOnMImageViewLongClickListener(object : WelfareAdapter.OnImageViewLongClickListener {
            override fun onImageViewClick(position: Int, entity: ResultsBean) {
                GlideUtils.downLoadImageNew(entity.url)
            }
        })
    }


    override fun isLazyLoad(): Boolean {
        return true
    }

    private fun initData() {
        mPresenter = WelfarePresenterImpl(this)
        mAdapter = WelfareAdapter()

        val contentView = spvContent.getContent()
        val recyclerView = contentView as RecyclerView
        recyclerView.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
            mAdapter.scrollMoreEntity(this) {
                mPresenter.getPeriPicture(WelfarePresenterImpl.MODE_MORE, WelfarePresenterImpl.PAGE_SIZE, ++page)
            }
        }
        updateData()
    }

    override fun onEmptyStatusResponse(mode: String) {
        refreshLayoutView.finishRefresh()
        if (mode == WelfarePresenterImpl.MODE_UPDATE) {
            spvContent.setBodyTransform(InconstantView.Type.EMPTY_STATE)
        }
    }

    override fun onPeriPicture(mode: String, everyDayEntity: MutableList<ResultsBean>, isCache: Boolean) {
        refreshLayoutView.finishRefresh()
        if (mode == WelfarePresenterImpl.MODE_UPDATE) {
            // 下拉刷新
            mAdapter.updateData(everyDayEntity)
        } else {
            // 加载更多
            mAdapter.moreData(everyDayEntity)
        }
        spvContent.setBodyTransform(InconstantView.Type.CONTENT)
    }

    private fun initView() {
        initBody(spvContent)
        initRefresh()
        welfareAdapter = WelfareAdapter()
    }


    private fun initRefresh() {
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

    private fun updateData() {
        mPresenter.getPeriPicture(WelfarePresenterImpl.MODE_UPDATE, WelfarePresenterImpl.PAGE_SIZE, page)
    }

    override fun msg(msg: Int): String {
        return getString(msg)
    }
}