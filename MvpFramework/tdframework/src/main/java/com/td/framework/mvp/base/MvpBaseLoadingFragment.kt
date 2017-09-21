package com.td.framework.mvp.base

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.support.annotation.StringRes

import com.td.framework.biz.NetError
import com.td.framework.moudle.loding.DialogHelper
import com.td.framework.mvp.presenter.BasePresenter

/**
 * Created by 江俊超 on 2017/1/9 0009.
 *
 * Gihub https://github.com/aohanyao
 *  * 有loading的
 *
 */
abstract class MvpBaseLoadingFragment<P> : MvpBaseFragment<P>(){

    /**
     * 显示确认信息：标题 内容 按钮文字等

     * @param message                 信息
     * *
     * @param confirmText             确认按钮文字
     * *
     * @param onDialogConfirmListener 确认按钮点击
     */
    fun showConfirmDialog(message: String?,
                          confirmText: String?,
                          cancelText: String?,
                          onDialogConfirmListener: DialogHelper.OnDialogConfirmListener?) {
        mDialogHelper.showConfirmDialog("", message, confirmText, cancelText, onDialogConfirmListener, null)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == NetError.LOGIN_OUT && resultCode == Activity.RESULT_OK) {
            onRetry()
        }
    }

    /**
     * 显示loading弹窗

     * @param msg        消息
     * *
     * @param cancelable 是否可以取消
     */
    protected fun showLoadingDialog(msg: String, cancelable: Boolean) {
        mDialogHelper.showLoadingDialog(msg, cancelable)
    }

    /**
     * 显示loading弹窗

     * @param resId      消息
     * *
     * @param cancelable 是否可以取消
     */
    protected fun showLoadingDialog(@StringRes resId: Int, cancelable: Boolean) {
        //显示loading
        showLoadingDialog(mActivity.resources.getString(resId), cancelable)
    }

    /**
     * 显示提示消息

     * @param msg
     */
    protected fun showMessageDialog(msg: String) {
        mDialogHelper.showMessageDialog(msg)
    }

    /**
     * 显示提示消息

     * @param msg
     */
    protected fun showMessageDialog(msg: String, onDialogConfirmListener: DialogHelper.OnDialogConfirmListener?) {
        mDialogHelper.showMessageDialog(msg, onDialogConfirmListener)
    }

    override fun onCancel(dialog: DialogInterface) {
        onDialogCancel()
    }

    /**
     * 弹窗 取消
     */
    protected fun onDialogCancel() {
        //弹窗消失 应该取消请求
        (p as? BasePresenter<*>?)?.unSubscribe()
    }


    override fun onDialogConfirmListener(dialog: DialogInterface) {
        onDialogCancel()
    }

}
