package com.audio.administrator.ganhuo.modules.img.activity

import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import com.audio.administrator.ganhuo.R
import com.audio.administrator.ganhuo.annotation.LayoutResId
import com.audio.administrator.ganhuo.app.GanHuoApplication
import com.audio.administrator.ganhuo.base.BaseActivity
import com.audio.administrator.ganhuo.base.Constant
import com.audio.administrator.ganhuo.modules.img.adapter.PicasaViewerAdapter
import com.audio.administrator.ganhuo.utils.StatusBarUtils
import kotlinx.android.synthetic.main.activity_picase_viewer.*

/**
 * Author: Chris
 * Blog: https://www.jianshu.com/u/a3534a2292e8
 * Date: 2019/2/16
 * Description 图片查看器
 */
@LayoutResId(R.layout.activity_picase_viewer)
class PicasaViewerActivity : BaseActivity() {

    var size = 0
    var position = 0
    private lateinit var imgs: ArrayList<String>

    override fun init() {
        StatusBarUtils.setStatusBarColor(this, resources.getColor(R.color.black))
        size = intent.getIntExtra(Constant.SIZE, 0)             // 总的图片大小
        position = intent.getIntExtra(Constant.POSITION, 0)     // 当前查看的 position
        imgs = intent.getStringArrayListExtra(Constant.IMGS)
        if (size == 0) return
        initView()

        initEvent()
    }

    private fun initEvent() {
        vpCenterView.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {

            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            }

            override fun onPageSelected(position: Int) {
                GanHuoApplication.get.setPageSelectedPosition(position)
                csDotView.setFocusPosition(position)
            }

        })
    }

    private fun initView() {

        val mPictures = ArrayList<View>()

        for (i in 1..size) {
            // 每张图片的 View
            val view = LayoutInflater.from(applicationContext).inflate(R.layout.item_picasa_viewer, null, false)
            mPictures.add(view)
        }
        val mAdapter = PicasaViewerAdapter(mPictures, imgs)
        vpCenterView.apply {
            adapter = mAdapter
            currentItem = position
        }
        csDotView.setFocusPosition(position)
        mAdapter.setOnFinishListener(object : PicasaViewerAdapter.OnFinishListener {
            override fun onFinish() {
                onBackPressed()
            }
        })

        csDotView.setDrawDotNumber(size)
    }

}