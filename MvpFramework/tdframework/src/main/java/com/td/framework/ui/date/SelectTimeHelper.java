package com.td.framework.ui.date;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.td.framework.R;
import com.td.framework.ui.wheel.vertical.LoopView;
import com.td.framework.ui.wheel.vertical.OnItemSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by 江俊超 on 2017/4/28 0028.
 * <p>版本:1.0.0</p>
 * <b>说明<b>选择日期帮助类<br/>
 * <li>//TODO 没有区分未来和现在</li>
 */
public class SelectTimeHelper {
    private Activity mActivity;
    private AlertDialog mAlertDialog;
    private View mDialogView;
    private List<String> mYears;
    private List<String> mMonths;
    private List<String> mDays;
    private String mSelectMonth;
    private String mSelectYear;
    private OnSelectTimeListener mOnSelectTimeListener;
    private String mSelectDay;
    public static final int ALL = 0;
    public static final int YEAR = 1;
    public static final int YEAR_MONTH = 2;
    public static final int MONTH = 3;
    public static final int MONTH_DAY = 4;
    public static final int DAY = 5;
    private int mType;
    private boolean isFuture = false;

    public SelectTimeHelper(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public void showSelectTimeDialog(int showType, OnSelectTimeListener OnSelectTimeListener) {
        this.mType = showType;
        this.mOnSelectTimeListener = OnSelectTimeListener;
        mDialogView = LayoutInflater.from(mActivity).inflate(R.layout.dialog_select_date_layout, null);
        initBeginYear();
        initBeginMonth();
        initBeginDay();
        mAlertDialog = new AlertDialog.Builder(mActivity, R.style.DialogStyle)
                .setView(mDialogView)
                .setCancelable(true)
                .show();
        initEvent();
    }

    public void showSelectTimeDialog(int showType, OnSelectTimeListener OnSelectTimeListener, boolean isFuture) {
        this.isFuture = isFuture;
        showSelectTimeDialog(showType, OnSelectTimeListener);
    }

    private void initEvent() {
        //取消
        mDialogView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        //确定
        mDialogView.findViewById(R.id.tv_determine).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mOnSelectTimeListener != null) {
                    mOnSelectTimeListener.onSelectTimeListener(mSelectYear.replace("年", ""),
                            mSelectMonth.replace("月", ""), mSelectDay.replace("日", ""));
                }
            }
        });
    }

    private void dismiss() {
        try {
            mAlertDialog.dismiss();
        } catch (Exception e) {
        }
    }

    /**
     * 初始化月
     */
    private void initBeginMonth() {
        LoopView mloopViewBeginMoth = (LoopView) mDialogView.findViewById(R.id.loopView_month);
        //判断是否显示
        mloopViewBeginMoth.setVisibility(mType == ALL || mType == YEAR_MONTH || mType == MONTH || mType == MONTH_DAY ? View.VISIBLE : View.GONE);

        if (mMonths == null) {
            mMonths = new ArrayList<>();
            for (int i = 1; i <= 12; i++) {
                mMonths.add(String.valueOf(i) + "月");
            }
        }
        mloopViewBeginMoth.setItems(mMonths);
        mloopViewBeginMoth.setCurrentPosition(0);
        mSelectMonth = mMonths.get(0);
        mloopViewBeginMoth.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                mSelectMonth = mMonths.get(index);
                //计算月份
                initBeginDay();
            }
        });
    }

    /**
     * 初始化日
     */
    private void initBeginDay() {
        //日期
        //判断年份
        switch (mSelectMonth) {
            case "2月":
                int year = Integer.parseInt(mSelectYear.replace("年", ""));
                //二月份
                if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                    initDays(29);
                } else {
                    initDays(28);
                }
                break;

            case "1月":
            case "3月":
            case "5月":
            case "7月":
            case "8月":
            case "10月":
            case "12月":
                //31天
                initDays(31);
                break;
            default:
                //30天的
                initDays(30);
                break;

        }

        LoopView mloopViewDay = (LoopView) mDialogView.findViewById(R.id.loopView_day);
        mloopViewDay.setVisibility(mType == ALL || mType == MONTH_DAY || mType == DAY ? View.VISIBLE : View.GONE);
        mloopViewDay.setItems(mDays);
        mloopViewDay.setCurrentPosition(0);
        mSelectDay = mDays.get(0);
        mloopViewDay.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                mSelectDay = mDays.get(index);

            }
        });

    }

    private void initDays(int maxDay) {
        mDays = new ArrayList<>();
        for (int i = 1; i <= maxDay; i++) {
            mDays.add(String.valueOf(i));
        }
    }

    /**
     * 初始化年
     */
    private void initBeginYear() {
        LoopView mloopViewBeginYear = (LoopView) mDialogView.findViewById(R.id.loopView_year);
        mloopViewBeginYear.setVisibility(mType == ALL || mType == YEAR || mType == YEAR_MONTH ? View.VISIBLE : View.GONE);
        if (mYears == null) {
            mYears = new ArrayList<>();
            Calendar instance = Calendar.getInstance();
            int mNoewYear = instance.get(Calendar.YEAR);
            for (int i = 0; i < 10; i++) {
                mYears.add(String.valueOf((isFuture ? mNoewYear + i : mNoewYear - i)) + "年");
            }
        }
        mloopViewBeginYear.setItems(mYears);
        mloopViewBeginYear.setCurrentPosition(0);
        mSelectYear = mYears.get(0);
        mloopViewBeginYear.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                mSelectYear = mYears.get(index);
            }
        });
    }

    public interface OnSelectTimeListener {
        /**
         * 日期选中
         *
         * @param year  2017
         * @param month 5
         * @param day   2
         */
        void onSelectTimeListener(String year, String month, String day);
    }

}
