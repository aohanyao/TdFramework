package com.td.framework.mvp.base

import com.td.framework.mvp.contract.RequestDataContract
import com.td.framework.utils.L

/**
 * Created by jc on 7/27/2017.
 * <p>版本:1.0.0</p>
 * <b>说明<b><br/>
 * <li>专门用来请求一次数据的MVP基类</li>
 * @param T 返回数据类型
 * @param P presenter
 */
abstract class MvpRequestDataBaseFragment< P,out T> : MvpBaseFragment<P>()
        , RequestDataContract.View<T> {

    override fun requestResultIsEmpty() {
        L.e("请求内容为空")
    }
}