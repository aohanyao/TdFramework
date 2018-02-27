package com.td.framework.expand

import android.widget.ImageView
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
    if (!url.isNullOrEmpty()) {
        //加载图片
        ImageHelper.loadImageFromGlide(context, url, this)
    }
}
fun ImageView.loadImageByFile(filePath: String?) {
    if (!filePath.isNullOrEmpty()) {
        //加载图片
        ImageHelper.loadImageFromGlide(context, File(filePath), this)
    }
}
fun ImageView.loadRoundImage(url: String?) {
    if (!url.isNullOrEmpty()) {
        //加载图片
        ImageHelper.loadShopRoundImage(context, url, this)
    }
}