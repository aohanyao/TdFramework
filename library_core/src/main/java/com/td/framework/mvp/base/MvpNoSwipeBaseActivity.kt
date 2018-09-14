package com.td.framework.mvp.base

import android.content.DialogInterface
import android.support.annotation.StringRes
import android.text.TextUtils
import com.alibaba.android.arouter.launcher.ARouter
import com.td.framework.base.view.NoSwipeBaseActivity
import com.td.framework.biz.NetError
import com.td.framework.global.app.Constant
import com.td.framework.global.router.RouterBasePath
import com.td.framework.moudle.loding.DialogHelper
import com.td.framework.mvp.presenter.BasePresenter
import com.td.framework.utils.T.showToast


/**
 * Created by jc on 2017/1/6 0006.
 *
 * Github
 */

abstract class MvpNoSwipeBaseActivity<P> : NoSwipeBaseActivity(), DialogInterface.OnCancelListener, DialogHelper.OnDialogConfirmListener {
    protected val p: P? by lazy { newP() }

    protected val mDialogHelper: DialogHelper by lazy {
        DialogHelper(mActivity, this)
    }


    public override fun onResume() {
        super.onResume()
        if (p != null) {
            if (checkIsPresenter())
                (p as BasePresenter<*>).subscribe()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (p != null) {
            if (checkIsPresenter())
                (p as BasePresenter<*>).unSubscribe()
        }
    }



    /**
     * 初始化P层

     * @return
     */
    protected abstract fun newP(): P?

    /**
     * 失败 信息

     * @param error
     */
    protected fun handlerFail(error: NetError) {
        val message = error.message
        if (TextUtils.isEmpty(message)) {
            return
        }
        if (error.type == NetError.LOGIN_OUT || message!!.contains("登陆失效")) {
            if (!TextUtils.isEmpty(message)) {
                mDialogHelper.showMessageDialog(message) {
                    ARouter.getInstance()
                            .build(RouterBasePath.Login)
                            .withInt(Constant.IDK,NetError.LOGIN_OUT)
                            .navigation(mActivity,NetError.LOGIN_OUT)
                }
            }
        } else {
            if (!TextUtils.isEmpty(message)) {

                mDialogHelper.showMessageDialog(message)
            }
        }
    }

    /**
     * 完成  弹出信息和关闭弹窗等

     * @param msg
     */
    protected fun handlerComplete(msg: String) {
        dismissDialog()
        if (!TextUtils.isEmpty(msg)) {
            showToast(msg)
        }
    }

    /**
     * 关闭弹窗
     */
    protected fun dismissDialog() {
        mDialogHelper.dismissDialog()
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
        dismissDialog()
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
        if (p != null) {
            if (checkIsPresenter()) {
                (p as BasePresenter<*>).unSubscribe()
            }
        }
        if (subscribe != null) {
            subscribe!!.dispose()
            subscribe = null
        }
    }

    /**
     * 检查是不是presenter

     * @return
     */
    private fun checkIsPresenter(): Boolean {

        if (p !is BasePresenter<*>) {
            throw RuntimeException("your P request extend BasePresenter")
        }

        return true
    }

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
                          onDialogConfirmListener: DialogHelper.OnDialogConfirmListener?, onDialogCancelableListener: DialogHelper.OnDialogCancelableListener) {
        mDialogHelper.showConfirmDialog("", message, confirmText, cancelText, onDialogConfirmListener, onDialogCancelableListener)
    }

    fun showConfirmDialog(message: String?,
                          confirmText: String?,
                          cancelText: String?,
                          onDialogConfirmListener: DialogHelper.OnDialogConfirmListener?) {
        mDialogHelper.showConfirmDialog("", message, confirmText, cancelText, onDialogConfirmListener, null)
    }



    fun onFail(error: NetError) {
        handlerFail(error)
    }

    fun complete(msg: String) {
        handlerComplete(msg)
    }

    /**
     * 显示弹窗

     * @param msg
     */
    fun showLoading(@StringRes msg: Int) {
        mDialogHelper.dismissDialog()
        mDialogHelper.showLoadingDialog(resources.getString(msg), true)
    }

    override fun onDialogConfirmListener(dialog: DialogInterface) {
        onDialogCancel()
    }
}
