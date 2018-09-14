package com.td.framework.mvp.base

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.loadmore.LoadMoreView
import com.td.framework.R
import com.td.framework.biz.NetError
import com.td.framework.global.BasicAdapterHelper
import com.td.framework.global.app.Constant
import com.td.framework.mvp.contract.GeneralLoadDataContract
import com.td.framework.mvp.model.BaseParamsInfo
import com.td.framework.mvp.model.EmptyParamsInfo
import com.td.framework.ui.adapter.CustomLoadMoreView
import com.td.framework.ui.refresh.RefreshLayout
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
    /**数据对象*/
    protected var mDatas: MutableList<T> = ArrayList()
    protected val mAdapter: BaseQuickAdapter<T, *>? by lazy {
        getAdapter()
    }
    //参数对象
    protected val mParam: BaseParamsInfo by lazy { getParam() }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    override fun onStart() {
        super.onStart()
        if (isCreate) {
            showLoading()
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
    protected open fun initAdapter() {
        //适配器
        mAdapter?.apply {
            getLoadMoreView()?.apply {
                setLoadMoreView(this)
                setOnLoadMoreListener(this@MvpLoadListDataBaseActivity)
            }
            BasicAdapterHelper.initAdapter(mActivity, mAdapter,
                    getRecyclerView(), getRecyclerViewOrientation(),
                    getEmptyTip(), getListEmptyLayoutId())
            getRecyclerView()?.adapter = this
            getHeaderView()?.apply {
                addHeaderView(this)
            }
            setHeaderAndEmpty(true)
        }
        //刷新
        (getSwipeRefreshLayout() as? RefreshLayout?)?.setOnRefreshListener(this)
        //点击
        getRecyclerView()?.addOnItemTouchListener(object : OnItemChildClickListener() {
            override fun onSimpleItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
                //回调
                onRecyclerViewItemChildClick(view, position, mDatas[position])
            }
        })
    }

    /**
     * 返回列表空布局ID
     */
    protected open fun getListEmptyLayoutId(): Int {
        return R.layout.layout_adatper_empty_view
    }
    /**
     * @return  类型  <p>
     *     Vertical = 1
     *     Horizontal =2
     */
    protected open fun getRecyclerViewOrientation(): Int {
        return LinearLayoutManager.VERTICAL
    }


    /**
     * 返回加载布局
     */
    protected open fun getLoadMoreView(): LoadMoreView? {
        return CustomLoadMoreView()
    }


    override fun onRetry() {
        p?.refreshData(mParam)
    }


    override fun handlerFail(error: NetError) {
        super.handlerFail(error)
        (getSwipeRefreshLayout() as? RefreshLayout?)?.isRefreshing = false
    }

    override fun refreshSuccess(datas: List<T>) {
        mDatas = datas as MutableList<T>
        mAdapter?.setNewData(mDatas)
        (getSwipeRefreshLayout() as? RefreshLayout?)?.isRefreshing = false
        showContent()
    }

    /**
     * 打开和关闭底布局
     *
     * 当前数据集合小于页大小
     */
    protected fun goneLoadMoreEnd() {
        if (mDatas.size < Constant.PAGE_SIZE)
            mAdapter?.loadMoreEnd(true)
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
        if (getMock()) {
            noMore()
        } else {
//            if (mDatas.size <= 10)
//                mAdapter?.loadMoreEnd(false)
//            else
            p?.loadMoreData(mParam)
        }
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
     * 设置是否模拟
     */
    protected open fun getMock(): Boolean {
        return false
    }

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
        if (getMock()) {
            (getSwipeRefreshLayout() as? RefreshLayout?)?.isRefreshing = false
        } else {
            p?.refreshData(mParam)
        }
    }
}