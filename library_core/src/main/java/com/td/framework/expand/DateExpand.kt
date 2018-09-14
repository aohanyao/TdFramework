package com.td.framework.expand

import com.td.framework.utils.DateUtils

/**
 * Created by jc on 2018-06-16.
 * Version:1.0
 * Description:
 * ChangeLog:
 */
/**
 * 将毫秒数转换为 【yyyy-MM-dd】
 */
fun Long.toDateYearMonthDayForDash(): String {
    return DateUtils.getTimeString(this, DateUtils.FORMAT_1)
}