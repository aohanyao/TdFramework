package com.td.framework.mvp.contract

import com.td.framework.model.bean.BaseCodeMsgInfo
import com.td.framework.mvp.model.BaseParamsInfo
import com.td.framework.mvp.presenter.BasePresenter
import com.td.framework.mvp.view.BaseView
import io.reactivex.Flowable

/**
 * Created by 江俊超 on 7/26/2017.
 *
 * 版本:1.0.0
 * **说明**<br></br>
 *  * 获取数据数据相关的合同
 **** */
interface RequestDataContract {
    /**
     * @param T 返回值数据类型
     */
    interface View<out T> : BaseView {
        /**
         * 请求数据成功
         */
        fun requestDataSuccess(data: @UnsafeVariance T)

        /**
         * 请求数据失败
         */
        fun requestResultIsEmpty()

        /**
         * 提交数据成功，可以选择重写这个方法
         */
        fun commitDataSuccess() {
        }

        /**
         * 提交数据失败
         */
        fun commitDataFail(msg:String?) {
        }
    }


    /**
     * @param RT 返回值数据类型
     *@param RP 请求参数类型 实现[BaseParamsInfo]接口
     */
    abstract class Presenter<V : View<*>, RT, in RP : BaseParamsInfo>(v: V) : BasePresenter<V>(v) {

        /**
         * 获取数据
         * 参数对象，使用此方法需要实现[com.td.framework.mvp.model.BaseParamsInfo]接口
         * 在子类中获取相对应的数值对象
         * @param params 参数对象
         */
        fun requestData(params: RP) {
            //取消前一次请求
            unSubscribe()
            //开始请求
            request(getRequestDataObservable(params)) {
                if (it != null) {
                    completeRequest(it)
                    v.requestDataSuccess(it)
                } else {
                    v.requestResultIsEmpty()
                }
            }

        }

//        @Deprecated("未完善，请勿使用", ReplaceWith("null"))
        fun commitData( commitParam: BaseParamsInfo) {
            getCommitDataObservable(commitParam)?.apply {
                //取消前一次请求
                unSubscribe()
                //开始请求
                request(this) {
                    if (it != null && it.code == 200) {
                        v.commitDataSuccess()
                    } else {
                        v.commitDataFail(it?.msg)
                    }
                }
            }
        }

        /**
         * 刷新数据完成，可以重写这个方法进行缓存

         * @param responseData
         */
        protected fun completeRequest(responseData: RT) {
        }

        /**
         * 获取请求数据
         */
        protected abstract fun getRequestDataObservable(params: RP): Flowable<RT>

        /**
         * 提交数据
         */
//        @Deprecated("未完善，请勿使用", ReplaceWith("null"))
        protected open fun getCommitDataObservable(params:BaseParamsInfo): Flowable<BaseCodeMsgInfo>? {
            return null
        }

    }
}
