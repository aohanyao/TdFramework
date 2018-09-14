package com.td.framework.global.router

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavigationCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.td.framework.R
import com.td.framework.global.app.App
import com.td.framework.utils.T
import java.io.Serializable
import java.util.*

/**
 * Created by jc on 2017/6/28 0028.
 *
 * 版本:1.0.0
 * **说明**<br></br>
 *  * 路由帮助类
 * * 并不建议直接使用这个类
 * */
object RouterHelper : NavigationCallback {


    /**
     * 跳转页面
     *
     * 没有任何参数的

     * @param path      需要跳转的路径
     * *
     * @param mActivity 共享动画
     */
    fun navigationActivity(path: String, mActivity: Context?) {
        buildRouter(path)
                .navigation(mActivity, this)
    }

    fun navigationActivity(path: String) {
        buildRouter(path)
                .navigation()
    }

    /**
     * 跳转页面，传递一个 Bundle 数据

     * @param path
     * *
     * @param mActivity
     * *
     * @param bundle
     */
    fun navigationActivity(path: String, mActivity: Context?, bundle: Bundle?) {
        if (bundle == null) {
            navigationActivity(path, mActivity)
        } else {
            buildRouter(path)
                    .with(bundle)
                    .navigation(mActivity, this)
        }
    }

    fun navigationActivity(path: String, mActivity: Context?, bundle: Bundle?, isGreen: Boolean) {
        if (isGreen) {
            buildRouter(path)
                    .with(bundle)
                    .greenChannel()
                    .navigation(mActivity, this)
        } else {
            navigationActivity(path, mActivity, bundle)
        }
    }

    /**
     * 跳转页面，有返回值的

     * @param path
     * *
     * @param mActivity
     * *
     * @param requestCode
     */
    fun navigationActivityForResult(path: String, mActivity: Activity?, requestCode: Int) {
        buildRouter(path)
                .navigation(mActivity, requestCode, this)
    }

    /**
     * 跳转页面，有返回值，且携带数据

     * @param path
     * *
     * @param mActivity
     * *
     * @param bundle
     * *
     * @param requestCode
     */
    fun navigationActivityForResult(path: String, mActivity: Activity?, bundle: Bundle, requestCode: Int) {
        buildRouter(path)
                .with(bundle)
                .navigation(mActivity, requestCode, this)
    }

    /**
     * 传递参数
     * 有返回值
     */
    fun navigationActivityFromPairForResult(path: String,
                                            mActivity: Activity?,
                                            requestCode: Int,
                                            vararg pair: Pair<String, Any>?) {
        //创建路由
        val router = buildRouterForParams(path, *pair)
        //导航
        router.navigation(mActivity, requestCode, this)
    }

    /**
     * 传递参数
     */
    fun navigationActivityFromPair(path: String, mActivity: Activity?, vararg pair: Pair<String, Any>?) {
        navigationActivityFromPairForResult(path, mActivity, -1, *pair)
    }


    /**
     * 创建路由

     * @param path
     * *
     * @return
     */
    private fun buildRouter(path: String): Postcard {
        return ARouter.getInstance()
                .build(path)
                .withTransition(R.anim.setup_next_in,
                        R.anim.setup_next_out)
    }

    /**
     * 创建携带参数的路由
     */
    fun buildRouterForParams(path: String, needAmin: Boolean,
                             vararg params: Pair<String, Any>?): Postcard {

        val router = ARouter.getInstance()
                .build(path)
        if (needAmin) {
            router.withTransition(R.anim.setup_next_in,
                    R.anim.setup_next_out)
        }
        //获取参数，创建 key value
        params.forEach {
            val value = it?.second
            when (value) {
                null -> router.withSerializable(it?.first, null as Serializable?)
                is Int -> router.withInt(it.first, value)
                is Long -> router.withLong(it.first, value)
                is CharSequence -> router.withCharSequence(it.first, value)
                is String -> router.withString(it.first, value)
                is Float -> router.withFloat(it.first, value)
                is Double -> router.withDouble(it.first, value)
                is Char -> router.withChar(it.first, value)
                is Short -> router.withShort(it.first, value)
                is Boolean -> router.withBoolean(it.first, value)
                is Serializable -> router.withSerializable(it.first, value)
                is Bundle -> router.withBundle(it.first, value)
                is Parcelable -> router.withParcelable(it.first, value)
                is Array<*> -> when {
                    value.isArrayOf<CharSequence>() -> router.withCharSequenceArray(it.first, value as Array<out CharSequence>)
                    value.isArrayOf<String>() -> router.withStringArrayList(it.first, value as ArrayList<String>)
                    value.isArrayOf<Parcelable>() -> router.withParcelableArrayList(it.first, value as ArrayList<out Parcelable>)
                }
            }
            return@forEach
        }
        //返回路由
        return router

    }

    fun buildRouterForParams(path: String, vararg params: Pair<String, Any>?): Postcard {

        return buildRouterForParams(path, true, *params)

    }

    override fun onLost(postcard: Postcard?) {
        T.showToast(App.newInstance(), "哎呀,页面失踪了....")

    }

    override fun onFound(postcard: Postcard?) {
//        postcard.
//        postcard.
//        T.showToast(App.newInstance(), "onFound....")

    }

    override fun onInterrupt(postcard: Postcard?) {
    }

    override fun onArrival(postcard: Postcard?) {

    }
}
