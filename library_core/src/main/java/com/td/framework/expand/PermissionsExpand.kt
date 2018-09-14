package com.td.framework.expand

import android.Manifest
import android.app.Activity
import android.support.v4.app.Fragment
import com.tbruyelle.rxpermissions2.RxPermissions
import com.td.framework.utils.L
import com.td.framework.utils.T

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
fun Activity.requestRxPermissions(vararg requestPermissions: String, block: (isSuccess: Boolean) -> Unit) {
    val rxPermissions = RxPermissions(this)
    rxPermissions.setLogging(L.isDebug)
    rxPermissions.request(*requestPermissions)
            .subscribe({
                block.invoke(it)
                if (!it) {
                    T.showToast(applicationContext, "哎呀,权限被拒了,无法进行后续操作了..")
                }
            })
}

fun Activity.checkRxPermission(permission: String): Boolean {
    val rxPermissions = RxPermissions(this)
    return rxPermissions.isGranted(permission)
}

/**
 * 请求地图相关权限
 */
fun Activity.requestMapPermissions(block: (isSuccess: Boolean) -> Unit) {
    requestRxPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION) {
        block.invoke(it)
    }
}

/**
 * 请求存储相关的权限
 * <p>照片</p>
 */
fun Activity.requestStoragePermissions(block: (isSuccess: Boolean) -> Unit) {
    requestRxPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE) {
        block.invoke(it)
    }
}

/**请求相机*/
fun Activity.requestCameraPermissions(block: (isSuccess: Boolean) -> Unit) {
    requestRxPermissions(Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE) {
        block.invoke(it)
    }
}
/**
 * 请求拨打电话的权限
 */
fun Activity.requestCallPhonePermissions(block: (isSuccess: Boolean) -> Unit) {
    requestRxPermissions(Manifest.permission.CALL_PHONE) {
        block.invoke(it)
    }
}
//************************************************************************Activity end


//************************************************************************Fragment start
/**
 * 请求权限
 */
fun Fragment.requestRxPermissions(vararg requestPermissions: String, block: (isSuccess: Boolean) -> Unit) {
    val rxPermissions = RxPermissions(activity!!)
    rxPermissions.setLogging(L.isDebug)
    rxPermissions.request(*requestPermissions)
            .subscribe({
                block.invoke(it)
                if (!it) {
                    T.showToast(act!!.applicationContext, "哎呀,权限被拒了,无法进行后续操作了..")
                }
            })
}


/**
 * 请求地图相关权限
 */
fun Fragment.requestMapPermissions(block: (isSuccess: Boolean) -> Unit) {
    requestRxPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION) {
        block.invoke(it)
    }
}

fun Fragment.requestCallPhonePermissions(block: (isSuccess: Boolean) -> Unit) {
    requestRxPermissions(Manifest.permission.CALL_PHONE) {
        block.invoke(it)
    }
}

/**
 * 请求存储相关的权限
 * <p>照片</p>
 */
fun Fragment.requestStoragePermissions(block: (isSuccess: Boolean) -> Unit) {
    requestRxPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE) {
        block.invoke(it)
    }
}

//************************************************************************Fragment end



