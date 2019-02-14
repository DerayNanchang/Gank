package com.audio.administrator.ganhuo.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.LinearLayout


/**
 * Author: Chris
 * Blog: https://www.jianshu.com/u/a3534a2292e8
 * Date: 2019/1/21
 * Description 空状态，网络连接失败，加载动画，数据呈现
 */
class InconstantView : RelativeLayout {

    private var mType: Type = Type.LOADING      // 默认状态为加载动画

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        gravity = CENTER_IN_PARENT
    }

    enum class Type {
        CONTENT, NO_CONNECT, EMPTY_STATE, LOADING
    }

    companion object {
        private const val CONTENT = "Content"
        private const val NO_CONNECT = "NoConnect"
        private const val EMPTY_STATE = "EmptyState"
        private const val LOADING = "loading"
    }


    fun getType() = mType

    /**
     *  添加内容
     */
    fun addContent(resID: Int) {
        val contentView = LayoutInflater.from(context).inflate(resID, null, false)
        contentView.tag = CONTENT
        addView(
            contentView,
            LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        )
    }

    /**
     *  获取内容
     */
    fun getContent(): View? {
        for (i: Int in 0 until childCount) {
            if (getChildAt(i).tag == CONTENT) {
                return getChildAt(i)
            }
        }
        return null
    }


    /**
     *  添加无网络
     */
    fun addNoConnect(resId: Int) {
        val noConnectView = LayoutInflater.from(context).inflate(resId, null)
        noConnectView.tag = NO_CONNECT
        addView(
            noConnectView,
            LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        )
    }

    /**
     *  添加空状态
     */
    fun addEmptyState(resId: Int) {
        val emptyStateView = LayoutInflater.from(context).inflate(resId, null)
        emptyStateView.tag = EMPTY_STATE
        addView(
            emptyStateView,
            LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        )
    }

    /**
     *  添加加载状态
     */
    fun addLoading(resId: Int) {
        val loadingView = LayoutInflater.from(context).inflate(resId, null)
        loadingView.tag = LOADING
        addView(
            loadingView,
            LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        )
    }


    fun setBodyTransform(type: Type) {
        mType = when (type) {
            Type.CONTENT -> {
                bodyTransformEntity(CONTENT)
                type
            }
            Type.EMPTY_STATE -> {
                bodyTransformEntity(EMPTY_STATE)
                type
            }
            Type.NO_CONNECT -> {
                bodyTransformEntity(NO_CONNECT)
                type
            }
            Type.LOADING -> {
                bodyTransformEntity(LOADING)
                type
            }
        }
    }

    private fun bodyTransformEntity(tag: String) {

        for (i: Int in 0 until childCount) {
            val child = getChildAt(i)
            if (child.tag != tag) {
                child.visibility = View.GONE
            } else {
                child.visibility = View.VISIBLE
            }
        }
    }
}