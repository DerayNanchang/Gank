package com.audio.administrator.ganhuo.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.util.AttributeSet
import android.view.View
import com.audio.administrator.ganhuo.R
import com.audio.administrator.ganhuo.utils.DensityUtil

/**
 * Author: Chris
 * Blog: https://www.jianshu.com/u/a3534a2292e8
 * Date: 2019/2/16
 * Description
 */
class CustomSmartDotView : View {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var num = 0     // 圆形个数
    private var mPosition = 0 // 当前选中的
    private var defaultDotColor = resources.getColor(R.color.t58)
    private var focusDotColor = resources.getColor(R.color.c3)
    private var viewY = 0
    private var viewX = 0
    private var paint = Paint()
    private var mRadius = 4f
    private var gapLength = 10f


    init {
        paint.apply {
            color = defaultDotColor
            style = Paint.Style.FILL
            isAntiAlias = true
            textSize = 10f
        }
    }


    // 绘制圆形个数
    fun setDrawDotNumber(num: Int) {
        if (num <= 1) {
            this.num = 0
        }else{
            this.num = num
        }
        invalidate()
    }

    fun setFocusPosition(position: Int) {
        this.mPosition = position
        invalidate()
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.viewX = w
        this.viewY = h
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (num != 0) {
            //  执行绘制,圆点的个数
            var x = 0f
            val y = (viewY / 2) * 1.0f

            for (i in 1..num) {
                if (i == 1) {
                    // 起始圆点坐标  总宽度 - 图形的宽度 / 2  + 半径
                    x = (viewX - (num * DensityUtil.dip2px(context, mRadius * 2) + ((num - 1) * DensityUtil.dip2px(
                        context,
                        gapLength
                    )))) / 2 + DensityUtil.dip2px(context, mRadius) * 1.0f

                } else {
                    // 后面的圆 起始坐标 等于 上一次的距离 +  直径  + 间隔长度
                    x += DensityUtil.dip2px(context, mRadius * 2) + DensityUtil.dip2px(context, gapLength)
                }

                if (mPosition == i - 1) {
                    paint.color = focusDotColor
                } else {
                    paint.color = defaultDotColor
                }
                canvas.drawCircle(x, y, DensityUtil.dip2px(context, mRadius) * 1.0f, paint)
            }
        }

    }
}
