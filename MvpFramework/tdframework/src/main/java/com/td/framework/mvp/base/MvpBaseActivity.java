package com.td.framework.mvp.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.td.framework.base.TDBaseActivity;
import com.td.framework.biz.NetError;
import com.td.framework.moudle.loding.DialogHelper;
import com.td.framework.mvp.presenter.BasePresenter;


/**
 * Created by 江俊超 on 2017/1/6 0006.
 * <p>Github https://github.com/aohanyao</p>
 */

public abstract class MvpBaseActivity<P> extends TDBaseActivity
        implements DialogInterface.OnCancelListener, DialogHelper.OnDialogConfirmListener {
    private P p;
    DialogHelper mDialogHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        p = getP();
        mDialogHelper = new DialogHelper(mActivity, this);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (p != null) {
            if (checkIsPresenter())
                ((BasePresenter) p).subscribe();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (p != null) {
            if (checkIsPresenter())
                ((BasePresenter) p).unSubscribe();
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
        if (error.getType() == NetError.LOGIN_OUT || message.contains("登陆失效")) {
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
            if (!TextUtils.isEmpty(message)) {
                mDialogHelper.showMessageDialog(message);
            }
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
            showToast(msg);
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
        dismissDialog();
        //显示loading
        showLoadingDialog(mActivity.getResources().getString(resId), cancelable);
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
        if (getP() != null) {
            if (checkIsPresenter()) {
                ((BasePresenter) getP()).unSubscribe();
            }
        }
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }

    /**
     * 检查是不是presenter
     *
     * @return
     */
    private boolean checkIsPresenter() {

        if (!(p instanceof BasePresenter)) {
            throw new RuntimeException("your P request extend BasePresenter");
        }

        return true;
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
                                  @Nullable final DialogHelper.OnDialogConfirmListener onDialogConfirmListener
            , DialogHelper.OnDialogCancelableListener onDialogCancelableListener) {
        mDialogHelper.showConfirmDialog("", message, confirmText, cancelText, onDialogConfirmListener, onDialogCancelableListener);
    }

    public void showConfirmDialog(@Nullable String message,
                                  @Nullable String confirmText,
                                  @Nullable String cancelText,
                                  @Nullable final DialogHelper.OnDialogConfirmListener onDialogConfirmListener) {
        mDialogHelper.showConfirmDialog("", message, confirmText, cancelText, onDialogConfirmListener, null);
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
