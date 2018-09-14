package com.td.framework.mvp.presenter

import com.td.framework.mvp.contract.GetContract
import com.td.framework.mvp.model.BaseParamsInfo

/**
 * Created by jc on 2018年3月16日15:58:54
 * <p>版本:1.0.0</p>
 * <b>说明<b><br/>
 * <li>获取数据的基类P</li>
 * @param RP 请求参数类型
 */
abstract class GetDataPresenter<in RP : BaseParamsInfo, T>(view: GetContract.View<T>)
    : GetContract.Presenter<GetContract.View<T>, RP, T>(view)