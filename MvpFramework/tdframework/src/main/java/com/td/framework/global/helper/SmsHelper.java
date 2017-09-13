package com.td.framework.global.helper;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.td.framework.R;
import com.td.framework.biz.ApiSubscriber;
import com.td.framework.utils.T;

/**
 * Created by 江俊超 on 2017/4/19 0019.
 * <p>版本:1.0.0</p>
 * <b>说明<b>发送短信的帮助类<br/>
 * <li></li>
 */
public class SmsHelper {
    private Activity mContext;

    public SmsHelper(Activity mContext) {
        this.mContext = mContext;
    }

    public static SmsHelper newInstance(Activity mContext) {
        return new SmsHelper(mContext);
    }

    /**
     * 发送短信
     *
     * @param phoneNumber
     * @param msgContent
     */
    public void sendSms(final String phoneNumber,final  String msgContent) {
        new RxPermissions(mContext)
                .request(Manifest.permission.SEND_SMS)
                .subscribe(new ApiSubscriber<Boolean>(null) {
                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNumber));
                            intent.putExtra("sms_body", msgContent);
                            mContext.startActivity(intent);
                        } else {
                            //显示提示
                            T.showToast(mContext, R.string.send_sms_permissions_is_refues);
                        }
                    }
                });

    }
}
