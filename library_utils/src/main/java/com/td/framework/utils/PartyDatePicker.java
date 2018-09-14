package com.td.framework.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by zhangxiugao on 2018/5/8
 */

public class PartyDatePicker {

    private TimePickerView pvTime;

    public void showTimePicker(Context mContext, final ViewGroup view, final TextView tvStartTime) {
        final Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        //startDate.set(2013,1,1);
        Calendar endDate = Calendar.getInstance();
        //endDate.set(2020,1,1);

        //正确设置方式 原因：注意事项有说明
        startDate.set(1950, 0, 1);
//        startDate.set(startDate.get(Calendar.YEAR),startDate.get(Calendar.MONTH),startDate.get(Calendar.DATE),
//                startDate.get(Calendar.HOUR),startDate.get(Calendar.MINUTE)+1);
        endDate.set(2050, 11, 31);

        if (pvTime == null) {
            pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {//选中事件回调
                    SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
                    tvStartTime.setText(DATE_TIME_FORMAT.format(date));
                }
            }).setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                    .setCancelText("取消")//取消按钮文字
                    .setSubmitText("确定")//确认按钮文字
                    .setContentTextSize(18)//滚轮文字大小
                    .setTitleSize(20)//标题文字大小
//               .setTitleText("时间")//标题文字
                    .setDecorView(view)
                    .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                    .isCyclic(false)//是否循环滚动
//               .setTitleColor(Color.BLUE)//标题文字颜色
                    .setSubmitColor(0xFFF44336)//确定按钮文字颜色
                    .setCancelColor(0xFFF44336)//取消按钮文字颜色
//               .setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
//               .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
                    .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                    .setRangDate(startDate, endDate)//起始终止年月日设定
                    .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                    .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                    .isDialog(false)//是否显示为对话框样式
                    .build();
        }
        pvTime.show();
    }

    public void showHourMinPicker(Context mContext, final ViewGroup view, final TextView tvStartTime) {
        final Calendar selectedDate = Calendar.getInstance();

        if (pvTime == null) {
            pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {//选中事件回调
                    SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("HH:mm", Locale.CHINA);
                    tvStartTime.setText(DATE_TIME_FORMAT.format(date));
                }
            }).setType(new boolean[]{false, false, false, true, true, false})// 默认全部显示
                    .setCancelText("取消")//取消按钮文字
                    .setSubmitText("确定")//确认按钮文字
                    .setContentTextSize(18)//滚轮文字大小
                    .setTitleSize(20)//标题文字大小
//               .setTitleText("时间")//标题文字
                    .setDecorView(view)
                    .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                    .isCyclic(true)//是否循环滚动
//               .setTitleColor(Color.BLUE)//标题文字颜色
                    .setSubmitColor(0xFFF44336)//确定按钮文字颜色
                    .setCancelColor(0xFFF44336)//取消按钮文字颜色
//               .setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
//               .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
                    .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                    .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                    .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                    .isDialog(false)//是否显示为对话框样式
                    .build();
        }
        pvTime.show();
    }

    public void LeaseSelectProductAttrPopup_showPicker(Context mContext, final ViewGroup view, final TextView textView, final int typeTime, final DateCallback dateCallback) {

        final Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        //startDate.set(2013,1,1);
        Calendar endDate = Calendar.getInstance();
        //endDate.set(2020,1,1);

        //正确设置方式 原因：注意事项有说明
        startDate.set(1950, 0, 1);
//        startDate.set(startDate.get(Calendar.YEAR),startDate.get(Calendar.MONTH),startDate.get(Calendar.DATE),
//                startDate.get(Calendar.HOUR),startDate.get(Calendar.MINUTE)+1);
        endDate.set(2050, 11, 31);

        if (pvTime == null) {
            pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {//选中事件回调
                    dateCallback.onData(date, textView, typeTime);
                }
            }).setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                    .setCancelText("取消")//取消按钮文字
                    .setSubmitText("确定")//确认按钮文字
                    .setContentTextSize(18)//滚轮文字大小
                    .setTitleSize(20)//标题文字大小
//               .setTitleText("时间")//标题文字
                    .setDecorView(view)
                    .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                    .isCyclic(false)//是否循环滚动
//               .setTitleColor(Color.BLUE)//标题文字颜色
                    .setSubmitColor(0xFFF44336)//确定按钮文字颜色
                    .setCancelColor(0xFFF44336)//取消按钮文字颜色
//               .setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
//               .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
                    .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                    .setRangDate(startDate, endDate)//起始终止年月日设定
                    .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                    .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                    .isDialog(false)//是否显示为对话框样式
                    .build();
        }
        pvTime.show();
    }

    public interface DateCallback {
        void onData(Date date, TextView textView, int typeTime);
    }
}
