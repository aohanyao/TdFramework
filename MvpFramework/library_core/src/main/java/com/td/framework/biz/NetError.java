package com.td.framework.biz;

import android.text.TextUtils;

import java.io.IOException;

/**
 * Created by wanglei on 2016/12/24.
 */

public class NetError extends IOException {
    private Throwable exception;
    private int type = NoConnectError;
    private String messge;
    public static final int ParseError = 0;   //数据解析异常
    public static final int NoConnectError = 1;   //无连接异常
    public static final int AuthError = 2;   //用户验证异常
    public static final int NoDataError = 3;   //无数据返回异常
    public static final int BusinessError = 4;   //业务异常
    public static final int OtherError = 5;   //其他异常
    public static final int SocketTimeoutError = 6;   //网络连接超时
    public static final int UknownError = 500;   //未知错误
    public static final int ConnectExceptionError = 7;   //无法连接到服务
    public static final int HttpException = 8;   //服务器错误
    public static final int LOGIN_OUT = 510;   //登陆失效
    public static final int USER_NOT_BAND_WECHAT = 1001;   //用户没有绑定
    public static final int OTHER = -99;   //其他
    public static final int UNOKE = -1;   //没有网络
    public static final int NOT_FOUND = 404;   //无法找到

    public NetError(Throwable exception, int type) {
        this.exception = exception;
        this.type = type;
    }

    public NetError(String message, Throwable cause) {
        super(message, cause);
    }

    public NetError(String message, int type) {
        super(message);
        this.type = type;
        this.messge = message;
    }

    @Override
    public String getMessage() {
        if (!TextUtils.isEmpty(messge)) {
            return messge;
        }
        switch (type) {
            case ParseError:
                return "数据解析异常";
            case NoConnectError:
                return "无连接异常";
            case AuthError:
                return "用户验证异常";
            case NoDataError:
                return "无数据返回异常";
            case BusinessError:
                return "业务异常";
            case SocketTimeoutError:
                return "网络连接超时";
            case OTHER:
                return messge;
            case UNOKE:
                return "当前无网络连接";
            case ConnectExceptionError:
                return "无法连接到服务器，请检查网络连接后再试！";
            case HttpException:
                try {
                    if (exception.getMessage().equals("HTTP 500 Internal Server Error")) {
                        return "服务器发生错误！";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (exception.getMessage().contains("Not Found"))
                    return "无法连接到服务器，请检查网络连接后再试！";
                return "服务器发生错误";
        }


        try {
            return exception.getMessage();
        } catch (Exception e) {
            return "未知错误";
        }

    }

    public int getType() {
        return type;
    }
}
