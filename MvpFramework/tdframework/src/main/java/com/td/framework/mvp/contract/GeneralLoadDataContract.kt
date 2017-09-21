package com.td.framework.mvp.contract

import com.td.framework.model.bean.BaseCodeMsgInfo
import com.td.framework.mvp.model.BaseParamsInfo
import com.td.framework.mvp.presenter.BasePresenter
import com.td.framework.mvp.view.BaseView
import io.reactivex.Flowable

/**
 * Created by 江俊超
 *
 * 版本:1.0.0
 * **说明**通用加载数据合同<br></br>
 *  *
 **** */
interface GeneralLoadDataContract {
    /**
     * 通用加载数据的View层
     *
     * @param T 泛型 返回的实体数据
     */
    interface GeneralLoadDataView<out T> : BaseView {

        /**
         * 刷新数据

         * @param datas
         */
        fun refreshSuccess(datas: List<@UnsafeVariance T>)

        /**
         * 加载更多数据

         * @param datas
         */
        fun loadMoreSuccess(datas: List<@UnsafeVariance T>)

        /**
         * 提交数据成功
         */
        fun commitDataSuccess()

        /**
         * 提交数据失败
         */
        fun commitDataFail()

        /**
         * 已经没有数据了
         */
        fun noMore()
    }

    /**
     * 通用的加载数据P层
     * @param V View 层，必须继承GeneralLoadDataView
     * @param T 数据对象
     * */
    abstract class GeneralLoadDataPresenter<V : GeneralLoadDataView<*>, T, out RP : BaseParamsInfo>(view: V) : BasePresenter<V>(view) {
        /**
         * 每页大小
         */
        /**
         * 获取每页大小

         * @return
         */
        protected val pageSize = 10
        /**
         * 页码
         */
        protected var mPagerIndex = 1

        /**
         * 刷新数据
         *
         * 自动判断PageSize和PageIndex
         * 可覆写getPageSize() 方法 设置页大小
         */
        fun refreshData(params: @UnsafeVariance RP) {
            //页码
            mPagerIndex = 1
            //取消前一次请求
            unSubscribe()
            //开始请求
            request(getRequestDataObservable(params)) {
                //强转类型
                val list = it ?: arrayListOf()
                //返回数据
                v.refreshSuccess(list)
                //计算是否有更多
                if (list.size != pageSize) {
                    v.noMore()
                }
            }
        }

        /**
         *
         * 自动判断PageSize和PageIndex
         * 可覆写getPageSize() 方法 设置页大小
         */
        fun loadMoreData(params: @UnsafeVariance RP) {
            //页码
            mPagerIndex++
            //取消前一次请求
            unSubscribe()
            //开始请求
            request(getRequestDataObservable(params)) {
                //强转类型
                val list = it ?: arrayListOf()
                //返回数据
                v.loadMoreSuccess(list)
                //计算是否有更多
                if (list.size != pageSize) {
                    v.noMore()
                }
            }
        }

        /**
         * 提交数据
         */
        fun <TRP : BaseParamsInfo> commitData(param: TRP) {
            //取消请求
            unSubscribe()
            //开始请求
            getCommitDataObservable(param)?.apply {
                request(this) {
                    if (it?.code == 200) {
                        v.commitDataSuccess()
                    } else {
                        v.commitDataFail()
                    }
                }
            }
        }

        /**
         * 请返回获取数据的Observable
         * @param pagerIndex 当前页码
         * @return
         */
        protected abstract fun getRequestDataObservable(params: @UnsafeVariance RP): Flowable<List<T>>

        protected open fun <TRP : BaseParamsInfo> getCommitDataObservable(params: TRP): Flowable<BaseCodeMsgInfo>? {
            return null
        }
    }

}
