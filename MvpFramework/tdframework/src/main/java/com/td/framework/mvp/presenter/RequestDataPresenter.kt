package com.td.framework.mvp.presenter

import com.td.framework.mvp.contract.RequestDataContract
import com.td.framework.mvp.model.BaseParamsInfo

/**
 * Created by 江俊超 on 7/27/2017.
 * <p>版本:1.0.0</p>
 * <b>说明<b><br/>
 * <li>只有一次请求的基本的请求P</li>
 * @param T 返回数据类型
 * @param RP 请求参数类型
 */
abstract class RequestDataPresenter<T, in RP : BaseParamsInfo>(view: RequestDataContract.View<T>)
    : RequestDataContract.Presenter<RequestDataContract.View<T>, T, RP>(view)