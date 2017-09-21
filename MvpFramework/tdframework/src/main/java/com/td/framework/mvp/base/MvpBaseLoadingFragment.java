package com.td.framework.mvp.base;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.td.framework.base.view.TDBaseLoadingFragment;
import com.td.framework.biz.NetError;
import com.td.framework.global.router.RouterBasePath;
import com.td.framework.moudle.loding.DialogHelper;
import com.td.framework.mvp.presenter.BasePresenter;

/**
 * Created by 江俊超 on 2017/1/9 0009.
 * <p>Gihub https://github.com/aohanyao</p>
 * <li>有loading的</li>
 * <p>不建议直接使用</p>
 */
//@Deprecated
public abstract class MvpBaseLoadingFragment<P> extends TDBaseLoadingFragment
        implements DialogInterface.OnCancelListener, DialogHelper.OnDialogConfirmListener {
    private P p;
    protected DialogHelper mDialogHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        p = getP();
        mDialogHelper = new DialogHelper(getMActivity(), this);

    }

    /**
     * 显示确认信息：标题 内容 按钮文字等
     *
     * @param message                 信息
     * @param confirmText             确认按钮文字
     * @param onDialogConfirmListener 确认按钮点击
     */
    public void showConfirmDialog(@Nullable String message,
                                  @Nullable String confirmText,
                                  @Nullable String cancelText,
                                  @Nullable final DialogHelper.OnDialogConfirmListener onDialogConfirmListener) {
        mDialogHelper.showConfirmDialog("", message, confirmText, cancelText, onDialogConfirmListener, null);
    }


    public P getP() {
        if (p == null) {
            p = newP();
        }
        return p;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            ((BasePresenter) p).unSubscribe();
        } catch (Exception e) {
        }
    }

    /**
     * 初始化P层
     *
     * @return
     */
    protected abstract P newP();

    /**
     * 失败 信息
     *
     * @param error
     */
    protected void handlerFail(NetError error) {
        String message = error.getMessage();
        if (TextUtils.isEmpty(message)) {
            return;
        }
        dismissDialog();
        handlerComplete(null);
        if (error.getType() == NetError.ConnectExceptionError ||
                error.getType() == NetError.SocketTimeoutError ||
                error.getType() == NetError.UknownError ||
                error.getType() == NetError.HttpException) {
            showRetry();
        } else if (error.getType() == NetError.LOGIN_OUT || message.contains("登陆失效")) {
            showRetry();
            if (!TextUtils.isEmpty(message)) {
                mDialogHelper.showMessageDialog(message, new DialogHelper.OnDialogConfirmListener() {
                    @Override
                    public void onDialogConfirmListener(DialogInterface dialog) {
                        ARouter.getInstance()
                                .build(RouterBasePath.Login)
                                .navigation(getMActivity(), NetError.LOGIN_OUT);
                    }
                });
            }
        } else {
            showContent();
            if (!TextUtils.isEmpty(message)) {
                mDialogHelper.showMessageDialog(message);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NetError.LOGIN_OUT && resultCode == getMActivity().RESULT_OK) {
            onRetry();
        }
    }

    /**
     * 完成  弹出信息和关闭弹窗等
     *
     * @param msg
     */

    protected void handlerComplete(String msg) {
        dismissDialog();
        if (!TextUtils.isEmpty(msg)) {
            showTost(msg);
        }
    }

    /**
     * 关闭弹窗
     */
    protected void dismissDialog() {
        mDialogHelper.dismissDialog();
    }

    /**
     * 显示loading弹窗
     *
     * @param msg        消息
     * @param cancelable 是否可以取消
     */
    protected void showLoadingDialog(@NonNull String msg, boolean cancelable) {
        mDialogHelper.showLoadingDialog(msg, cancelable);
    }

    /**
     * 显示loading弹窗
     *
     * @param resId      消息
     * @param cancelable 是否可以取消
     */
    protected void showLoadingDialog(@StringRes int resId, boolean cancelable) {
        //显示loading
        showLoadingDialog(getMActivity().getResources().getString(resId), cancelable);
    }

    /**
     * 显示提示消息
     *
     * @param msg
     */
    protected void showMessageDialog(String msg) {
        mDialogHelper.showMessageDialog(msg);
    }

    /**
     * 显示提示消息
     *
     * @param msg
     */
    protected void showMessageDialog(String msg, @Nullable final DialogHelper.OnDialogConfirmListener onDialogConfirmListener) {
        mDialogHelper.showMessageDialog(msg, onDialogConfirmListener);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        onDialogCancel();
    }

    /**
     * 弹窗 取消
     */
    protected void onDialogCancel() {
        //弹窗消失 应该取消请求
        ((BasePresenter) p).unSubscribe();
    }

    public void onFail(NetError error) {
        handlerFail(error);
    }

    public void complete(String msg) {
        handlerComplete(msg);
    }

    /**
     * 显示弹窗
     *
     * @param msg
     */
    public void showLoading(@StringRes int msg) {
        mDialogHelper.dismissDialog();
        mDialogHelper.showLoadingDialog(getResources().getString(msg), true);
    }

    @Override
    public void onDialogConfirmListener(DialogInterface dialog) {
        onDialogCancel();
    }

}
