package com.td.framework.expand

import android.content.Context
import android.widget.Toast

fun Context.showText(content: String): Toast {
    val toast = Toast.makeText(this,content,Toast.LENGTH_SHORT)
    toast.show()
    return toast
}