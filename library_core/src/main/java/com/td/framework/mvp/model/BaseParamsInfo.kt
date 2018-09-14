package com.td.framework.mvp.model

import java.io.Serializable
import java.util.*

/**
 * Created by jc on 7/26/2017.
 *
 * 版本:1.0.0
 * **说明**<br></br>
 *  * 基本的参数对象
 **** */
interface BaseParamsInfo : Serializable {
    /**将参数转换为map*/
    fun /*<T : BaseParamsInfo> */mapToParams(/*param: T*/): Map<String, Any> {
        //创建参数集合
        val mParamsMap = WeakHashMap<String, Any>()
//        //获取反射
//        this.javaClass
//                .kotlin
//                .memberProperties
//                .forEach {
//                    mParamsMap.put(it.name, it.get(this).toString())
//                }
        return mParamsMap
    }
}
