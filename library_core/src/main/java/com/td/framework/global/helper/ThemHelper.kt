package com.td.framework.global.helper

import com.td.framework.R
import com.td.framework.global.app.App
import com.td.framework.utils.SPUtils

/**
 * Created by jc on 2018-02-07.
 * Version:1.0
 * Description: 颜色主题帮助类
 * ChangeLog:
 * --------------------------------------
 * ① 2018年3月7日11:34:40
 * 当前的颜色只使用了一种颜色，也就是primaryColor，
 * primaryDarkColor并没有使用到，后期将会进行修改
 * ，来适配这种情况，视项目的进度而言。
 * --------------------------------------
 *
 * --------------------------------------
 * ② 2018年3月8日12:26:20
 * 修改了方法注释
 * --------------------------------------
 *
 *
 * --------------------------------------
 * ③ 2018年3月15日09:12:50
 * 修改进制转换，如果转入0xFFFFFFFF 则存入的值
 * 是-1，kotlin默认存储的是10进制的值，传入16
 * 进制的值会转换为10进制进行存储。
 * --------------------------------------
 *
 *
 */
object ThemHelper {

    private val PARTY_PRIMARY = "party_primary"
    private val PARTY_PRIMARY_DARK = "party_primary_dark"


    /**
     * 设置主题颜色(十六进制)
     * 0xff000000
     */
    fun setPrimaryColor(primaryColor: Int) {
        SPUtils.put(App.newInstance(), PARTY_PRIMARY, primaryColor)
    }

    /**
     * 设置次主题颜色(十六进制)
     * 0xff000000
     */
    fun setPrimaryDarkColor(primaryDarkColor: Int) {
        SPUtils.put(App.newInstance(), PARTY_PRIMARY_DARK, primaryDarkColor)

    }

    /**
     * 获取主题颜色
     */
    fun getPrimaryColor(): Int {
        var defaultColor = App.newInstance().resources.getColor(R.color.colorPrimary)
        defaultColor = SPUtils.get(App.newInstance(), PARTY_PRIMARY, defaultColor) as Int
        //--------------------
        // 2018年3月15日09:15:19  jc 增加进制转换 注释③ 详情???突然之间好了？
        //--------------------


        return defaultColor
    }

    /**
     * 获取次主题颜色
     */
    fun getPrimaryDarkColor(): Int {

        var defaultColor = App.newInstance().resources.getColor(R.color.colorPrimaryDark)
        defaultColor = SPUtils.get(App.newInstance(), PARTY_PRIMARY_DARK, defaultColor) as Int

        return defaultColor
    }
}