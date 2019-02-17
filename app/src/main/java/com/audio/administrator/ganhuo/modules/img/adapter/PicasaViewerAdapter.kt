package com.audio.administrator.ganhuo.modules.img.adapter

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import com.audio.administrator.ganhuo.R
import com.audio.administrator.ganhuo.utils.glide.GlideHelp
import com.audio.administrator.ganhuo.utils.glide.GlideUtils
import org.jetbrains.anko.find
import uk.co.senab.photoview.PhotoView

/**
 * Author: Chris
 * Blog: https://www.jianshu.com/u/a3534a2292e8
 * Date: 2019/2/16
 * Description
 */
class PicasaViewerAdapter(private var views: List<View>, private var imgs: List<String>) : PagerAdapter() {


    override fun getCount() = views.size

    override fun isViewFromObject(p0: View, p1: Any) = p0 == p1

    private lateinit var onFinishListener: OnFinishListener


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = views[position]
        val img = imgs[position]
        val mPhotoView = view.find<PhotoView>(R.id.pvContentView)
        mPhotoView.setOnViewTapListener { view, x, y ->
            onFinishListener.onFinish()
        }

        mPhotoView.setOnLongClickListener {
            GlideUtils.downLoadImageNew(img)
            true
        }
        GlideUtils.noDefault(mPhotoView, img)
        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(views[position])
    }

    fun setOnFinishListener(onFinishListener: OnFinishListener) {
        this.onFinishListener = onFinishListener
    }

    interface OnFinishListener {
        fun onFinish()
    }


}