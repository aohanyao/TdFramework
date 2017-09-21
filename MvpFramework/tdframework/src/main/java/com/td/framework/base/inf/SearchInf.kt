package com.td.framework.base.inf

import com.td.framework.mvp.model.BaseParamsInfo

/**
 * Created by 江俊超 on 8/14/2017.
 * <p>版本:1.0.0</p>
 * <b>说明<b><br/>
 * <li></li>
 */
interface SearchInf {
    /**
     * 设置参数
     */
    fun setParams(param: BaseParamsInfo)

    /**
     * 开始搜索
     */
    fun searchKeyword(keyword: String)
}