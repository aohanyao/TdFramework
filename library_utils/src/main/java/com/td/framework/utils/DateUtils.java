/*
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.td.framework.utils;

import android.annotation.SuppressLint;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static String FORMAT_1 = "yyyy-MM-dd";
    public static String FORMAT_2 = "MM月dd日 HH时mm分";
    public static String FORMAT_3 = "yyyy-MM-dd HH:mm:ss";
    public static String FORMAT_4 = "yyyy年MM月dd日HH:mm";
    public static String FORMAT_5 = "yyyy-MM-dd HH:mm";
    public static String FORMAT_6 = "yyyy年MM月dd日 HH:mm";
    public static String FORMAT_7 = "MM月dd日 HH:mm";
    public static String FORMAT_8 = "yyyy年MM月dd日";
    public static String FORMAT_9 = "MM/dd  HH:mm";
    public static String FORMAT_10 = "yyyy/MM/dd HH:mm:ss";
    public static String FORMAT_11 = "yyyy/MM/dd HH:mm";
    public static String FORMAT_12 = "MM-dd";
    public static String FORMAT_13 = "HH:mm";


    /**
     * @param date 日期
     * @param format 格式
     */
    public static String getTimeString(Date date, String format) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.CHINA);
        return simpleDateFormat.format(date);
    }


    /**
     * 获取日期格式
     */
    public static String getTimeString(Long date, String format) {
        if (date == null) {
            return "暂无相关日期";
        }
        return getTimeString(new Date(date), format);
    }


    /**
     * yyyy-MM-dd
     */
    public static String getTimeyyyyMMdd(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
        return format.format(date);
    }


    /**
     * dd
     */
    public static String getTimeDay(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("d", Locale.CHINA);
        return format.format(date);
    }


    public static String getMonth(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("MM", Locale.CHINA);
        return format.format(date);
    }


    /**
     * yyyy
     */
    public static String getTimeyyyy(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy", Locale.CHINA);
        return format.format(date);
    }


    /**
     * 获取两个时间的时间查 如1天2小时30分钟
     */
    public static String getDatePoor(Long endDate) {
        return getDatePoor(new Date(endDate));
    }


    /**
     * 时间戳相差的天数
     */
    public static int differentDays(long time1, long time2) {
        try {
            String date1 = formatData(time1);
            String date2 = formatData(time2);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d1 = df.parse(date1);
            Date d2 = df.parse(date2);
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(d1);
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(d2);
            int day1 = cal1.get(Calendar.DAY_OF_YEAR);
            int day2 = cal2.get(Calendar.DAY_OF_YEAR);
            int year1 = cal1.get(Calendar.YEAR);
            int year2 = cal2.get(Calendar.YEAR);
            //同一年
            if (year1 != year2) {
                int timeDistance = 0;
                for (int i = year1; i < year2; i++) {
                    if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {
                        //闰年
                        timeDistance += 366;
                    } else {
                        //不是闰年
                        timeDistance += 365;
                    }
                }
                return timeDistance + (day2 - day1);
            } else {
                //不同年
                //System.out.println("判断day2 - day1 : " + (day2 - day1));
                return day2 - day1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;

    }


    private static String formatData(long timeStamp) {
        if (timeStamp == 0) {
            return "";
        }
        String result = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        result = format.format(new Date(timeStamp));
        return result;
    }


    /**
     * 获取两个时间的时间查 如1天2小时30分钟
     */
    public static String getDatePoor(Date endDate) {
        Date nowDate = new Date();
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        if (day == 0) {
            return hour + "小时" + min + "分钟";
        }
        return day + "天" + hour + "小时" + min + "分钟";
    }


    private final static long minute = 60 * 1000;// 1分钟
    private final static long hour = 60 * minute;// 1小时
    private final static long day = 24 * hour;// 1天
    private final static long month = 31 * day;// 月
    private final static long year = 12 * month;// 年


    /**
     * 返回文字描述的日期
     */
    public static String getTimeFormatText(Long date) {
        return RelativeDateFormat.format(new Date(date));
    }


    /**
     * 计算两个日期之间相差的月数
     */
    public static int getMonth(Date dateStart, Date dateEnd) {
        int iMonth = 0;
        int flag = 0;
        try {
            Calendar mStartCal1 = Calendar.getInstance();
            mStartCal1.setTime(dateStart);

            Calendar mEndCal2 = Calendar.getInstance();
            mEndCal2.setTime(dateEnd);

            //时间相等
            if (mEndCal2.equals(mStartCal1)) return 0;
            //开始时间大于结束时间
            if (mStartCal1.after(mEndCal2)) {
                Calendar temp = mStartCal1;
                mStartCal1 = mEndCal2;
                mEndCal2 = temp;
            }
            //结束时间的月份小于 开始时间  为一个月
            if (mEndCal2.get(Calendar.DAY_OF_MONTH) < mStartCal1.get(Calendar.DAY_OF_MONTH)) {
                flag = 1;
            }

            //相差一年以上
            if (mEndCal2.get(Calendar.YEAR) > mStartCal1.get(Calendar.YEAR)) {
                iMonth = ((mEndCal2.get(Calendar.YEAR) - mStartCal1.get(Calendar.YEAR)) * 12 +
                    mEndCal2.get(Calendar.MONTH) - flag)
                    - mStartCal1.get(Calendar.MONTH);
            } else {
                iMonth = mEndCal2.get(Calendar.MONTH) - mStartCal1.get(Calendar.MONTH) - flag;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return iMonth;
    }

}
