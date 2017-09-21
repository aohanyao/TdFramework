package com.td.framework.mvp.base

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.td.framework.biz.NetError
import com.td.framework.global.BasicAdapterHelper
import com.td.framework.mvp.contract.RequestDataContract
import com.td.framework.ui.adapter.CustomLoadMoreView

/**
 * Created by 江俊超 on 7/27/2017.
 * <p>版本:1.0.0</p>
 * <b>说明<b><br/>
 * <li>专门用来请求一次加载列表数据的MVP基类</li>
 *
 * *
 *
 * @param T 返回数据类型
 * @param P presenter
 */
abstract class MvpRequestOneListDataBaseActivity<T, out P> :
        MvpLoadingActivity<P>()
        , RequestDataContract.View<List<T>>, SwipeRefreshLayout.OnRefreshListener {

    //数据对象
    protected var mDatas: MutableList<T> = arrayListOf()
    //适配器
    protected var mAdapter: BaseQuickAdapter<T, *>? = getAdapter()


    override fun onStart() {
        super.onStart()
        if (isCreate) {
            isCreate = false
            initAdapter()
            onRefresh()
        }
    }

    override fun handlerComplete(msg: String?) {
        super.handlerComplete(msg)
        (getSwipeRefreshLayout() as? SwipeRefreshLayout?)?.isRefreshing=false

    }

    override fun handlerFail(error: NetError) {
        super.handlerFail(error)
        (getSwipeRefreshLayout() as? SwipeRefreshLayout?)?.isRefreshing=false
    }
    override fun onRetry() {
        onRefresh()
    }

    /**
     * 初始化适配器
     */
    private fun initAdapter() {
        //适配器
        mAdapter?.apply {
            setLoadMoreView(CustomLoadMoreView())
            BasicAdapterHelper.initAdapterVertical(mActivity, mAdapter, getRecyclerView(), getEmptyTip())
            getRecyclerView()?.adapter = this
        }
        //刷新
        (getSwipeRefreshLayout() as? SwipeRefreshLayout?)?.setOnRefreshListener(this)
        //点击
        getRecyclerView()?.apply {
            addOnItemTouchListener(object : OnItemChildClickListener() {
                override fun onSimpleItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
                    //回调
                    onRecyclerViewItemChildClick(view, position, mDatas[position])
                }
            })
            //看看是不是网格布局
            getGridLayoutManager()?.apply {
                getRecyclerView()?.layoutManager = this
            }
        }
    }

    override  fun  requestDataSuccess(data: List<T>) {
        this.mDatas = data as MutableList<T>
        mAdapter?.setNewData(mDatas)
        showContent()
        (getSwipeRefreshLayout() as? SwipeRefreshLayout?)?.isRefreshing=false
    }

    protected open fun getSpanCount() = 0

    /**
     * 设置网格布局
     */
    private fun getGridLayoutManager(): GridLayoutManager? {
        if (getSpanCount() > 0) {
            return GridLayoutManager(mActivity, getSpanCount())
        }
        return null
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

     * @param adapter  适配器
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

    override fun requestResultIsEmpty() {
        mAdapter?.setNewData(arrayListOf())
        showContent()
    }


}