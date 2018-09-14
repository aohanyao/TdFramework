package com.td.framework.mvp.contract

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
 *  * 提交数据数据相关的合同
 *
 *  ------------------------
 *  2018年3月7日12:14:09
 *  更名PostContract
 *  ------------------------
 *
 * */
interface PostContract {
    /**
     * @param T 返回值数据类型
     */
    interface View : BaseView {

        /**
         * 提交数据成功，可以选择重写这个方法
         */
        fun postSuccess()

        /**
         * 提交数据失败
         */
        fun postFail(msg: String?)
    }


    /**
     *@param RP 请求参数类型 实现[BaseParamsInfo]接口
     */
    abstract class Presenter<V : View, in RP : BaseParamsInfo>(v: V) : BasePresenter<V>(v) {

        /**
         * 已经数据
         */
        fun postData(commitParam: RP) {
            getPostFlowable(commitParam)?.apply {
                //v.showLoading(R.string.commit_data_loding)
                //取消前一次请求
                unSubscribe()
                //开始请求
                request(this) {
                    v.complete("")
                    if (it != null && it.code == 200) {
                        v.postSuccess()
                    } else {
                        v.postFail(it?.message)
                    }
                }
            }
        }


        /**
         * 提交数据
         */
        protected abstract fun getPostFlowable(params: RP): Flowable<CodeMsgModel>?

    }
}
