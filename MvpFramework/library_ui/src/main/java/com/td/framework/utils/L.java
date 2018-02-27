package com.td.framework.utils;

import com.orhanobut.logger.Logger;

//Logcatͳ
public class L {


    private L() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isDebug = true;// 是否需要打印bug，可以在application的onCreate函数里面初始化

    // 下面四个是默认tag的函数
    public static void i(String msg) {
        if (isDebug)
            Logger.i(msg);
    }

    public static void d(String msg) {
        if (isDebug)
            Logger.d( msg);
    }

    public static void e(String msg) {
        if (isDebug)
            Logger.e(msg);
    }

    public static void v(String msg) {
        if (isDebug)
            Logger.v(msg);
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (isDebug)
            Logger.e( msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            Logger.e( msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            Logger.e( msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            Logger.e(msg);
    }
}