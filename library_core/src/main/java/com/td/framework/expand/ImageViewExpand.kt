package com.td.framework.expand

import android.widget.ImageView
import com.td.framework.R
import com.td.framework.global.helper.ImageHelper
import java.io.File

/**
 * Created by jc on 7/24/2017.
 * <p>版本:1.0.0</p>
 * <b>说明<b><br/>
 * <li>关于ImageView的拓展</li>
 */
/**
 * 通过url 加载图片
 */
fun ImageView.loadImage(url: String?) {

    if (url != null && url.isNotEmpty() && url.contains("http")) {
        //加载图片
        ImageHelper.loadImageFromGlide(context, url, this)
    } else {
        ImageHelper.loadImageFromGlide(context, R.drawable.image_loading, this)
    }
}

fun ImageView.loadImage(url: String?, defaultLoading: Int) {

    if (url != null && url.isNotEmpty() && url.contains("http")) {
        //加载图片
        ImageHelper.loadImageFromGlide(context, url, this, defaultLoading)
    } else {
        ImageHelper.loadImageFromGlide(context, R.drawable.image_loading, this, defaultLoading)
    }
}

/**
 * 加载文件类型的图片
 */
fun ImageView.loadImageByFile(filePath: String?) {
    if (!filePath.isNullOrEmpty()) {
        //加载图片
        ImageHelper.loadImageFromGlide(context, File(filePath), this)
    }
}

fun ImageView.loadRoundImageByFile(filePath: String?) {
    if (!filePath.isNullOrEmpty()) {
        //加载图片
        ImageHelper.loadRoundImageByFile(context, File(filePath), this)
    }
}

/**
 * 加载圆形的图片
 */
fun ImageView.loadRoundImage(url: String?) {
    ImageHelper.loadRoundImage(context, url ?: "", this)
}

/**
 * @param defaultLoading 默认占位图
 */
fun ImageView.loadRoundImage(url: String?, defaultLoading: Int) {
    ImageHelper.loadRoundImage(context, url ?: "", defaultLoading, this)
}

/**
 * @param ra 圆角角度
 */
fun ImageView.loadRoundImage(ra: Int, url: String?) {
    ImageHelper.loadRoundImage(context, url ?: "", this, ra)
}


/**
 * 从资源中加载圆形图片
 */
fun ImageView.loadRoundImage(resId: Int) {
    //加载图片
    ImageHelper.loadRoundImage(context, resId, this)
}