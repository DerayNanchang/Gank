package com.audio.administrator.ganhuo.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Author: Chris
 * Blog: https://www.jianshu.com/u/a3534a2292e8
 * Date: 2019/1/22
 * Description
 */
public abstract class BaseViewHolder<DATA, VIEW extends BaseItemView<DATA>> extends RecyclerView.ViewHolder {

    public BaseViewHolder(@NonNull VIEW itemView) {
        super(itemView);
    }




    /*view = holder.itemView as VIEW
    view ?.let {
        val mPosition = position - 1
        // 已经过滤了 head 与 foot 只要 -1 就好了
        it.bindData(data[mPosition], mPosition)
        it.setOnItemViewClickListener(object :BaseItemView.OnItemViewClickListener<DATA> {
            override fun onItemViewClick(position:Int, data:DATA){
                itemClickListener ?.onItemClick(position, data)
            }
        })
    }*/

}
