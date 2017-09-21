package com.td.framework.mvp.view

/**
 * Created by 江俊超 on 2017/2/15 0015.
 *
 * 版本:1.0.0
 * **说明**<br></br>
 *  * 列表的View *
 **** */
interface BaseListView<T> : BaseView {
    /**
     * 刷新的数据

     * @param infos
     */
    fun refreshData(infos: List<T>)

    /**
     * 加载更多的数据

     * @param infos
     */
    fun loadMoresData(infos: List<T>)

    /**
     * 没有更多的数据了
     */
    fun noMoreData()
}
