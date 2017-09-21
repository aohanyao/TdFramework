package com.td.framework.base.view

import android.os.Build
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.td.framework.R
import com.td.framework.utils.DensityUtils
import com.td.framework.utils.statusbar.StatusBarUtil

/**
 * Created by 江俊超 on 2016/12/30 0030.
 *
 * Gihub https://github.com/aohanyao
 *
 * 需要加载数据的Activity
 */

abstract class TDBaseLoadingActivity : TDBaseActivity() {
    private var mLoadView: View? = null
    private var mRetryView: View? = null
    private var mEmptyView: View? = null
    private var mNoPermissionsView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    /**
     * 初始化布局 增加加载框

     * @param resId
     * *
     * @return
     */
    protected fun inflateView(resId: Int): View {
        val contentView = LayoutInflater.from(mActivity).inflate(resId, null, false) as CoordinatorLayout
        initLoadView(contentView)
        return contentView
    }

    /**
     * 初始化
     */
    protected fun initLoadView(view: View) {
        //其他布局
        mLoadView = LayoutInflater.from(mActivity).inflate(R.layout.loding_base_loading, null, false)
        mRetryView = LayoutInflater.from(mActivity).inflate(R.layout.loding_base_retry, null, false)
        mEmptyView = LayoutInflater.from(mActivity).inflate(R.layout.loding_base_empty, null, false)
        mNoPermissionsView = LayoutInflater.from(mActivity).inflate(R.layout.loding_no_permissions, null, false)
        setRetryEvent(mRetryView)
        resetLoadView()

        val frameLayout = FrameLayout(mActivity)
        val params = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            params.topMargin = DensityUtils.dp2px(mActivity, 44f) + StatusBarUtil.getStatusBarHeight(mActivity)//为了显示标题栏
        } else {
            params.topMargin = DensityUtils.dp2px(mActivity, 44f)//为了显示标题栏
        }
        frameLayout.layoutParams = params

        frameLayout.addView(mLoadView)
        frameLayout.addView(mRetryView)
        frameLayout.addView(mEmptyView)
        frameLayout.addView(mNoPermissionsView)
        (view as CoordinatorLayout).addView(frameLayout)
    }

    /**
     * 重置视图
     */
    protected fun resetLoadView() {
        mEmptyView?.visibility = View.GONE
        mRetryView?.visibility = View.GONE
        mLoadView?.visibility = View.GONE
        mNoPermissionsView?.visibility = View.GONE
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
     * 无权查看
     */
    fun showNoPermissions() {
        resetLoadView()
        mNoPermissionsView?.visibility = View.VISIBLE
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
     * 重新尝试

     * @param retryView
     */
    fun setRetryEvent(retryView: View?) {
        retryView?.findViewById(R.id.id_retry)?.setOnClickListener {
            showLoading()
            onRetry()
        }
    }

    /**
     * 数据重新尝试
     */
    protected abstract fun onRetry()
}
