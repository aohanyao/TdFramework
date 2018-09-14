package com.td.framework.expand

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.view.View
import com.td.framework.global.app.Constant
import com.td.framework.global.router.RouterHelper
import com.td.framework.utils.amin.JumpAnimUtils
import java.io.Serializable

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
        navigationActivityFromPair(path, *pair)
    }
}

/**
 * 用动画的方式打开一个Activity
 * @param intentData     需要传递的数据对象
 * *
 * @param sharedElements 需要分享的展现动画的元素
 */
inline fun <reified T : Activity> Activity.launchActivityFromSharedElementsTransition(
        intentData: Serializable?,
        vararg sharedElements: android.util.Pair<View, String>) {

    val intent = Intent(this, T::class.java)
    if (intentData != null) {
        intent.putExtra(Constant.IDK, intentData)
    }
    //5.0及以上使用过度动画
    if (Build.VERSION.SDK_INT > 21) {
        val options = ActivityOptions.makeSceneTransitionAnimation(this, *sharedElements)
        ActivityCompat.startActivity(this, intent, options.toBundle())
    } else {
        val transitionView = sharedElements[0].first
        //让新的Activity从一个小的范围扩大到全屏
        val options = ActivityOptionsCompat.makeScaleUpAnimation(transitionView, //The View that the new activity is animating from
                transitionView.width.toInt() / 2, transitionView.height.toInt() / 2, //拉伸开始的坐标
                0, 0)//拉伸开始的区域大小，这里用（0，0）表示从无到全屏
        ActivityCompat.startActivity(this, intent, options.toBundle())
    }
}