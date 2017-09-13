package com.td.framework.mvp.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.td.framework.base.TDBaseLoadingActivity;
import com.td.framework.biz.NetError;
import com.td.framework.moudle.loding.DialogHelper;
import com.td.framework.mvp.presenter.BasePresenter;


/**
 * Created by 江俊超 on 2017/1/6 0006.
 * <p>版本:1.0.1</p>
 * <b>说明<b><br/>
 * <li>MVP模式下的加载类型Activity</li>
 */

public abstract class MvpLoadingActivity<P extends BasePresenter> extends TDBaseLoadingActivity implements DialogInterface.OnCancelListener, DialogHelper.OnDialogConfirmListener {
    private P p;
    protected DialogHelper mDialogHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        p = getP();
        mDialogHelper = new DialogHelper(mActivity, this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            p.subscribe();
        } catch (Exception ignored) {
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            p.unSubscribe();
        } catch (Exception ignored) {
        }

    }


    public P getP() {
        if (p == null) {
            p = newP();
        }
        return p;
    }

    /**
     * 初始化P层
     *
     */
    protected abstract P newP();

    /**
     * 失败 信息
     *
     * @param error 错误信息
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
                error.getType() == NetError.HttpException) {
            showRetry();
        } else if (error.getType() == NetError.LOGIN_OUT || message.contains("登陆失效")) {
            showRetry();

            if (!TextUtils.isEmpty(message)) {
                mDialogHelper.showMessageDialog(message, new DialogHelper.OnDialogConfirmListener() {
                    @Override
                    public void onDialogConfirmListener(DialogInterface dialog) {
                        ARouter.getInstance()
                                .build("/User/Login")
                                .navigation();
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

    /**
     * 完成  弹出信息和关闭弹窗等
     *
     * @param msg 需要显示的信息
     */
    protected void handlerComplete(String msg) {
        dismissDialog();
        if (!TextUtils.isEmpty(msg)) {
            showToast(msg);
        }
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
        showLoadingDialog(mActivity.getResources().getString(resId), cancelable);
    }

    /**
     * 显示提示消息
     *
     * @param msg 需要提示的消息
     */
    protected void showMessageDialog(String msg) {
        mDialogHelper.showMessageDialog(msg);
    }

    /**
     * 显示提示消息
     *
     * @param msg 需要提示的消息
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
        try {
            getP().unSubscribe();
        } catch (Exception ignored) {
        }
        try {
            disposable.dispose();
        } catch (Exception ignored) {
        }

    }

    public void onFail(NetError error) {
        handlerFail(error);
    }

    public void complete(String msg) {
        handlerComplete(msg);
    }

    @Override
    public void onDialogConfirmListener(DialogInterface dialog) {
        onDialogCancel();
    }
}
