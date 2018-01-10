/*
 * Copyright (C) 2012 www.amsoft.cn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.td.framework.utils;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.td.framework.R;
import com.td.framework.global.app.App;


/**
 * 描述：Toast工具类.
 */

public class T {


    /** 上下文. */
    private static Context mContext = null;

    /** 显示Toast. */
    public static final int SHOW_TOAST = 0;

    /**
     * 主要Handler类，在线程中可用
     * what：0.提示文本信息
     */
    private static Handler baseHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_TOAST:
                    showToast(mContext,msg.getData().getString("TEXT"));
                    break;
                default:
                    break;
            }
        }
    };
    /**
     * 描述：Toast提示文本.
     * @param text  文本
     */
    public static void showToast(Context context, String text) {
        mContext = context;
        LayoutInflater inflate = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflate.inflate(R.layout.layout_toast, null);
        TextView tvMsg = (TextView) layout.findViewById((R.id.tv_msg));
        tvMsg.setText(text);
        if (!TextUtils.isEmpty(text)) {
            Toast toast = new Toast(mContext);
            toast.setGravity(Gravity.BOTTOM, 0, 150);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();
        }

    }



    /**
     * 描述：在线程中提示文本信息.
     * @param resId 要提示的字符串资源ID，消息what值为0,
     */
    public static void showToastInThread(Context context, int resId) {
        mContext = context;
        Message msg = baseHandler.obtainMessage(SHOW_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString("TEXT", context.getResources().getString(resId));
        msg.setData(bundle);
        baseHandler.sendMessage(msg);
    }

    /**
     * 描述：在线程中提示文本信息.
     */
    public static void showToastInThread( String text) {
        mContext = App.newInstance();
        Message msg = baseHandler.obtainMessage(SHOW_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString("TEXT", text);
        msg.setData(bundle);
        baseHandler.sendMessage(msg);
    }


    /**
     * 描述：Toast提示文本.
     *
     * @param text 文本
     */
    public static void showToast(String text) {
        showToast(App.newInstance(), text);
    }


    /**
     * 描述：Toast提示文本.
     *
     * @param resId 文本的资源ID
     */
    public static void showToast(Context context, int resId) {
        Context mContext = context.getApplicationContext();
        showToast(mContext, mContext.getResources().getString(resId));
    }
}
