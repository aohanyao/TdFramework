package com.td.framework.moudle.loding;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.td.framework.R;

/**
 * Created by 江俊超 on 2017/1/17 0017.
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
        mDialog = new AlertDialog.Builder(mActivity)
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
        if (isUseAlert) {
            //另外一种风格的提示窗  后期优化的时候可以启用
//            Alerter.create(mActivity)
//                    .setTitle("提示")
//                    .setText(message)
//                    .setBackgroundColor(R.color.color_alert)
//                    .show();
        } else {

            mDialog = new AlertDialog.Builder(mActivity)
                    // .setTitle("提示")
                    .setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            try {
                mDialog.show();
            } catch (Exception e) {
            }
        }
    }

    /**
     * 显示消息的弹窗
     *
     * @param message
     */
    public void showMessageDialog(String message, @Nullable final OnDialogConfirmListener onDialogConfirmListener) {
        dismissDialog();

        mDialog = new AlertDialog.Builder(mActivity)
                // .setTitle("提示")
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (onDialogConfirmListener != null) {
                            onDialogConfirmListener.onDialogConfirmListener(dialog);
                        } else {
                            dialog.dismiss();
                        }
                    }
                })
                .create();
        try {
            mDialog.show();
        } catch (Exception e) {
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
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        dismissDialog();

        //  builder.setTitle(TextUtils.isEmpty(title) ? "提示" : title);
        builder.setMessage(message);
        builder.setPositiveButton(TextUtils.isEmpty(confirmText) ? "确定" : confirmText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (onDialogConfirmListener != null) {
                    onDialogConfirmListener.onDialogConfirmListener(dialog);
                } else {
                    dialog.dismiss();
                }
            }
        });
        builder.setNegativeButton(TextUtils.isEmpty(cancelText) ? "取消" : cancelText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (onDialogCancelableListener != null) {
                    onDialogCancelableListener.onDialogCancelableListener(dialog);
                } else {
                    dialog.dismiss();
                }
            }
        });
        mDialog = builder.create();
        mDialog.setCancelable(false);
        try {
            mDialog.show();
        } catch (Exception e) {
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
    public  interface OnDialogCancelableListener {
        void onDialogCancelableListener(DialogInterface dialog);
    }

    public void setOnDialogConfirmListener(OnDialogConfirmListener onDialogConfirmListener) {
        this.onDialogConfirmListener = onDialogConfirmListener;
    }

    public void setOnDialogCancelableListener(OnDialogCancelableListener onDialogCancelableListener) {
        this.onDialogCancelableListener = onDialogCancelableListener;
    }
}
