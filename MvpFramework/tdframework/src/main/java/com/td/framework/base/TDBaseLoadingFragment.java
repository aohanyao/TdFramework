package com.td.framework.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.td.framework.R;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by 江俊超 on 2016/12/30 0030.
 * <p>Gihub https://github.com/aohanyao</p>
 * <p>需要加载数据的Fragment</p>
 */

public abstract class TDBaseLoadingFragment extends TDBaseFragment {

    private View mLoadView;
    private View mRetryView;
    private View mEmptyView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (useEventBus()) {
            EventBus.getDefault().register(this);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        if (useEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroyView();
    }

    /**
     * 初始化布局 增加加载框
     *
     * @param resId
     * @param container
     * @return
     */
    protected View inflateView(int resId, ViewGroup container) {
        FrameLayout mRootView = new FrameLayout(mActivity);
        mRootView.setLayoutParams(new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        //其他布局
        mLoadView = LayoutInflater.from(mActivity).inflate(R.layout.loding_base_loading, container, false);
        mRetryView = LayoutInflater.from(mActivity).inflate(R.layout.loding_base_retry, container, false);
        mEmptyView = LayoutInflater.from(mActivity).inflate(R.layout.loding_base_empty, container, false);
        setRetryEvent(mRetryView);
        mLoadView.setVisibility(View.GONE);
        resetLoadView();
        View contentView = LayoutInflater.from(mActivity).inflate(resId, container, false);
        mRootView.addView(contentView);
        mRootView.addView(mLoadView);
        mRootView.addView(mRetryView);
        mRootView.addView(mEmptyView);
        return mRootView;
    }





    /**
     * 重置视图
     */
    private void resetLoadView() {
        mEmptyView.setVisibility(View.GONE);
        mRetryView.setVisibility(View.GONE);
        mLoadView.setVisibility(View.GONE);
        //增加动画 不用显得那么突兀
//        if (mLoadView.getVisibility() == View.VISIBLE)
//            mLoadView.animate()
//                    .alpha(0)
//                    .setDuration(500)
//                    .setListener(new AnimatorListenerAdapter() {
//                        @Override
//                        public void onAnimationEnd(Animator animation) {
//                            super.onAnimationEnd(animation);
//                            mLoadView.setVisibility(View.GONE);
//                        }
//                    })
//                    .start();

    }

    /**
     * 显示加载框
     */
    public void showLoading() {
        resetLoadView();
        mLoadView.setVisibility(View.VISIBLE);
    }

    /**
     * 显示重新尝试的框
     */
    public void showRetry() {
//        resetLoadView();
        mRetryView.setVisibility(View.VISIBLE);
    }

    /**
     * 显示内容
     */
    public void showContent() {
        resetLoadView();
    }

    /**
     * 显示空布局
     */
    public void showEmpty() {
        resetLoadView();
        mEmptyView.setVisibility(View.VISIBLE);
    }



    /**
     * 重新尝试
     *
     * @param retryView
     */
    private void setRetryEvent(View retryView) {
        View view = retryView.findViewById(R.id.id_retry);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading();
                onRetry();
            }
        });
    }

    /**
     * 数据重新尝试
     */
    protected abstract void onRetry();
}
