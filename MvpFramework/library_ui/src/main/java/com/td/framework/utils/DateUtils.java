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


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtils {
    public static final String[] WEEKS = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

    /**
     * 盘算两个日期之间的秒数
     *
     * @param startDate
     * @return 返回秒数
     */
    public static int countSecondBetweenTwoDates(Date startDate, Date endTime) {
        if (startDate == null || endTime == null) {
            return 0;
        }
        long a = startDate.getTime();
        long b = endTime.getTime();
        int c = (int) ((b - a) / 1000);
        return c;
    }

    public static int countSecondBetweenTwoDay(long startDate, long endTime) {
        int seconds = countSecondBetweenTwoDates(new Date(startDate), new Date(endTime));

        return Math.abs(seconds / 60 / 60 / 24);
    }

    public static int countSecondBetweenTwoDay(Date startDate, Date endTime) {
        int seconds = countSecondBetweenTwoDates(startDate, endTime);

        return Math.abs(seconds / 60 / 60 / 24);
    }
    /**
     * 获取日期是星期几
     *
     * @Exception 发生异常<br>
     */
    public static String dayForWeek(String pTime, String form) {
        SimpleDateFormat format = new SimpleDateFormat(form);
        Calendar c = Calendar.getInstance();
        int dayForWeek = 0;
        try {
            c.setTime(format.parse(pTime));
            if (c.get(Calendar.DAY_OF_WEEK) == 1) {
                dayForWeek = 7;
            } else {
                dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return WEEKS[dayForWeek];
    }

    public static int dayForWeekIndex(String pTime, String form) {
        SimpleDateFormat format = new SimpleDateFormat(form);
        Calendar c = Calendar.getInstance();
        int dayForWeek = 0;
        try {
            c.setTime(format.parse(pTime));
            if (c.get(Calendar.DAY_OF_WEEK) == 1) {
                dayForWeek = 7;
            } else {
                dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dayForWeek;
    }

    /***
     * 获取日期是星期几
     * @return
     */
    public static String dayForWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index = 0;
        }

        return WEEKS[week_index];
    }
    /**
     * 获取过去或者未来 任意天内的日期数组
     * @param intervals      intervals天内
     * @return 日期数组
     */
//    public static ArrayList<String> test(int intervals ) {
//        ArrayList<String> pastDaysList = new ArrayList<>();
//        ArrayList<String> fetureDaysList = new ArrayList<>();
//        for (int i = 0; i <intervals; i++) {
//            pastDaysList.add(getPastDate(i));
//            fetureDaysList.add(getFetureDate(i));
//        }
//        return pastDaysList;
//    }

    /**
     * 获取未来天数
     *
     * @param intervals 多少天
     * @return
     */
    public static ArrayList<Date> getFetureDays(int intervals) {
        ArrayList<Date> fetureDaysList = new ArrayList<>();
        for (int i = 0; i < intervals; i++) {
            fetureDaysList.add(getFetureDate(i));
        }
        return fetureDaysList;
    }

    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static Date getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        String result = format.format(today);
//        Log.e(null, result);
        return today;
    }

    /**
     * 获取未来 第 past 天的日期
     *
     * @param past
     * @return
     */
    private static Date getFetureDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        Date today = calendar.getTime();
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        String result = format.format(today);
//        Log.e(null, result);
        return today;
    }

    /**
     * 获得当前时间的未来时间
     *
     * @param past
     * @param startDate
     * @return
     */
    public static Date getFeatureDate(int past, Date startDate) {
        if (startDate == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        Date today = calendar.getTime();
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        String result = format.format(today);
//        Log.e(null, result);
        return today;
    }

    /**
     * yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String getTimeyyyy_MM_dd(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);

    }

    public static String getTimeyyyy_MM_dd(long date) {
        return getTimeyyyy_MM_dd(new Date(date));
    }

    /**
     * MM月dd日 HH时mm分
     *
     * @param date
     * @return
     */
    public static String getTime_MM_dd_HH_mm(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日 HH时mm分");
        return format.format(date);

    }

    /**
     * yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String getTimeyyyy_MM_dd_HH_mm_ss(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    public static String getTimeChines(String date) {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formt2 = new SimpleDateFormat("yyyy年MM月dd 日 HH:mm:ss");
        try {
            return formt2.format(format1.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String getTimeyyyyMMdd(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(date);
    }

    /**
     * dd
     *
     * @param date
     * @return
     */
    public static String getTimeDay(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("d");
        return format.format(date);
    }

    public static String getTimeMonthDay(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日");
        return format.format(date);
    }

    public static String getHour(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("HH");
        return format.format(date);
    }

    public static String getMonth(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("MM");
        return format.format(date);
    }

    public static String getMinute(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("mm");
        return format.format(date);
    }

    /**
     * yyyy
     *
     * @param date
     * @return
     */
    public static String getTimeyyyy(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        return format.format(date);
    }

    /**
     * hh:mm:ss
     *
     * @param date
     * @return
     */
    public static String getTimeHHmm(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(date);
    }

    /**
     * 将字符串时间转换为毫秒数
     *
     * @param strTime
     * @return
     */
    public static long getStringDate2Long(String strTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(strTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Date getString2Date(String strTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    /**
     * 将字符时间进行转换
     *
     * @param strTime
     * @return
     */
    public static String getStringDate2StringDate(String strTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return new SimpleDateFormat("yyyy-MM-dd").format(format.parse(strTime));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strTime;
    }

    public static String getStringDateymde(Date date) {
        try {
            return new SimpleDateFormat("yyyy 年 MM 月 dd 日  E").format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取两个时间段之间的所有天数
     *
     * @param begin
     * @param end
     * @return
     */
    public static List<String> getBetweenDate(Date begin, Date end) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        List<String> betweenList = new ArrayList<>();

        try {
            Calendar startDay = Calendar.getInstance();
            startDay.setTime(begin);
            startDay.add(Calendar.DATE, -1);

            while (true) {
                startDay.add(Calendar.DATE, 1);
                Date newDate = startDay.getTime();
                String newend = format.format(newDate);
                betweenList.add(newend);
                if (format.format(end).equals(newend)) {//
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return betweenList;
    }

    /**
     * date2比date1多的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2)   //同一年
        {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)    //闰年
                {
                    timeDistance += 366;
                } else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else    //不同年
        {
            System.out.println("判断day2 - day1 : " + (day2 - day1));
            return day2 - day1;
        }
    }
}
