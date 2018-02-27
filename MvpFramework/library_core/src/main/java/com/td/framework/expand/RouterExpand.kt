package com.td.framework.expand

import android.app.Activity
import android.support.v4.app.Fragment
import com.td.framework.global.router.RouterHelper
import com.td.framework.utils.amin.JumpAnimUtils

/**
 * Created by jc on 2017/7/4 0004.
 * <p>版本:1.0.0</p>
 * <b>说明<b><br/>
 * <li>路由相关的拓展</li>
 */
/**
 *打开一个activity，并且传入参数。没有返回值
 * @param path 路由地址
 * @param pair  "key1" to "value1","key2" to "value2"
 */
fun Activity.navigationActivityFromPair(path: String, vararg pair: Pair<String, Any>?) {
    RouterHelper.navigationActivityFromPair(path, Activity@ this, *pair)
}

fun Fragment.navigationActivityFromPair(path: String, vararg pair: Pair<String, Any>?) {
    RouterHelper.navigationActivityFromPair(path, activity, *pair)
}

fun Fragment.navigationActivityForResult(path: String, requestCode: Int) {
    RouterHelper.navigationActivityForResult(path, activity, requestCode)
}

fun Fragment.navigationActivityFromPairForResult(path: String, requestCode: Int, vararg pair: Pair<String, Any>?) {
    RouterHelper.navigationActivityFromPairForResult(path, activity, requestCode, *pair)
}

/**
 * 通过路由路径打开一个Activity
 * @param path 路由地址
 */
fun Activity.navigationActivity(path: String) {
    RouterHelper.navigationActivity(path, Activity@ this)
    JumpAnimUtils.jumpTo(this)
}

/**
 * 跳转 有返回值的
 */
fun Activity.navigationActivityForResult(path: String, requestCode: Int) {
    RouterHelper.navigationActivityForResult(path, Activity@ this, requestCode)
}

/**
 * 通过路由路径打开一个Activity
 * @param path 路由地址
 */
fun Activity.navigationActivityFromPairForResult(path: String, requestCode: Int, vararg pair: Pair<String, Any>) {
    RouterHelper.navigationActivityFromPairForResult(path, Activity@ this, requestCode, *pair)
    /**
     *打开一个activity，并且传入参数。没有返回值
     * @param path 路由地址
     * @param pair  "key1" to "value1","key2" to "value2"
     */
    fun Fragment.navigationActivityFromPair(path: String, vararg pair: Pair<String, Any>) {
        //TODO 不知道会不会有问题
        navigationActivityFromPair(path, *pair)
    }
}