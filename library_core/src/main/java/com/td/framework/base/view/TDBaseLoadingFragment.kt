package com.td.framework.base.view

import android.support.design.widget.CoordinatorLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView

import com.td.framework.R
import com.td.framework.ui.VStatusBar


/**
 * Created by jc on 2016/12/30 0030.
 *
 * Gihub
 *
 * 需要加载数据的Fragment
 */

abstract class TDBaseLoadingFragment : TDBaseFragment() {
    /**加载中*/
    private var mLoadView: View? = null
    /**重试*/
    private var mRetryView: View? = null
    /**空数据*/
    protected var mEmptyView: View? = null
    /**状态栏*/
    private var mStatusBarView: View? = null
    /**无权限*/
    private var mNoPermissionsView: View? = null

    protected open var mErrorTextView: TextView? = null

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
        mLoadView = LayoutInflater.from(mActivity).inflate(getRootLoadingLayoutId(), container, false)
        mRetryView = LayoutInflater.from(mActivity).inflate(getRootRetryLayoutId(), container, false)
        mErrorTextView = mRetryView?.findViewById(R.id.tvError)

        mEmptyView = LayoutInflater.from(mActivity).inflate(getRootEmptyLayoutId(), container, false)
        mStatusBarView = VStatusBar(mActivity)
        mStatusBarView?.setBackgroundColor(0x88000000.toInt())
        mStatusBarView?.visibility = View.GONE
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
        mRootView.addView(mStatusBarView)
        return mRootView
    }

    /**
     * 返回加载布局ID
     */
    protected open fun getRootLoadingLayoutId(): Int {
        return R.layout.loding_base_loading
    }
    /**
     * 返回列表空布局ID
     */
    protected open fun getListEmptyLayoutId(): Int {
        return R.layout.layout_adatper_empty_view
    }

    /**
     * 返回重试布局
     */
    protected open fun getRootRetryLayoutId(): Int {
        return R.layout.loding_base_retry
    }

    /**
     * 返回空布局
     */
    protected open fun getRootEmptyLayoutId(): Int {
        return R.layout.loding_base_empty
    }

    /**
     * 设置错误提示的文字
     * @param errorText 错误提示
     */
    protected fun setErrorText(errorText: String?) {
        errorText?.run {
            mErrorTextView?.text = errorText
        }
    }
    /**
     * 显示和关闭状态栏
     */
    protected fun resetStatusBarView(visibility: Int) {
        mStatusBarView?.visibility = visibility
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
