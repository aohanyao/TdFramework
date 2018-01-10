package com.td.framework.moudle.loding;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.td.framework.R;

/**
 * Created by jc on 2017/1/17 0017.
 * <p>Gihub https://github.com/aohanyao</p>
 * <p>弹窗帮助类</p>
 */
public class DialogHelper {
    private Activity mActivity;
    private LayoutInflater mInflater;
    private DialogInterface.OnCancelListener onCancelListener;
    private AlertDialog mDialog;
    private OnDialogConfirmListener onDialogConfirmListener;
    private OnDialogCancelableListener onDialogCancelableListener;
    private int mStyle;
    private boolean isUseAlert = false;

    public DialogHelper(Activity mActivity, DialogInterface.OnCancelListener onCancelListener) {
        this(mActivity, onCancelListener, R.style.DialogStyle);
    }

    public DialogHelper(Activity mActivity, DialogInterface.OnCancelListener onCancelListener, @StyleRes int style) {
        this.mActivity = mActivity;
        this.onCancelListener = onCancelListener;
        mInflater = LayoutInflater.from(mActivity);
        this.mStyle = style;
    }

    /**
     * 显示loading弹窗
     *
     * @param msg        消息
     * @param cancelable 是否可以取消
     */
    public void showLoadingDialog(String msg, boolean cancelable) {
        dismissDialog();
        View mDialogLoadingView = mInflater.inflate(R.layout.dialog_loading, null);
        TextView mTvDialogMessage = (TextView) mDialogLoadingView.findViewById(R.id.tv_loading_text);
        mTvDialogMessage.setText(msg);
        mDialog = new AlertDialog.Builder(mActivity,mStyle)
                .setView(mDialogLoadingView)
                .setCancelable(cancelable)
                .setOnCancelListener(onCancelListener)
                .create();
        try {
            mDialog.show();
        } catch (Exception e) {
        }
    }

    /**
     * 显示消息的弹窗
     *
     * @param message
     */
    public void showMessageDialog(String message) {
        dismissDialog();
        View mMessageView = mInflater.inflate(R.layout.dialog_message, null);
        //消息
        TextView tvDialogMessage = (TextView) mMessageView.findViewById(R.id.tv_dialog_message);
        if (tvDialogMessage != null) {
            tvDialogMessage.setText(message);
        }
        //确认按钮
        Button btnConfirm = (Button) mMessageView.findViewById(R.id.btn_confirm);
        if (btnConfirm != null) {
            btnConfirm.setText("确定");
            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissDialog();
                }
            });
        }
        mDialog = new AlertDialog.Builder(mActivity, mStyle)
                .setView(mMessageView)
                .setCancelable(false)
                .setOnCancelListener(onCancelListener)
                .create();

        try {
            mDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示消息的弹窗
     *
     * @param message
     */
    public void showMessageDialog(String message, @Nullable final OnDialogConfirmListener onDialogConfirmListener) {
        dismissDialog();
        View mMessageView = LayoutInflater.from(mActivity).inflate(R.layout.dialog_message, null);
        //消息
        TextView tvDialogMessage = (TextView) mMessageView.findViewById(R.id.tv_dialog_message);
        if (tvDialogMessage != null) {
            tvDialogMessage.setText(message);
        }
        //确认按钮
        Button btnConfirm = (Button) mMessageView.findViewById(R.id.btn_confirm);
        if (btnConfirm != null) {
            btnConfirm.setText("确定");
            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onDialogConfirmListener != null) {
                        dismissDialog();
                        onDialogConfirmListener.onDialogConfirmListener(mDialog);
                    } else {
                        dismissDialog();
                    }
                }
            });
        }
        mDialog = new AlertDialog.Builder(mActivity, mStyle)
                .setView(mMessageView)
                .setCancelable(false)
                .setOnCancelListener(onCancelListener)
                .create();

        try {
            mDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示确认信息：标题 内容 按钮文字等
     *
     * @param title                      标题
     * @param message                    信息
     * @param confirmText                确认按钮文字
     * @param cancelText                 取消按钮文字
     * @param onDialogConfirmListener    确认按钮点击
     * @param onDialogCancelableListener 取消按钮点击
     */
    public void showConfirmDialog(@Nullable String title,
                                  @Nullable String message,
                                  @Nullable String confirmText,
                                  @Nullable String cancelText,
                                  @Nullable final OnDialogConfirmListener onDialogConfirmListener,
                                  @Nullable final OnDialogCancelableListener onDialogCancelableListener) {
        dismissDialog();

        View mConfirmView = mInflater.inflate(R.layout.dialog_confirm, null);

        //消息
        TextView tvDialogMessage = (TextView) mConfirmView.findViewById(R.id.tv_dialog_message);
        if (tvDialogMessage != null) {
            tvDialogMessage.setText(message);
        }
        //确认按钮
        Button btnConfirm = (Button) mConfirmView.findViewById(R.id.btn_confirm);
        if (btnConfirm != null) {
            btnConfirm.setText(TextUtils.isEmpty(confirmText) ? "确定" : confirmText);
            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onDialogConfirmListener != null) {
                        onDialogConfirmListener.onDialogConfirmListener(mDialog);
                    } else {
                        dismissDialog();
                    }
                }
            });
        }
        //取消按钮
        Button btnCancel = (Button) mConfirmView.findViewById(R.id.btn_cancel);
        if (btnCancel != null) {
            btnCancel.setText(TextUtils.isEmpty(cancelText) ? "取消" : cancelText);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onDialogCancelableListener != null) {
                        onDialogCancelableListener.onDialogCancelableListener(mDialog);
                    } else {
                        dismissDialog();
                    }
                }
            });
        }
        mDialog = new AlertDialog.Builder(mActivity, mStyle)
                .setView(mConfirmView)
                .setCancelable(false)
                .setOnCancelListener(onCancelListener)
                .create();
        try {
            mDialog.show();
        } catch (Exception ignored) {
        }
    }

    /**
     * 只显示 信息 不监听
     *
     * @param message 信息
     */
    public void showConfirmDialog(String message) {
        showConfirmDialog(null, message, null, null, null, null);
    }

    public void showConfirmDialog(String title, String message) {
        showConfirmDialog(title, message, null, null, null, null);
    }

    public void showConfirmDialog(String message, OnDialogConfirmListener onDialogConfirmListener1) {
        showConfirmDialog(null, message, null, null, onDialogConfirmListener1, null);
    }

    /**
     * 关闭弹窗
     */
    public void dismissDialog() {
        try {
            mDialog.dismiss();
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    /**
     * 弹窗确定按钮回调
     */
    public interface OnDialogConfirmListener {
        void onDialogConfirmListener(DialogInterface dialog);
    }

    /**
     * 弹窗取消
     */
    public interface OnDialogCancelableListener {
        void onDialogCancelableListener(DialogInterface dialog);
    }

    public void setOnDialogConfirmListener(OnDialogConfirmListener onDialogConfirmListener) {
        this.onDialogConfirmListener = onDialogConfirmListener;
    }

    public void setOnDialogCancelableListener(OnDialogCancelableListener onDialogCancelableListener) {
        this.onDialogCancelableListener = onDialogCancelableListener;
    }
}
