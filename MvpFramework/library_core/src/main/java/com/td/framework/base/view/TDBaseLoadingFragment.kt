package com.td.framework.base.view

import android.support.design.widget.CoordinatorLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

import com.td.framework.R


/**
 * Created by jc on 2016/12/30 0030.
 *
 * Gihub https://github.com/aohanyao
 *
 * 需要加载数据的Fragment
 */

abstract class TDBaseLoadingFragment : TDBaseFragment() {

    private var mLoadView: View? = null
    private var mRetryView: View? = null
    private var mEmptyView: View? = null
    private var mNoPermissionsView: View? = null


    /**
     * 初始化布局 增加加载框

     * @param resId
     * *
     * @param container
     * *
     * @return
     */
    protected fun inflateView(resId: Int, container: ViewGroup?): View {
        val mRootView = FrameLayout(mActivity)
        mRootView.layoutParams = CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        //其他布局
        mLoadView = LayoutInflater.from(mActivity).inflate(R.layout.loding_base_loading, container, false)
        mRetryView = LayoutInflater.from(mActivity).inflate(R.layout.loding_base_retry, container, false)
        mEmptyView = LayoutInflater.from(mActivity).inflate(R.layout.loding_base_empty, container, false)
        mNoPermissionsView = LayoutInflater.from(mActivity).inflate(R.layout.loding_no_permissions, container, false)
        setRetryEvent(mRetryView)
        mLoadView?.visibility = View.GONE
        resetLoadView()
        val contentView = LayoutInflater.from(mActivity).inflate(resId, container, false)
        mRootView.addView(contentView)
        mRootView.addView(mLoadView)
        mRootView.addView(mRetryView)
        mRootView.addView(mEmptyView)
        mRootView.addView(mNoPermissionsView)
        return mRootView
    }


    /**
     * 重置视图
     */
    private fun resetLoadView() {
        mEmptyView?.visibility = View.GONE
        mRetryView?.visibility = View.GONE
        mLoadView?.visibility = View.GONE
        mNoPermissionsView?.visibility = View.GONE
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
    fun showLoading() {
        resetLoadView()
        mLoadView?.visibility = View.VISIBLE
    }

    /**
     * 显示重新尝试的框
     */
    fun showRetry() {
        //        resetLoadView();
        mRetryView?.visibility = View.VISIBLE
    }

    /**
     * 显示内容
     */
    fun showContent() {
        resetLoadView()
    }

    /**
     * 显示空布局
     */
    fun showEmpty() {
        resetLoadView()
        mEmptyView?.visibility = View.VISIBLE
    }

    /**
     * 无权查看
     */
    fun showNoPermissions() {
        resetLoadView()
        mNoPermissionsView?.visibility = View.VISIBLE
    }

    /**
     * 重新尝试

     * @param retryView
     */
    private fun setRetryEvent(retryView: View?) {
        retryView?.findViewById<View>(R.id.id_retry)
                ?.setOnClickListener {
                    showLoading()
                    onRetry()
                }
    }

    /**
     * 数据重新尝试
     */
    protected abstract fun onRetry()
}
