package com.audio.administrator.ganhuo

import android.Manifest
import com.audio.administrator.ganhuo.annotation.LayoutResId
import com.audio.administrator.ganhuo.base.BaseActivity
import com.audio.administrator.ganhuo.base.BaseFragment
import com.audio.administrator.ganhuo.config.Config
import com.audio.administrator.ganhuo.entity.MainTabEntity
import com.audio.administrator.ganhuo.modules.android.fragment.AndroidFragment
import com.audio.administrator.ganhuo.modules.ios.fragment.IOSFragment
import com.audio.administrator.ganhuo.modules.video.fragment.VideoFragment
import com.audio.administrator.ganhuo.modules.welfare.fragment.WelfareFragment
import com.audio.administrator.ganhuo.ui.adapter.MainAdapter
import com.audio.administrator.ganhuo.ui.superier.PageChangeAdapter
import com.audio.administrator.ganhuo.ui.superier.TabSelectAdapter
import com.flyco.tablayout.listener.CustomTabEntity
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*


@LayoutResId(R.layout.activity_main)
class MainActivity : BaseActivity() {

    private lateinit var mPermission: Disposable

    override fun init() {
        initView()
        initEvent()
    }

    private fun initEvent() {

        vpContent.addOnPageChangeListener(object : PageChangeAdapter() {
            override fun onPageSelected(position: Int) {
                ctlMainTabLayout.currentTab = position
            }
        })

        ctlMainTabLayout.setOnTabSelectListener(object : TabSelectAdapter() {
            override fun onTabSelect(position: Int) {
                vpContent.currentItem = position
            }
        })


    }

    private fun initView() {
        initDir()
        val (fragments, titles) = initTabLayout()
        initViewPage(fragments, titles)
    }

    /**
     *  初始化 ViewPage
     */
    private fun initViewPage(
        fragments: List<BaseFragment>,
        titles: List<String>
    ) {
        val mAdapter = MainAdapter(supportFragmentManager, fragments, titles)
        vpContent.apply {
            adapter = mAdapter
            offscreenPageLimit = 3
            currentItem = 0
        }
    }

    fun initDir() {
        val rx = RxPermissions(this)
        //  初始化文件夹
        mPermission = rx.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe { aBoolean ->
                if (aBoolean!!) {
                    //  初始化文件夹
                    Config.get().initPhotoDir()
                }
            }
    }

    /**
     *  初始化 TabLayout 数据
     */
    private fun initTabLayout(): Pair<List<BaseFragment>, List<String>> {
        //val fragments = listOf(AndroidFragment(), IOSFragment(), WelfareFragment(), VideoFragment())
        //val titles = listOf("Android", "IOS", "福利", "视频")
        val fragments = listOf(AndroidFragment(), IOSFragment(), WelfareFragment())
        val titles = listOf("Android", "IOS", "福利")
        val tabEntities = ArrayList<CustomTabEntity>()
        titles.forEach {
            val entity = MainTabEntity(it)
            tabEntities.add(entity)
        }
        ctlMainTabLayout.setTabData(tabEntities)
        fragments as List<BaseFragment>
        return Pair(fragments, titles)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!mPermission.isDisposed) mPermission.dispose()
    }
}
