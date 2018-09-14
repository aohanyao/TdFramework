package com.td.framework.utils;

/**
 * Created by jc on 2018-03-22.
 * Version:1.0
 * Description: 字符串工具类
 * ChangeLog:
 */

public class StringUtils {
    /**
     *首字母大写
     */
    public static String captureName(String name) {
        char[] cs=name.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);
    }
}
