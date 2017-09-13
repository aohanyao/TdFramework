package com.td.framework.ui.caneldar;

import java.io.Serializable;

/**
 * Created by 江俊超 on 2016/12/29 0029.
 * <p>Gihub https://github.com/aohanyao</p>
 * <p>日历信息</p>
 */

public class CandlerInfo implements Serializable {
    private int year;
    private int month;
    private int day;
    private String date;
    private String monthAndDay;
    private String time;
    private String type;

    public CandlerInfo(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        date = year + "" + month + "" + day + "";
        if (month < 10) {
            date = year + "0" + month + "" + day + "";
        } else {
            date = year + "" + month + "" + day + "";
        }
        monthAndDay = month + "月" + day + "日";
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * yyyy-mm-dd
     */
    public String getDataFormat() {
        date = year + "-" + month + "-" + day + "";
        if (month < 10) {
            if (day < 10) {
                date = year + "-0" + month + "-0" + day + "";
            } else {
                date = year + "-0" + month + "-" + day + "";
            }
        } else {
            date = year + "-" + month + "-" + day + "";
        }
        return date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public CandlerInfo(String year, String month, String day) {
        this.year = Integer.parseInt(year);
        this.month = Integer.parseInt(month);
        this.day = Integer.parseInt(day);
        date = year + "" + month + "" + day + "";
        if (this.month < 10) {
            date = year + "" + month + "" + day + "";
        } else {
            date = year + "" + month + "" + day + "";
        }
        monthAndDay = month + "月" + day + "日";
    }


    public int getYear() {
        return year;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonthAndDay() {
        return monthAndDay;
    }

    public void setMonthAndDay(String monthAndDay) {
        this.monthAndDay = monthAndDay;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
