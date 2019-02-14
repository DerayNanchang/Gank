package com.audio.administrator.ganhuo.ui.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import com.audio.administrator.ganhuo.R
import com.audio.administrator.ganhuo.annotation.LayoutResId
import com.audio.administrator.ganhuo.base.BaseCustomView
import com.audio.administrator.ganhuo.utils.DensityUtil
import kotlinx.android.synthetic.main.view_custom_progress.view.*
import org.jetbrains.anko.doAsync

/**
 * Author: Chris
 * Blog: https://www.jianshu.com/u/a3534a2292e8
 * Date: 2018/12/12
 * Description 自定义加载动画
 *
 *  p.s 设计思路
 *
 *  1. 动画下滑 -> 底部阴影放大 同时执行 嵌套执行
 *  2. 动画上滑 -> 底部动画缩小 同时执行 嵌套执行
 *  3. 当完成一次周期时，进行图形的转换并且自转
 *
 */
@LayoutResId(R.layout.view_custom_progress)
class CustomProgressView : BaseCustomView {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    companion object {

        const val DURATION: Long = 500  // 动画周期执行时间
        const val EXTENT = 32f          // 下降 上升的幅度
        const val ZOOM_RANGE = 0.7f     // 缩放幅度

    }


    override fun init() {

        // 1. 执行动画 下滑 -> 上移 ，并且 放大 -> 缩小
            startDownAnimator()
        // 2. 绘制上面自定义图形，并且随着动画生命周期进行相应的改变   line
    }

    private fun startDownAnimator() {

            val downTranslation = ObjectAnimator.ofFloat(csvCustomShape, "translationY", 0f, DensityUtil.dip2px(context,EXTENT).toFloat())
            val downScaleX = ObjectAnimator.ofFloat(vCustomProgressOval, "scaleX", 1.0f - ZOOM_RANGE, 1.0f)
            val set = AnimatorSet()
            set.playTogether(downTranslation, downScaleX)
            set.duration = DURATION
            set.interpolator = DecelerateInterpolator()

            set.start()
            set.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    startUpAnimator()
                }
            })
    }

    private fun startUpAnimator() {
        val upTranslation = ObjectAnimator.ofFloat(csvCustomShape, "translationY", DensityUtil.dip2px(context,EXTENT).toFloat(), 0f)
        val upScaleX = ObjectAnimator.ofFloat(vCustomProgressOval, "scaleX", 1.0f, 1.0f - ZOOM_RANGE)
        val set = AnimatorSet()
        set.playTogether(upTranslation, upScaleX)
        set.duration = DURATION
        set.interpolator = AccelerateInterpolator()
        set.start()
        set.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                startDownAnimator()
                csvCustomShape.changeShape()
            }
        })
    }

    override fun setVisibility(visibility: Int) {
        super.setVisibility(visibility)
        if (visibility == GONE) {
            // 1. 将动画删除
            csvCustomShape.clearAnimation()
            vCustomProgressOval.clearAnimation()

            // . 从父容器移除
            val parent = parent as ViewGroup
            parent.removeView(this)
        }
    }
}