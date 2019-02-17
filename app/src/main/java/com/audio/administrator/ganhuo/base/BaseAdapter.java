package com.audio.administrator.ganhuo.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.audio.administrator.ganhuo.ui.view.CustomHeadOrFootView;
import com.audio.administrator.ganhuo.ui.view.DefaultFootView;
import com.audio.administrator.ganhuo.ui.view.DefaultHeadView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Chris
 * Blog: https://www.jianshu.com/u/a3534a2292e8
 * Date: 2019/1/22
 * Description
 */
public abstract class BaseAdapter<DATA> extends RecyclerView.Adapter<BaseViewHolder> {
    private List data = new ArrayList<DATA>();
    private int mLastPosition = -1;
    private static final int HEAD = 8888;  // headViewType
    private static final int FOOT = 9999;  // footViewType
    private static final int DEFAULT_VIEW_TYPE = -9999; // 默认的ViewType
    private boolean isAddDefaultHead = false;
    private boolean isAddDefaultFoot = false;
    private CustomHeadOrFootView customHeadView;
    private CustomHeadOrFootView customFootView;


    public List<DATA> getBody() {
        return data;
    }

    public int getBodySize() {
        return data.size();
    }


    // 默认不添加 head
    protected boolean isAddDefaultHead(boolean isAddDefaultHead) {
        this.isAddDefaultHead = isAddDefaultHead;
        return this.isAddDefaultHead;
    }

    // 默认不添加 head
    protected boolean isAddDefaultFoot(boolean isAddDefaultFoot) {
        this.isAddDefaultFoot = isAddDefaultFoot;
        return this.isAddDefaultFoot;
    }

    public void addHeadView(CustomHeadOrFootView customHeadView) {
        this.customHeadView = customHeadView;
        isAddDefaultHead = false;
    }

    public void addFootView(CustomHeadOrFootView customFootView) {
        this.customFootView = customFootView;
        isAddDefaultFoot = false;
    }

    private boolean isAddCustomHead() {
        return customHeadView != null;
    }

    private boolean isAddCustomFoot() {
        return customFootView != null;
    }

    /**
     * 更新UI的方法
     */
    public void updateData(List<DATA> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
        mLastPosition = -1;
    }


    /**
     * 上滑加载更多使用
     */
    public void moreData(List<DATA> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case HEAD: {
                if (customHeadView != null) {
                    return new CustomHeadAdapterViewHolder(customHeadView);
                } else {
                    DefaultHeadView headView = new DefaultHeadView(parent.getContext());
                    return new HeadAdapterViewHolder(headView);
                }
            }
            case FOOT: {
                if (customFootView != null) {
                    return new CustomHeadAdapterViewHolder(customFootView);
                } else {
                    DefaultFootView footView = new DefaultFootView(parent.getContext());
                    return new FootAdapterViewHolder(footView);
                }
            }
            default:
                return onCreateBodyViewHolder(parent, viewType);
        }
    }


    /**
     * 默认的 head
     */
    class HeadAdapterViewHolder extends BaseViewHolder<String, DefaultHeadView> {
        HeadAdapterViewHolder(@NotNull DefaultHeadView view) {
            super(view);
        }
    }

    class CustomHeadAdapterViewHolder extends BaseViewHolder<String, CustomHeadOrFootView> {
        CustomHeadAdapterViewHolder(@NotNull CustomHeadOrFootView view) {
            super(view);
        }
    }


    /**
     * 默认的 foot
     */
    class FootAdapterViewHolder extends BaseViewHolder<String, DefaultFootView> {

        public FootAdapterViewHolder(@NotNull DefaultFootView view) {
            super(view);
        }
    }

    /**
     * 传递给孩子，让孩子实现
     *
     * @param parent
     * @param viewType
     * @return
     */
    protected abstract BaseViewHolder onCreateBodyViewHolder(ViewGroup parent, int viewType);

    @Override
    public int getItemCount() {
        if (data.size() > 0) {
            if ((isAddDefaultHead && isAddDefaultFoot) || (isAddCustomHead() && isAddCustomFoot())) {
                return data.size() + 2;   // 既有 head 也有 foot
            } else if ((!isAddDefaultHead && !isAddDefaultFoot) && (!isAddCustomHead() && !isAddCustomFoot())) {
                return data.size();       // 既没有 head 也没有 foot
            } else if (isAddDefaultHead && isAddCustomFoot() || isAddDefaultFoot && isAddCustomHead()) {
                return data.size() + 2;   // 默认的头部与定制的底部 ， 默认的底部与定制的头部
            } else {
                return data.size() + 1;   // 有 head 或者有 foot
            }
        } else {
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        // 所有的基础都在存在数据,否则不
        if (data.size() > 0) {
            if (isValidHead(position)) {
                // 如果有 head 就将第一个设置为 head
                return HEAD;
            } else if (isValidFoot(position)) {
                // 如果有 foot 就将最后一个设置为 foot
                return FOOT;
            } else {
                // 是否设置了 EntityType
                //if (!isSimpleBody(position)) {
                // 子 adapter 复杂，抛出去交给子 adapter 自己去处理具体的逻辑
                if (isAddDefaultHead || isAddCustomHead()) {
                    int mPosition = position - 1;
                    return getEntityViewType(mPosition);
                } else {
                    return getEntityViewType(position);
                }
            }
        } else {
            return super.getItemViewType(position);
        }
    }

    private boolean isValidHead(int position) {
        return (isAddDefaultHead && position == 0) || (isAddCustomHead() && position == 0);
    }

    private boolean isValidFoot(int position) {
        return isAddDefaultFoot && (position == getItemCount() - 1) || isAddCustomFoot() && (position == getItemCount() - 1);
    }

    protected boolean isSimpleBody(int position) {
        return getEntityViewType(position) == DEFAULT_VIEW_TYPE;
    }

    /**
     * 子类要是想要复杂的布局
     */
    protected int getEntityViewType(int position) {
        return DEFAULT_VIEW_TYPE;  // 默认 -99999
    }


    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int rawPosition) {
        if (data.size() > 0) {
            // 1. 处理 head
            if (getItemViewType(rawPosition) == HEAD) {

                if (customHeadView != null) {
                    CustomHeadOrFootView itemView = (CustomHeadOrFootView) holder.itemView;
                    itemView.setMsg(getBodySize());
                } else {
                    DefaultHeadView defaultHeadView = (DefaultHeadView) holder.itemView;
                    // 将每次的 body 数据总数传进去,head or foot 根据 size 显示不同的 msg
                    defaultHeadView.setMsg(getBodySize());
                }
            } else if (getItemViewType(rawPosition) == FOOT) {
                if (customFootView != null) {
                    CustomHeadOrFootView itemView = (CustomHeadOrFootView) holder.itemView;
                    itemView.setMsg(getBodySize());
                } else {
                    // 2. 处理 foot
                    DefaultFootView defaultFootView = (DefaultFootView) holder.itemView;
                    defaultFootView.setMsg(getBodySize());
                }
            } else {
                // 3. 处理 body
                int mPosition;
                if (isAddDefaultHead || isAddCustomHead()) {
                    mPosition = rawPosition - 1;
                } else {
                    mPosition = rawPosition;
                }
                BaseItemView itemView = (BaseItemView) holder.itemView;
                int viewType = getEntityViewType(mPosition);
                onBindBodyViewHolder(itemView, mPosition, viewType);
            }
        }
    }


    protected abstract void onBindBodyViewHolder(BaseItemView<DATA> itemView, int position, int viewType);


    public OnItemClickListener<DATA> itemClickListener;
    public OnItemLongClickListener itemLongClickListener;

    public interface OnItemClickListener<DATA> {
        void onItemClick(int position, DATA data);
    }

    public void setOnItemClickListener(OnItemClickListener<DATA> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(int position);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener itemLongClickListener) {
        this.itemLongClickListener = itemLongClickListener;
    }


    public void scrollMoreEntity(RecyclerView mRecyclerView, OnLastVisiblePositionListener mVisiblePositionListener) {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (getBody() != null && getBodySize() > 0) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                        if (layoutManager instanceof LinearLayoutManager) {
                            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                            int lastVisiblePosition = linearLayoutManager.findLastVisibleItemPosition();
                            if (lastVisiblePosition == getItemCount() - 1) {
                                if (mVisiblePositionListener != null)
                                    mVisiblePositionListener.onLastVisiblePosition(lastVisiblePosition);
                            }
                        }
                    }
                }
            }
        });
    }

    public interface OnLastVisiblePositionListener {
        void onLastVisiblePosition(int lastVisiblePosition);
    }

    /**
     * 滑动底部动画
     *
     * @return
     */
    public boolean isLoadingScrollAlpha() {
        return false;
    }

    @Override
    public void onViewAttachedToWindow(@NonNull BaseViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (isLoadingScrollAlpha()) {
            addAnimate(holder, holder.getLayoutPosition());
        }
    }

    private void addAnimate(BaseViewHolder holder, int position) {
        if (mLastPosition < position) {
            holder.itemView.setAlpha(0f);
            holder.itemView.animate().alpha(1f).start();
            mLastPosition = position;
        }
    }
}
