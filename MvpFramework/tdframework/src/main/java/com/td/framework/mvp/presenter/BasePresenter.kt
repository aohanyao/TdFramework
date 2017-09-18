/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.td.framework.mvp.presenter

import com.trello.rxlifecycle2.components.RxActivity
import com.trello.rxlifecycle2.components.RxFragment
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.trello.rxlifecycle2.components.support.RxFragmentActivity
import io.reactivex.FlowableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * 基类P
 */
abstract class BasePresenter<V>(val v: V) {
    //需要处理一些 统一的异常信息
    protected var TAG = javaClass.simpleName
    protected var disposable: Disposable? = null


    /**
     * 取消订阅
     *
     *防止内存泄露
     *
     * 用户取消网络请求
     */
    fun unSubscribe() {
        try {

            disposable!!.dispose()
        } catch (ignored: Exception) {
        }

    }

    /**
     * 已订阅，暂留，用来做后续的优化
     */
    open fun subscribe() {

    }

    /**
     * 获取View相关视图

     * @return
     */
    //     protected fun getV(): V {
//        if (v !is BaseView) {
//            throw RuntimeException("your V request extend BaseView")
//        }
//        return v
//    }

    /**
     *
     *  *  1. 线程切换
     *  *  2. Rx生命周期绑定
     *
     * @return
     */
    protected fun <T> getCompose(): FlowableTransformer<T, T> {
        return FlowableTransformer { observable ->
            observable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose<T>(when (v) {
                        is RxAppCompatActivity -> v.bindToLifecycle<T>()
                        is RxFragmentActivity -> v.bindToLifecycle<T>()
                        is RxActivity -> v.bindToLifecycle<T>()
                        is RxFragment -> v.bindToLifecycle<T>()
                        is com.trello.rxlifecycle2.components.support.RxFragment -> v.bindToLifecycle<T>()
                        else -> (v as RxAppCompatActivity).bindToLifecycle<T>()
                    })
        }
    }
}
