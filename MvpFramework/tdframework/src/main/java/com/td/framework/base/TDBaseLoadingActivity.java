package com.td.framework.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.td.framework.R;
import com.td.framework.utils.DensityUtils;
import com.td.framework.utils.statusbar.StatusBarUtil;

/**
 * Created by 江俊超 on 2016/12/30 0030.
 * <p>Gihub https://github.com/aohanyao</p>
 * <p>需要加载数据的Activity</p>
 */

public abstract class TDBaseLoadingActivity extends TDBaseActivity {
    private View mLoadView;
    private View mRetryView;
    private View mEmptyView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    /**
     * 初始化布局 增加加载框
     *
     * @param resId
     * @return
     */
    protected View inflateView(int resId) {
        CoordinatorLayout contentView = (CoordinatorLayout) LayoutInflater.from(mActivity).inflate(resId, null, false);
        //其他布局
        mLoadView = LayoutInflater.from(mActivity).inflate(R.layout.loding_base_loading, null, false);
        mRetryView = LayoutInflater.from(mActivity).inflate(R.layout.loding_base_retry, null, false);
        mEmptyView = LayoutInflater.from(mActivity).inflate(R.layout.loding_base_empty, null, false);
        setRetryEvent(mRetryView);
        resetLoadView();

        FrameLayout frameLayout = new FrameLayout(mActivity);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            params.topMargin = DensityUtils.dp2px(mActivity, 44) + StatusBarUtil.getStatusBarHeight(mActivity);//为了显示标题栏
        } else {
            params.topMargin = DensityUtils.dp2px(mActivity, 44);//为了显示标题栏
        }
        frameLayout.setLayoutParams(params);

        frameLayout.addView(mLoadView);
        frameLayout.addView(mRetryView);
        frameLayout.addView(mEmptyView);
        contentView.addView(frameLayout);
        return contentView;
    }

    /**
     * 重置视图
     */
    private void resetLoadView() {
        mEmptyView.setVisibility(View.GONE);
        mRetryView.setVisibility(View.GONE);
        mLoadView.setVisibility(View.GONE);
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
    public void setRetryEvent(View retryView) {
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
