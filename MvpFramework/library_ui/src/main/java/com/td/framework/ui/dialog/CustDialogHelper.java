package com.td.framework.ui.dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yida.cloud.ui.R;


/**
 * Created by jc on 2017/3/1 0001.
 * <p>版本:1.0.0</p>
 * <b>说明<b>自定义的dialog帮助类<br/>
 * <li></li>
 */
public class CustDialogHelper {
    private Activity mContext;
    private AlertDialog mAlertDialog;

    public static CustDialogHelper newInstance(Activity mContext) {
        return new CustDialogHelper(mContext);
    }

    private CustDialogHelper(Activity mContext) {
        this.mContext = mContext;
    }

    public void showDialog(String mMsg) {
        showDialog(null, mMsg, null, null);
    }

    public void showDialog(String mTitle, String mMsg, String mConfirmText) {
        showDialog(mTitle, mMsg, mConfirmText, null);
    }

    public void showDialog(String mTitle, String mMsg, String mConfirmText, final OnDialogConfirmListener onDialogConfirmListener) {
        View mDialogView = LayoutInflater.from(mContext).inflate(R.layout.dialog_layout, null);

        TextView tvDialogTitle = (TextView) mDialogView.findViewById(R.id.tv_dialog_title);
        TextView tvDialogContent = (TextView) mDialogView.findViewById(R.id.tv_dialog_content);
        Button btnConfirm = (Button) mDialogView.findViewById(R.id.btn_confirm);
        ImageView ivCancel = (ImageView) mDialogView.findViewById(R.id.iv_cancel);
        if (!TextUtils.isEmpty(mTitle)) {
            tvDialogTitle.setText(mTitle);
        }
        if (!TextUtils.isEmpty(mMsg)) {
            tvDialogContent.setText(mMsg);
        }
        if (!TextUtils.isEmpty(mConfirmText)) {
            btnConfirm.setText(mConfirmText);
        }
        //监听事件
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAlertDialog != null && mAlertDialog.isShowing()) {
                    mAlertDialog.dismiss();
                }
                //回调
                if (onDialogConfirmListener != null) {
                    onDialogConfirmListener.onDialogConfirmListener(mAlertDialog);
                }
            }
        });
        //取消
        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAlertDialog != null && mAlertDialog.isShowing()) {
                    mAlertDialog.dismiss();
                    if (onDialogConfirmListener != null) {
                        onDialogConfirmListener.onDialogCancelistener(mAlertDialog);
                    }
                }
            }
        });
        mAlertDialog = new AlertDialog.Builder(mContext, R.style.DialogStyle)
                .setView(mDialogView)
                .setCancelable(false)
                .show();
    }

    /**
     * 弹窗确定按钮回调
     */
    public interface OnDialogConfirmListener {
        void onDialogConfirmListener(DialogInterface dialog);
        void onDialogCancelistener(DialogInterface dialog);
    }
}
