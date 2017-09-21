package com.td.framework.global.helper

import android.app.Activity
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import com.td.framework.R
import com.td.framework.expand.makeCall
import com.td.framework.utils.T

/**
 * Created by 江俊超 on 2017/4/1 0001.
 *
 * 版本:1.0.0
 * **说明**<br></br>
 *  * 拨打电话的帮助类
 **** */
class CallPhoneHelper private constructor(private val mContext: Activity) {
    private var mAlertDialog: AlertDialog? = null

    fun showCallPhoneDialog(phone: String?) {
        if (TextUtils.isEmpty(phone)) {
            T.showToast(mContext, "暂无联系电话")
            return
        }
        val mDialogView = LayoutInflater.from(mContext).inflate(R.layout.dialog_confirm, null)

        val tvPhoneNumber = mDialogView.findViewById(R.id.tv_dialog_message) as TextView
        val btnCall = mDialogView.findViewById(R.id.btn_confirm) as Button
        val btnStation = mDialogView.findViewById(R.id.btn_cancel) as Button

        if (!TextUtils.isEmpty(phone)) {
            tvPhoneNumber.text = phone
        }

        //监听事件
        btnCall.setOnClickListener {
            if (mAlertDialog != null && mAlertDialog!!.isShowing) {
                mAlertDialog!!.dismiss()
                //拨打电话
                mContext.makeCall(phone!!)
            }
        }
        btnStation.setOnClickListener {
            if (mAlertDialog != null && mAlertDialog!!.isShowing) {
                mAlertDialog!!.dismiss()
            }
        }

        mAlertDialog = AlertDialog.Builder(mContext, R.style.DialogStyle)
                .setView(mDialogView)
                .setCancelable(false)
                .show()
    }

    companion object {

        fun newInstance(mContext: Activity): CallPhoneHelper {
            return CallPhoneHelper(mContext)
        }
    }


}
