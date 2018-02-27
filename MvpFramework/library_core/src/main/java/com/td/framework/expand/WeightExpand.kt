package com.td.framework.expand

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.text.Editable
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.chad.library.adapter.base.BaseViewHolder
import com.td.framework.base.BaseTextWatcher
import com.td.framework.base.listener.BaseAnimatorListener
import com.td.framework.utils.DensityUtils

/**
 * Created by jc on 2017/7/20 0020.
 * <p>版本:1.0.0</p>
 * <b>说明<b><br/>
 * <li>组件拓展</li>
 */

/**
 * 根据状态(一个文本框绑定多个按钮)
 */
fun TextView.switchLinkViews(length: Int? = 0, vararg views: View) {
    addTextChangedListener(object : BaseTextWatcher() {
        override fun afterTextChanged(s: Editable?) {
            views.forEach {
                it.isEnabled = equalLength(length!!)
            }
        }
    })
}

/**
 * 切换显示状态（多个文本框绑定一个按钮）
 * @param views
 *          first         目标内容长度
 *          second        目标文本
 */
fun View.switchLinkViews(vararg views: Pair<Int, TextView>) {
    views.forEach { pair ->
        pair.second.addTextChangedListener(object : BaseTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                var isEqualLength = false
                views.forEach enable@ {
                    //判断长度
                    if (!it.second.equalLength(it.first)) {
                        isEqualLength = false
                        return@enable
                    }
                    isEqualLength = true
                }

                isEnabled = isEqualLength
            }
        })
    }


}


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


/**
 * 调整第一个item的顶部边距
 */
fun BaseViewHolder.changeFirstItemTopMargin(context: Context,
                                            rootLayoutId: Int,
                                            marginTop: Float) {
    changeFirstItemTopMargin(context, rootLayoutId, marginTop, 0)
}

/**
 * 调整item的顶部边距
 */
fun BaseViewHolder.changeFirstItemTopMargin(context: Context,
                                            rootLayoutId: Int,
                                            marginTop: Float,
                                            vararg position: Int) {
    val rootView = getView<View>(rootLayoutId)
    val layoutParams = rootView.layoutParams as? ViewGroup.MarginLayoutParams?
    layoutParams?.apply {

        topMargin = if (layoutPosition in position) {
            DensityUtils.dp2px(context, marginTop)
        } else {
            0
        }

        rootView.layoutParams = layoutParams
    }
}


/**
 * 转换参数
 */
fun View.px2sp(px: Float): Float {
    return DensityUtils.px2sp(context, px)
}

/**
 * 设置像素单位的字体大小
 */
fun TextView.setPxTextSize(px: Float) {
    textSize = px2sp(px)
}