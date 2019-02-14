package com.audio.administrator.ganhuo.base;

/**
 * Author: Chris
 * Blog: https://www.jianshu.com/u/a3534a2292e8
 * Date: 2019/1/22
 * Description
 */
public abstract class SimpleAdapter<DATA> extends BaseAdapter<DATA> {


    @Override
    protected void onBindBodyViewHolder(BaseItemView<DATA> itemView, int mPosition, int viewType) {
        if (isSimpleBody(mPosition)) {
            itemView.bindData(getBody().get(mPosition), mPosition);
            {
                itemView.setOnItemViewClickListener((position, data) -> {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(position, data);
                    }
                });
            }
            {
                itemView.setOnItemViewLongClickListener(position -> {
                    if (itemLongClickListener != null) {
                        itemLongClickListener.onItemLongClick(position);
                    }
                });
            }
        }
    }
}
