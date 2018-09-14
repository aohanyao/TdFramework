package com.td.framework.mvp.contract

/**
 * Created by jc on 2018-07-26.
 * Version:1.0
 * Description:
 * ChangeLog:
 */
interface Response<T> {
    fun response(result: T)
}

interface Failure {
    fun failure(message:String?)
}