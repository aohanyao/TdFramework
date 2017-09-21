package com.td.framework.expand

import android.animation.Animator
import android.animation.ObjectAnimator
import android.text.Editable
import android.view.View
import android.widget.TextView
import com.td.framework.base.listener.BaseAnimatorListener
import com.td.framework.base.BaseTextWatcher

/**
 * Created by 江俊超 on 2017/7/20 0020.
 * <p>版本:1.0.0</p>
 * <b>说明<b><br/>
 * <li>组件拓展</li>
 */
/**
 * 返回内容文本
 */
fun TextView.contentText(): String {
    return text.toString()
}

/**
 * 判断文本长度
 */
fun TextView.equalLength(length: Int): Boolean {
    return text.toString().length >= length
}

/**
 * 判断是不是空的
 */
fun TextView.isEmptyText(): Boolean {
    return contentText().isNullOrEmpty()
}

/**
 *判断相等
 */
fun TextView.equalsText(text: String?): Boolean {
    return contentText() == (text)
}

/**
 * 字符串更改后
 */
fun TextView.afterTextChanged(block: (str: String) -> Unit) {
    this.addTextChangedListener(object : BaseTextWatcher() {
        override fun afterTextChanged(s: Editable?) {
            //返回数据
            block.invoke(contentText())
        }
    })
}


fun View.alphaAnimator(startAlpha: Float, targetAlpha: Float, duration: Long) {
    //开始动画
    ObjectAnimator.ofFloat(this, "alpha", startAlpha, targetAlpha)
            .setDuration(duration)
            .apply {
                addListener(object : BaseAnimatorListener() {
                    override fun onAnimationStart(animation: Animator?) {
                        if (startAlpha <= 0) {
                            visibility = View.VISIBLE
                        }
                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        if (startAlpha > 0) {
                            visibility = View.GONE
                        }
                    }
                })
                start()
            }
}