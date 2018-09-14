package com.td.framework.mvp.contract

import com.td.framework.biz.NetError
import com.td.framework.model.bean.BaseDataModel
import com.td.framework.model.bean.CodeMsgModel
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
interface PostAndGetContract {
    /**
     * @param PT post response T 刷新数据返回类型
     */
    interface View<out PT> : BaseView {
        /**
         * 获取数据成功
         */
        fun getDataSuccess(responseData: @UnsafeVariance PT?)

        /**
         * 提交数据成功
         */
        fun postDataSuccess()

        /**
         * 请求数据失败
         */
        fun getDataFail()

        /**
         * 提交数据失败
         */
        fun postDataFail(msg: String?)
    }

    /**
     * @param V [View]
     * @param RT response T 返回值类型
     * @param GP get data params 刷新参数类型
     * @param PP post data params 提交数据参数类型
     */
    abstract class Presenter<V : View<*>, RT, in GP : BaseParamsInfo, in PP : BaseParamsInfo>(v: V) : BasePresenter<V>(v) {

        /**
         * 获取数据
         * 参数对象，使用此方法需要实现[com.td.framework.mvp.model.BaseParamsInfo]接口
         * 在子类中获取相对应的数值对象

         * @param params 参数对象
         */
        fun getData(params: GP) {
            //取消前一次请求
            unSubscribe()
            //开始请求
            request(getDataObservable(params)) {
                if (it != null) {
                    if (it.code == 200) {
                        completeGet(it.data)
                        v.getDataSuccess(it.data)
                    } else {
                        v.onFail(NetError(it.message, NetError.OTHER))
                        v.getDataFail()
                    }
                } else {
                    v.getDataFail()
                }
            }
        }


        /**
         * 获取数据，在方法中直接返回数据
         * 参数对象，使用此方法需要实现[com.td.framework.mvp.model.BaseParamsInfo]接口
         * 在子类中获取相对应的数值对象

         * @param params 参数对象
         * @param success 成功的回调
         * @param fail 失败回调
         */
//        fun getData(params: GP, success: Response<RT>? = null/*, fail: Failure? = null*/) {
//            //取消前一次请求
//            unSubscribe()
//            //开始请求
//            request(getDataObservable(params)) {
//                if (it != null) {
//                    if (it.code == 200) {
//                        completeGet(it.data)
//                        if (success == null) {
//                            v.getDataSuccess(it.data)
//                        } else {
//                            success.response(it.data)
//                        }
//                    } else {
////                        if (fail == null) {
////                            v.onFail(NetError(it.message, NetError.OTHER))
////                            v.getDataFail()
////                        } else {
////                            // v.onFail(NetError(it.message, NetError.OTHER))
////                            // 在这里处理一些错误操作
////                            fail.failure(it.message)
////                        }
//                    }
//                } else {
////                    if (fail == null) {
////                        v.getDataFail()
////                    } else {
////                        fail.failure(null)
////                    }
//                }
//            }
//        }

        /**
         * 获取数据，在方法中直接返回数据
         * 参数对象，使用此方法需要实现[com.td.framework.mvp.model.BaseParamsInfo]接口
         * 在子类中获取相对应的数值对象

         * @param params 参数对象
         * @param success 成功的回调
         * @param fail 失败回调
         */
        fun getData(params: GP, success: (result: RT) -> Unit/*, fail: Failure? = null*/) {
            //取消前一次请求
            unSubscribe()
            //开始请求
            request(getDataObservable(params)) {
                if (it != null) {
                    if (it.code == 200) {
                        completeGet(it.data)
                        success.invoke(it.data)
                    } else {
                        v.onFail(NetError(it.message, NetError.OTHER))
                        v.getDataFail()
                    }
                } else {
                    v.getDataFail()
                }
            }
        }


        /**
         * 提交数据
         * 参数对象，使用此方法需要实现[com.td.framework.mvp.model.BaseParamsInfo]接口
         * 在子类中获取相对应的数值对象

         * @param params 参数对象
         */
        fun postData(params: PP) {
            //取消前一次请求
            unSubscribe()
            //开启请求
            request(postDataObservable(params)) {
                v.complete("")
                //判断是否为空
                if (it != null) {
                    if (it.code == 200) {
                        completePostData()
                        v.postDataSuccess()
                    } else {
                        //v.onFail(NetError(it.message, NetError.OTHER))
                        v.postDataFail(it.message)
                    }
                } else {
                    v.postDataFail(null)
                }
            }
        }

        /**
         * 刷新数据完成，可以重写这个方法进行缓存
         * @param refreshResponse
         */
        protected open fun completeGet(refreshResponse: RT?) {

        }

        /**
         * 完成提交数据
         */
        protected open fun completePostData() {}


        protected abstract fun getDataObservable(params: GP): Flowable<BaseDataModel<RT>>?

        protected abstract fun postDataObservable(params: PP): Flowable<CodeMsgModel>?
    }
}
