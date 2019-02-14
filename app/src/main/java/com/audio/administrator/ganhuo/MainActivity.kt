package com.audio.administrator.ganhuo

import com.audio.administrator.ganhuo.annotation.LayoutResId
import com.audio.administrator.ganhuo.base.BaseActivity
import com.audio.administrator.ganhuo.base.BaseFragment
import com.audio.administrator.ganhuo.entity.MainTabEntity
import com.audio.administrator.ganhuo.modules.android.fragment.AndroidFragment
import com.audio.administrator.ganhuo.modules.ios.fragment.IOSFragment
import com.audio.administrator.ganhuo.modules.video.fragment.VideoFragment
import com.audio.administrator.ganhuo.modules.welfare.fragment.WelfareFragment
import com.audio.administrator.ganhuo.ui.adapter.MainAdapter
import com.audio.administrator.ganhuo.ui.superier.PageChangeAdapter
import com.audio.administrator.ganhuo.ui.superier.TabSelectAdapter
import com.flyco.tablayout.listener.CustomTabEntity
import kotlinx.android.synthetic.main.activity_main.*


@LayoutResId(R.layout.activity_main)
class MainActivity : BaseActivity() {
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

    /**
     *  初始化 TabLayout 数据
     */
    private fun initTabLayout(): Pair<List<BaseFragment>, List<String>> {
        val fragments = listOf(AndroidFragment(), IOSFragment(), WelfareFragment(), VideoFragment())
        val titles = listOf("Android", "IOS", "福利", "视频")

        val tabEntities = ArrayList<CustomTabEntity>()
        titles.forEach {
            val entity = MainTabEntity(it)
            tabEntities.add(entity)
        }
        ctlMainTabLayout.setTabData(tabEntities)

        return Pair(fragments, titles)
    }
}
