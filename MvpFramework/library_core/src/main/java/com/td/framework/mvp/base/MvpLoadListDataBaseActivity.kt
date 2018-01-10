package com.td.framework.mvp.base

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.td.framework.biz.NetError
import com.td.framework.global.BasicAdapterHelper
import com.td.framework.mvp.contract.GeneralLoadDataContract
import com.td.framework.mvp.model.BaseParamsInfo
import com.td.framework.mvp.model.EmptyParamsInfo
import com.td.framework.ui.adapter.CustomLoadMoreView
import java.util.*

/**
 * Created by jc on 7/26/2017.
 *
 * 版本:1.0.0
 * **说明**<br>专门用于加载列表数据的基类</br>
 *  *
 **** */
abstract class MvpLoadListDataBaseActivity<out P : GeneralLoadDataContract.GeneralLoadDataPresenter<*, *, *>, T> :
        MvpLoadingActivity<P>(), GeneralLoadDataContract.GeneralLoadDataView<T>,
        BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    //数据对象
    protected var mDatas: MutableList<T> = ArrayList()
    protected var mAdapter: BaseQuickAdapter<T, *>? = getAdapter()
    //参数对象
    protected val mParam: BaseParamsInfo by lazy { getParam() }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    override fun onStart() {
        super.onStart()
        if (isCreate) {
            initAdapter()
            isCreate = false
            p?.refreshData(mParam)
        }
    }

    protected open fun getParam(): BaseParamsInfo {
        return EmptyParamsInfo()
    }

    /**
     * 初始化适配器
     */
    protected fun initAdapter() {
        //适配器
        mAdapter?.apply {
            setLoadMoreView(CustomLoadMoreView())
            setOnLoadMoreListener(this@MvpLoadListDataBaseActivity)
            BasicAdapterHelper.initAdapterVertical(mActivity, mAdapter, getRecyclerView(), getEmptyTip())
            getRecyclerView()?.adapter = this
            getHeaderView()?.apply {
                addHeaderView(this)
            }
        }
        //刷新
        (getSwipeRefreshLayout() as? SwipeRefreshLayout?)?.setOnRefreshListener(this)
        //点击
        getRecyclerView()?.addOnItemTouchListener(object : OnItemChildClickListener() {
            override fun onSimpleItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
                //回调
                onRecyclerViewItemChildClick(view, position, mDatas[position])
            }
        })
    }


    override fun onRetry() {
        p?.refreshData(mParam)
    }


    override fun handlerFail(error: NetError) {
        super.handlerFail(error)
        (getSwipeRefreshLayout() as? SwipeRefreshLayout?)?.isRefreshing = false
    }

    override fun refreshSuccess(datas: List<T>) {
        mDatas = datas as MutableList<T>
        mAdapter?.setNewData(mDatas)
        (getSwipeRefreshLayout() as? SwipeRefreshLayout?)?.isRefreshing = false
        showContent()
    }

    override fun loadMoreSuccess(datas: List<T>) {
        mDatas.addAll(datas)
        mAdapter?.notifyDataSetChanged()
        mAdapter?.loadMoreComplete()
    }

    protected open fun refresh() {
        p?.refreshData(mParam)
    }

    override fun noMore() {
        mAdapter?.loadMoreEnd()
    }

    override fun onLoadMoreRequested() {
        p?.loadMoreData(mParam)
    }

    override fun commitDataSuccess() {
        //提交数据成功
    }

    override fun commitDataFail() {
        //提交数据失败
    }

    /**
     * 获取刷新布局
     * * 写View的原因是为了方便以后假如替换相关的刷新
     */
    protected abstract fun getSwipeRefreshLayout(): View?

    /**
     * 获取适配器
     */
    protected abstract fun getAdapter(): BaseQuickAdapter<T, *>?

    /**
     * 获取RecyclerView
     */
    protected abstract fun getRecyclerView(): RecyclerView?

    /**
     * 点击事件

     * *
     * @param view     点击的View
     * *
     * @param position 下标
     * *
     * @param data     点击的数据
     */
    protected abstract fun onRecyclerViewItemChildClick(view: View, position: Int, data: T)

    /**
     * 获取内容为空的提示

     * @return
     */
    protected open fun getEmptyTip(): String {
        return ""
    }

    /**
     * 获取头布局
     */
    protected open fun getHeaderView(): View? {
        return null
    }

    override fun onRefresh() {
        p?.refreshData(mParam)
    }
}