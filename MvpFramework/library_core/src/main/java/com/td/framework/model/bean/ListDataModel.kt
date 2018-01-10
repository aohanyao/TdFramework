package com.td.framework.model.bean

/**
 * Created by jc on 2017/7/21 0021.
 *
 * 版本:1.0.0
 * **说明**<br></br>
 *  * 基本的列表数据返回对象接口，请一定要实现该接口
 **** */
interface ListDataModel<T> {
    /**
     * 返回最大页码
     *
     * @return
     */
    val maxPage: Int

    /**
     * 返回数据结果
     *
     * @return
     */
    val list: List<T>
}
