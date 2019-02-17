package com.audio.administrator.ganhuo.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.audio.administrator.ganhuo.R;
import com.audio.administrator.ganhuo.annotation.LayoutResId;
import com.audio.administrator.ganhuo.ui.view.InconstantView;

/**
 * Author: Chris
 * Blog: https://www.jianshu.com/u/a3534a2292e8
 * Date: 2018/12/3
 * Description
 */
abstract public class BaseFragment extends Fragment {

    private boolean isFmtVisible = false;     // fragment 是否可视（by 懒加载）

    private boolean isPrepared = false;       // 是否准备好了  (by 懒加载1)

    private boolean isFirst = true;
    private ProgressDialog dialog;
    protected View view;


    private void lazyLoadValid() {

        // 如果它不是依次进入，不可见，没有准备好就 return
        if (!isFmtVisible || !isPrepared || !isFirst) {
            return;
        }
        init();
        isFirst = false;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(this.getClass().getAnnotation(LayoutResId.class).value(), null, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (isLazyLoad()) {
            isPrepared = true;   // 会先执行 setUserVisibleHint 方法再执行 onActivityCreated 为了避免空指针需要做个标记
            lazyLoadValid();
        } else {
            // 不使用懒加载
            init();
        }
    }

    /**
     * 是否懒加载
     *
     * @return 默认不懒加载
     */
    protected boolean isLazyLoad() {
        return false;
    }


    protected abstract void init();

    protected void initBody(InconstantView inconstantView) {
        // 添加空状态与无网络
        if (inconstantView != null) {
            inconstantView.addContent(R.layout.view_default_content);
            inconstantView.addEmptyState(R.layout.view_default_empty_state);
            inconstantView.addNoConnect(R.layout.view_default_no_connect);
            inconstantView.addLoading(R.layout.view_custom_wrap_progress);
            inconstantView.setBodyTransform(InconstantView.Type.LOADING);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isLazyLoad()) {
            if (getUserVisibleHint()) {
                isFmtVisible = true; // 可见
                lazyLoadValid();
            } else {
                isFmtVisible = false; // 不可见
            }
        }
    }

    protected void showDialog(String msg) {
        dialog = ProgressDialog.show(getActivity(), "", msg);
        dialog.show();
    }

    protected void showDialog() {
        String message = "正在加载数据";
        dialog = ProgressDialog.show(getActivity(), "", message);
        dialog.show();
    }


    protected void dismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    protected void startActivity(Class clazz) {

        startActivity(new Intent(getActivity(), clazz));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isLazyLoad()) {
            isFmtVisible = false;
            isPrepared = false;
            isFirst = true;
        }
        dismissDialog();
    }
}
