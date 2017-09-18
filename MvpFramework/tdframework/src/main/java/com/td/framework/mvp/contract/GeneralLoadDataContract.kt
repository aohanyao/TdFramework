package com.td.framework.mvp.contract

import com.td.framework.biz.ApiSubscriber
import com.td.framework.mvp.presenter.BasePresenter
import com.td.framework.mvp.view.BaseView
import io.reactivex.Flowable
import org.reactivestreams.Subscriber

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

     * @param <T> 泛型 返回的实体数据
    </T> */
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
         * 已经没有数据了
         */
        fun noMore()
    }

    /**
     * 通用的加载数据P层

     * @param <V> View 层，必须继承GeneralLoadDataView
     * *
     * @param <T>
    </T></V> */
    abstract class GeneralLoadDataPresenter<V : GeneralLoadDataView<*>, T>(view: V) : BasePresenter<V>(view) {
        /**
         * 每页大小
         */
        /**
         * 获取每页大小

         * @return
         */
        protected val pageSize = 10

        /**
         * 刷新数据
         *
         * 自动判断PageSize和PageIndex
         * 可覆写getPageSize() 方法 设置页大小
         */
        fun refreshData() {

            /* setSubscribe(*/getRefreshObservable()
                    .compose(getCompose<Any>())
                    .subscribe(object : ApiSubscriber<Any>(v) {
                        override fun onNext(o: Any) {
                            //强转类型
                            val list = o as List<T>
                            //返回数据
                            v.refreshSuccess(list)
                            //计算是否有更多
                            if (list != null && list.size != pageSize) {
                                v.noMore()
                            }
                        }

                    })/*)*/
        }

        /**
         *
         * 自动判断PageSize和PageIndex
         * 可覆写getPageSize() 方法 设置页大小
         */
        fun loadMoreData() {

            val flowable = Flowable.just("1")
            flowable.subscribe { }
//            disposable=
            /*setSubscribe(*/getLoadMoreObservable()
                    .compose(getCompose<Any>())
                    .subscribeWith<Subscriber<T>>(object : ApiSubscriber<Any>(v) {
                        override fun onNext(o: Any) {
                            //强转类型
                            val list = o as List<T>
                            //返回数据
                            v.loadMoreSuccess(list)
                            //计算是否有更多
                            if (list != null && list.size != pageSize) {
                                v.noMore()
                            }
                        }

                    })/*)*/
        }


        /**
         * 返回刷新的Observable

         * @return
         */
        protected abstract fun getRefreshObservable(): Flowable<List<T>>

        /**
         * 返回加载更多的Observable

         * @return
         */
        protected abstract fun getLoadMoreObservable(): Flowable<List<T>>

    }
}
