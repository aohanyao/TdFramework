package com.td.framework.mvp.contract

import com.td.framework.model.bean.BaseDataModel
import com.td.framework.mvp.model.BaseParamsInfo
import com.td.framework.mvp.presenter.BasePresenter
import com.td.framework.mvp.view.BaseView
import io.reactivex.Flowable

/**
 * Created by jc on 2018年3月16日15:54:59
 *
 * 版本:1.0.0
 * **说明**<br></br>
 *  * 获取数据数据相关的合同
 *
 * */
interface GetContract {
    /**
     * @param T 返回值数据类型
     */
    interface View<T> : BaseView {

        /**
         * 提交数据成功
         */
        fun getSuccess(data: T)

        /**
         * 提交数据失败
         */
        fun getFail(msg: String?)
    }


    /**
     *@param RP 请求参数类型 实现[BaseParamsInfo]接口
     * @param V view
     * @param T 数据类型
     */
    abstract class Presenter<V : View<T>, in RP : BaseParamsInfo, T>(v: V) : BasePresenter<V>(v) {

        /**
         * 获取数据
         */
        fun getData(getParam: RP) {
            getGetFlowable(getParam)?.apply {
                //取消前一次请求
                unSubscribe()
                //开始请求
                request(this) {
                    if (it != null && it.code == 200) {
                        completeRequest(it.data)
                        v.getSuccess(it.data)
                        v.complete("")
                    } else {
                        v.complete("")
                        v.getFail(it?.message)
                    }
                }
            }
        }


        /**
         * 提交数据
         */
        protected abstract fun getGetFlowable(params: RP): Flowable<BaseDataModel<T>>?
        /**
         * 数据请求完成，可以重写这个方法进行缓存
         * @param data
         */
        protected open fun completeRequest(data: T) {

        }

    }
}
