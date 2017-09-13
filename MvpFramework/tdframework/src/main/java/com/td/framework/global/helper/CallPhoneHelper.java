package com.td.framework.global.helper;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.td.framework.R;
import com.td.framework.biz.ApiSubscriber;
import com.td.framework.utils.T;

/**
 * Created by 江俊超 on 2017/4/1 0001.
 * <p>版本:1.0.0</p>
 * <b>说明<b><br/>
 * <li>拨打电话的帮助类</li>
 */
public class CallPhoneHelper {
    private Activity mContext;
    private AlertDialog mAlertDialog;

    public static CallPhoneHelper newInstance(Activity mContext) {
        return new CallPhoneHelper(mContext);
    }

    private CallPhoneHelper(Activity mContext) {
        this.mContext = mContext;
    }

    public void showCallPhoneDialog(final String phone) {
        if (TextUtils.isEmpty(phone)) {
            T.showToast(mContext, "暂无联系电话");
            return;
        }
        View mDialogView = LayoutInflater.from(mContext).inflate(R.layout.dialog_call_phone, null);

        TextView tvPhoneNumber = (TextView) mDialogView.findViewById(R.id.tv_phone_number);
        Button btnCall = (Button) mDialogView.findViewById(R.id.btn_call);
        Button btnStation = (Button) mDialogView.findViewById(R.id.btn_cancel);

        if (!TextUtils.isEmpty(phone)) {
            tvPhoneNumber.setText(phone);
        }

        //监听事件
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAlertDialog != null && mAlertDialog.isShowing()) {
                    mAlertDialog.dismiss();
                    requestCallPhone(phone);
                }

            }
        });
        btnStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAlertDialog != null && mAlertDialog.isShowing()) {
                    mAlertDialog.dismiss();
                }
            }
        });

        mAlertDialog = new AlertDialog.Builder(mContext, R.style.DialogStyle)
                .setView(mDialogView)
                .setCancelable(false)
                .show();
    }

    /**
     * 在这里拨打号码
     *
     * @param phone
     */
    private void requestCallPhone(final String phone) {
        //检查权限
        new RxPermissions(mContext)
                .request(Manifest.permission.CALL_PHONE)
                .subscribe(new ApiSubscriber<Boolean>(null) {
                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            Intent callIntent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phone));
                            mContext.startActivity(callIntent);
                        } else {
                            //显示提示
                            T.showToast(mContext, R.string.call_phone_permissions_is_refues);
                        }
                    }
                });

    }
}
