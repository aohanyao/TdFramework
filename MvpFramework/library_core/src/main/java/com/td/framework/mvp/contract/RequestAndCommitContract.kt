package com.td.framework.mvp.contract

import com.td.framework.mvp.model.BaseParamsInfo
import com.td.framework.mvp.presenter.BasePresenter
import com.td.framework.mvp.view.BaseView
import io.reactivex.Flowable

/**
 * Created by jc on 7/26/2017.
 *
 * 版本:1.0.0
 * **说明**<br></br>
 *  * 获取数据,提交数据相关的合同
 **** */
interface RequestAndCommitContract {
    /**
     * @param RT requestT 刷新数据返回类型
     * @param CT commitT  提交数据返回类型
     */
    interface View<out RT, out CT> : BaseView {
        /**
         * 属性数据成功
         */
        fun requestDataSuccess(refreshData: @UnsafeVariance RT?)

        /**
         * 提交数据成功
         */
        fun commitDataSuccess(commitResponse: @UnsafeVariance CT)

        /**
         * 请求数据失败
         */
        fun requestResultIsEmpty()

        /**
         * 提交数据失败
         */
        fun commitDataFail()
    }

    /**
     * @param V [View]
     * @param RT response T 返回值类型
     * @param RP refresh params 刷新参数类型
     * @param CP commit params 提交数据参数类型
     */
    abstract class Presenter<V : View<*, *>, RT, in RP : BaseParamsInfo, in CP : BaseParamsInfo>(v: V) : BasePresenter<V>(v) {

        /**
         * 获取数据
         * 参数对象，使用此方法需要实现[com.td.framework.mvp.model.BaseParamsInfo]接口
         * 在子类中获取相对应的数值对象

         * @param params 参数对象
         */
        fun refreshData(params: RP?) {
            //取消前一次请求
            unSubscribe()
            //开始请求
            request(getRefreshDataObservable(params)) {
                if (it != null) {
                    completeRequest(it)
                    v.requestDataSuccess(it)
                } else {
                    v.requestResultIsEmpty()
                }
            }
        }

        /**
         * 提交数据
         * 参数对象，使用此方法需要实现[com.td.framework.mvp.model.BaseParamsInfo]接口
         * 在子类中获取相对应的数值对象

         * @param params 参数对象
         */
        fun commitData(params: CP) {
            //取消前一次请求
            unSubscribe()
            //开启请求
            request(getCommitDataObservable(params)) {
                //判断是否为空
                it?.let {
                    completeCommit(it)
                    v.commitDataSuccess(it)
                }
                it ?: let { v.commitDataFail() }
            }

        }

        /**
         * 刷新数据完成，可以重写这个方法进行缓存
         * @param refreshResponse
         */
        protected fun completeRequest(refreshResponse: RT) {

        }

        /**
         * 提交数据完成，可以重写这个方法进行缓存
         * @param commitResponse
         */
        protected fun completeCommit(commitResponse: RT) {

        }

        protected abstract fun getCommitDataObservable(params: CP): Flowable<RT>

        protected abstract fun getRefreshDataObservable(params: RP?): Flowable<RT>
    }
}
