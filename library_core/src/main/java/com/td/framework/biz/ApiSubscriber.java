package com.td.framework.biz;

import com.google.gson.JsonParseException;
import com.td.framework.global.app.App;
import com.td.framework.mvp.view.BaseView;
import com.td.framework.utils.DateUtils;
import com.td.framework.utils.FileUtil;
import com.td.framework.utils.L;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;
import org.reactivestreams.Subscriber;

import java.io.FileWriter;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Date;

import io.reactivex.subscribers.ResourceSubscriber;
import okhttp3.ResponseBody;
import retrofit2.HttpException;


/**
 * <p>1.错误处理转换</p>
 */
public abstract class ApiSubscriber<T> extends ResourceSubscriber<T> {
    private BaseView v;

    private ApiSubscriber() {
    }

    /**
     * 使用这个构造函数时候 将错误提示交给 v来处理
     *
     * @param v
     */
    public ApiSubscriber(BaseView v) {
        this.v = v;
    }

    public ApiSubscriber(Subscriber<?> subscriber, BaseView v) {
        this.v = v;
    }

    public ApiSubscriber(Subscriber<?> subscriber, boolean shareSubscriptions, BaseView v) {
        this.v = v;
    }

    @Override
    public void onError(Throwable e) {
        NetError error = null;
        if (e != null) {


            if (!(e instanceof NetError)) {
                if (e instanceof UnknownHostException) {
                    error = new NetError(e, NetError.NoConnectError);
                } else if (e instanceof JSONException || e instanceof JsonParseException) {
                    error = new NetError(e, NetError.ParseError);
                } else if (e instanceof SocketTimeoutException) {
                    error = new NetError(e, NetError.SocketTimeoutError);
                } else if (e instanceof ConnectException) {
                    error = new NetError(e, NetError.ConnectExceptionError);
                } else if (e instanceof HttpException) {
                    //处理网络状态码失败的情况
                    error = handlerRequestFail((HttpException) e);
                } else {
                    error = new NetError(e, NetError.OtherError);
                }
            } else {
                if (L.isDebug) {
                    error = (NetError) e;
                } else {
                    //在这里将错误发送出去
                    MobclickAgent.reportError(App.newInstance(), error);
                    error = new NetError("很抱歉，我们发生一些错误", NetError.OTHER);
                }
            }

            if (useCommonErrorHandler() && BaseApi.getProvider() != null) {
                if (BaseApi.getProvider().handleError(error)) {        //使用通用异常处理
                    return;
                }//
            }
            if (v != null) {
                v.onFail(error);
            } else {
                onFail(error);
            }
            if (L.isDebug)
                e.printStackTrace();
            // onFail(error);
        }

    }

    /**
     * 处理请求码中的错误
     *
     * @param e 抛出错误
     * @return
     */
    private NetError handlerRequestFail(HttpException e) {
        //请求处理
        ResponseBody responseBody = e.response().errorBody();

        String responseBodyString = "";
        try {
            responseBodyString = responseBody.string();

            JSONObject jsonObject = new JSONObject(responseBodyString);

            //先判断登陆的情况
            //--------------登陆start-------------------
            //   登陆失败    {"code":"401","error":"登陆失败！","error_description":"用户名或者密码不正确，请重试！"}
            String login_error_description = "";

            String code = "";
            try {
                code = jsonObject.getString("code");
                login_error_description = jsonObject.getString("error_description");
            } catch (Exception ignored) {

                try {
                    //  商城的情况 {"code":-1,"message":"请先登录系统！"}
                    code = jsonObject.getString("code");
                    if (responseBodyString.contains("请先登录系统") || "-1".equals(code)) {
                        return new NetError("您的登陆令牌已失效，请重新登陆！", NetError.LOGIN_OUT);
                    }
                } catch (Exception ignored2) {
                    try {
                        login_error_description = jsonObject.getString("message");
                    } catch (JSONException ignored1) {
                    }
                }

            }

            //不为空 登陆失败
            if (!login_error_description.isEmpty() && ("-1".equals(code) || "401".equals(code))) {
                //抛出错误
                return new NetError(login_error_description, Integer.parseInt(code));
            }
            //   登陆失败

            //   登陆失效 start
            // token失效{"error":"invalid_token","error_description":"Access token expired"}
            //{"error":"access_denied","error_description":"Invalid token does not contain resource id (smartparty)"}
            String error = null;
            try {
                error = jsonObject.getString("error");
                if (error.equals("invalid_token") || error.equals("unauthorized") || e.code() == 403) {
                    return new NetError("您的登陆令牌已失效，请重新登陆！", NetError.LOGIN_OUT);
                }
            } catch (JSONException ignored) {

            }


            //   登陆失效 token
            //--------------登陆end-------------------


            //---------------------500---------------

            // 500 start
            /**
             * {
             "timestamp": 1521699003572,
             "status": 500,
             "error": "Internal Server Error",
             "exception": "java.lang.ClassCastException",
             "message": "java.lang.Integer cannot be cast to java.lang.Long",
             "path": "/sparty_votes/resultApp"
             }
             */
            //调试模式下 保存错误信息并提示，发布模式下只提示 服务器错误
            // 将错误日志信息存储在本地内存卡中
            // 500 end

            if (e.code() == 500 || L.isDebug) {//服务器错误
                writeError(responseBodyString);
            }
            //---------------------500---------------


        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return new NetError(e, NetError.HttpException);
    }

    /**
     * 发生错误
     *
     * @param error
     */
    public void onFail(NetError error) {
        error.printStackTrace();
    }


    @Override
    public void onComplete() {
    }


    protected boolean useCommonErrorHandler() {
        return true;
    }


    /**
     * 写错错误信息
     */
    private void writeError(String errorJson) {
        try {
            String fileName = DateUtils.getTimeString(new Date(), DateUtils.FORMAT_3) + ".txt";
            FileWriter writer = new FileWriter(FileUtil.getLogDir() + "/" + fileName);
            writer.write(errorJson);
            writer.close();
        } catch (Exception e) {
            if (L.isDebug) {
                e.printStackTrace();
            }
        }
    }

}
