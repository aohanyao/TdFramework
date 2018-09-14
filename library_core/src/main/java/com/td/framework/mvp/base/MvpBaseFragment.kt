package com.td.framework.mvp.base

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.annotation.StringRes
import android.text.TextUtils

import com.alibaba.android.arouter.launcher.ARouter
import com.td.framework.base.view.TDBaseLoadingFragment
import com.td.framework.biz.NetError
import com.td.framework.global.app.Constant
import com.td.framework.global.router.RouterBasePath
import com.td.framework.moudle.loding.DialogHelper
import com.td.framework.mvp.presenter.BasePresenter

/**
 * Created by jc on 2017/1/9 0009.
 *
 * Gihub
 *
 * MVPFramgnrt
 */
abstract class MvpBaseFragment<P> : TDBaseLoadingFragment(),
        DialogInterface.OnCancelListener, DialogHelper.OnDialogConfirmListener {
    protected var p: P? = null
    protected val mDialogHelper: DialogHelper by lazy {
        DialogHelper(mActivity, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        p = getPer()
    }

    override fun onResume() {
        super.onResume()
        try {
            (p as BasePresenter<*>).subscribe()
        } catch (ignored: Exception) {
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        try {
            (p as BasePresenter<*>).subscribe()
        } catch (ignored: Exception) {
        }

    }

    override fun onRetry() {

    }

    protected fun getPer(): P? {
        if (p == null) {
            p = newP()
        }
        return p
    }

    override fun onDestroy() {
        super.onDestroy()
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
    protected open fun handlerFail(error: NetError) {
        val message = error.message
        if (TextUtils.isEmpty(message)) {
            return
        }
        dismissDialog()
        handlerComplete(null)
        if (error.type == NetError.ConnectExceptionError ||
                error.type == NetError.SocketTimeoutError ||
                error.type == NetError.HttpException) {
            showRetry()
        } else if (error.type == NetError.LOGIN_OUT || message!!.contains("登陆失效")) {
            showRetry()

            if (!TextUtils.isEmpty(message)) {
                mDialogHelper.showMessageDialog(message) {
                    ARouter.getInstance()
                            .build(RouterBasePath.Login)
                            .withInt(Constant.IDK, NetError.LOGIN_OUT)
                            .navigation(mActivity, NetError.LOGIN_OUT)
                }
            }
        } else {
            showContent()
            if (!TextUtils.isEmpty(message)) {
                mDialogHelper.showMessageDialog(message)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == NetError.LOGIN_OUT && resultCode == NetError.LOGIN_OUT) {
            onRetry()
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    /**
     * 完成  弹出信息和关闭弹窗等

     * @param msg
     */
    protected fun handlerComplete(msg: String?) {
        dismissDialog()
        if (!TextUtils.isEmpty(msg)) {
            showToast(msg!!)
        }
    }

    /**
     * 关闭弹窗
     */
    protected fun dismissDialog() {
        mDialogHelper.dismissDialog()
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

    override fun onCancel(dialog: DialogInterface) {
        if (subscribe != null) {
            subscribe!!.dispose()
            subscribe = null
        }
    }

    override fun onDialogConfirmListener(dialog: DialogInterface) {

    }
}
