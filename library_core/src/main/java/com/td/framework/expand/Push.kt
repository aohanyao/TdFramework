package com.td.framework.expand

import android.content.Intent
import java.io.Serializable

/**
 * Created by jc on 2018-08-24.
 * Version:1.0
 * Description:
 * ChangeLog:
 */

/**从 intent 和extras中获取字符串*/
fun Intent?.getStringDataExtra(key: String): String {
    return this?.getStringExtra(key) ?: this?.extras?.getString(key) ?: ""
}

/**从 intent 和extras中获取Serializable*/
fun Intent?.getSerializableDataExtra(key: String): Serializable? {
    return this?.getSerializableExtra(key) ?: this?.extras?.getSerializable(key)
}