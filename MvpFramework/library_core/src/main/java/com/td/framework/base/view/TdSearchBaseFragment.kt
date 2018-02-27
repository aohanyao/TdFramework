package com.td.framework.base.view

import com.td.framework.base.inf.SearchInf
import com.td.framework.mvp.base.MvpRequestDataBaseFragment
import com.td.framework.mvp.model.BaseParamsInfo

/**
 * Created by jc on 7/31/2017.
 * <p>版本:1.0.0</p>
 * <b>说明<b><br/>
 * <li>搜索相关的基类fragment</li>
 */
abstract class TdSearchBaseFragment< P, out T> : MvpRequestDataBaseFragment<P, T>(), SearchInf {

    /**
     * 设置参数
     */
    override fun setParams(param: BaseParamsInfo) {

    }

}