package com.td.framework.expand

import android.animation.Animator
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.widget.AppCompatEditText
import android.text.Editable
import android.text.InputType
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.chad.library.adapter.base.BaseViewHolder
import com.td.framework.base.listener.BaseAnimatorListener
import com.td.framework.base.listener.BaseTextWatcher
import com.td.framework.global.app.App
import com.td.framework.utils.DensityUtils
import com.td.framework.utils.KeyBoardUtils
import com.td.framework.utils.SpannableStringUtils

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

//fun TDBaseActivity.post(){
//    this.mDisposables
//}

/**
 * 绑定输入
 */
fun TextView.bindClear(clearView: View) {
    addTextChangedListener(object : BaseTextWatcher() {
        override fun afterTextChanged(s: Editable?) {
            clearView.visibility = if (equalLength(1)) View.VISIBLE
            else View.INVISIBLE
        }
    })
    clearView.setOnClickListener { reset() }
}

/**
 * 清空数据
 */
fun TextView.reset() {
    text = ""
}


/**
 * 追踪输入
 *@param block 回调当前的输入结果
 */
fun TextView.trackingInput(block: (nowLength: Int) -> Unit) {
    addTextChangedListener(object : BaseTextWatcher() {
        override fun afterTextChanged(s: Editable?) {
            block.invoke(contentText().length)
        }
    })
}

/**
 * 显示或者隐藏
 */
fun View.visibilityOrGone(isVisibility: Boolean) {
    visibility = if (isVisibility) View.VISIBLE
    else View.GONE
}

/**
 * 切换显示状态（多个文本框绑定一个按钮）
 * @param views
 *          first         目标内容长度
 *          second        目标文本
 */
fun View.switchLinkViews(vararg views: Pair<Int, TextView>) {
    //当前View
    val targetView = this
    //填充的数据
    val mEnableViews = HashSet<TextView>()
    views.forEach forEach@{ pair ->
        pair.second.addTextChangedListener(object : BaseTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                views.forEach {
                    //判断长度
                    if (pair.second.equalLength(pair.first)) {
                        mEnableViews.add(pair.second)
                    } else {
                        mEnableViews.remove(pair.second)
                    }
                }
                //相等
                targetView.isEnabled = mEnableViews.size == views.size
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

/**打开键盘*/
fun EditText.openKeyboard() {
    KeyBoardUtils.openKeybord(this, App.newInstance())
}

/**关闭键盘*/
fun EditText.closeKeyboard() {
    KeyBoardUtils.closeKeybord(this, App.newInstance())
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
fun BaseViewHolder.changeItemTopMargin(context: Context,
                                       rootLayoutId: Int,
                                       marginTop: Float): BaseViewHolder {
    return changeItemTopMargin(context, rootLayoutId, marginTop, 0)
}

/**
 * 设置选中
 */
fun BaseViewHolder.setSelected(viewId: Int, isSelect: Boolean = true): BaseViewHolder {
    getView<View>(viewId).isSelected = isSelect
    return this
}

/**
 * 调整item的顶部边距
 */
fun BaseViewHolder.changeItemTopMargin(context: Context,
                                       rootLayoutId: Int,
                                       marginTop: Float,
                                       vararg position: Int): BaseViewHolder {
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
    return this
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

/**
 * 获取颜色
 */
fun Activity.getResourceColor(color: Int): Int {
    return resources.getColor(color)
}

fun Fragment.getResourceColor(color: Int): Int {
    return resources.getColor(color)
}

fun AppCompatEditText.switchEnterType(block: (isPassword: Boolean) -> Unit) {
    val type = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
    if (inputType === type) {
        inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        setSelection(text.length)     //把光标设置到当前文本末尾
        block.invoke(true)
    } else {
        inputType = type
        setSelection(text.length)
        block.invoke(false)

    }
}

/**
 * 设置特别的文字价格
 */
fun TextView.setUnusualPrices(strPrice: String?, vararg other: String) {

    text = try {
        val mStringPrice = strPrice?.split(".")
        val mPriceText = SpannableStringUtils.getBuilder("￥")
                .append(mStringPrice?.get(0) ?: "0")
//                .setBold()
                .setProportion(1.5f)
                .append(".${mStringPrice?.get(1)}")

        other.forEach {
            mPriceText.append(it)
                    .setForegroundColor(0xff666666.toInt())
        }

        // 设置价格
        mPriceText.create()
    } catch (e: Exception) {
        strPrice
    }

}