package com.td.framework.mvp.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.td.framework.base.TDBaseLoadingFragment;
import com.td.framework.biz.NetError;
import com.td.framework.moudle.loding.DialogHelper;
import com.td.framework.mvp.presenter.BasePresenter;

/**
 * Created by 江俊超 on 2017/1/9 0009.
 * <p>Gihub https://github.com/aohanyao</p>
 * <p>MVPFramgnrt</p>
 */
public abstract class MvpBaseFragment<P extends BasePresenter> extends TDBaseLoadingFragment implements DialogInterface.OnCancelListener, DialogHelper.OnDialogConfirmListener {
    private P p;
    protected DialogHelper mDialogHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        p = getP();
        mDialogHelper = new DialogHelper(mActivity, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            p.subscribe();
        } catch (Exception ignored) {
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            p.unSubscribe();
        } catch (Exception ignored) {
        }
    }

    @Override
    protected void onRetry() {

    }

    protected P getP() {
        if (p == null) {
            p = newP();
        }
        return p;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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

    public void onFail(NetError error) {
        handlerFail(error);
    }

    public void complete(String msg) {
        handlerComplete(msg);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }

    @Override
    public void onDialogConfirmListener(DialogInterface dialog) {

    }
}
