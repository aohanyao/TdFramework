package com.td.framework.expand

import android.Manifest
import android.app.Activity
import android.support.v4.app.Fragment
import com.tbruyelle.rxpermissions2.RxPermissions
import com.td.framework.utils.L
import com.td.framework.utils.T
import org.jetbrains.anko.support.v4.act

/**
 * Created by jc on 2017/8/8.
 * <p>版本:1.0.0</p>
 * <b>说明<b><br/>
 * <li>权限相关</li>
 */

//************************************************************************Activity start
/**
 * 请求权限
 */
fun Activity.requestRxPermissions(vararg requestPermissions: String, block: () -> Unit) {
    val rxPermissions = RxPermissions(this)
    rxPermissions.setLogging(L.isDebug)
    rxPermissions.request(*requestPermissions)
            .subscribe({
                if (it) {
                    block.invoke()
                } else {
                    T.showToast(applicationContext, "哎呀,权限被拒了,无法进行后续操作了..")
                }
            })
}

/**
 * 请求地图相关权限
 */
fun Activity.requestMapPermissions(block: () -> Unit) {
    requestRxPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION) {
        block.invoke()
    }
}

/**
 * 请求拨打电话的权限
 */
fun Activity.requestCallPhonePermissions(block: () -> Unit) {
    requestRxPermissions(Manifest.permission.CALL_PHONE) {
        block.invoke()
    }
}
//************************************************************************Activity end


//************************************************************************Fragment start
/**
 * 请求权限
 */
fun Fragment.requestRxPermissions(vararg requestPermissions: String, block: () -> Unit) {
    val rxPermissions = RxPermissions(activity!!)
    rxPermissions.setLogging(L.isDebug)
    rxPermissions.request(*requestPermissions)
            .subscribe({
                if (it) {
                    block.invoke()
                } else {
                    T.showToast(act!!.applicationContext, "哎呀,权限被拒了,无法进行后续操作了..")
                }
            })
}


/**
 * 请求地图相关权限
 */
fun Fragment.requestMapPermissions(block: () -> Unit) {
    requestRxPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION) {
        block.invoke()
    }
}

fun Fragment.requestCallPhonePermissions(block: () -> Unit) {
    requestRxPermissions(Manifest.permission.CALL_PHONE) {
        block.invoke()
    }
}
//************************************************************************Fragment end



