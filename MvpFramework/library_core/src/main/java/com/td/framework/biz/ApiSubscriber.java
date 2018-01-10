package com.td.framework.biz;

import com.google.gson.JsonParseException;
import com.td.framework.global.app.App;
import com.td.framework.mvp.view.BaseView;
import com.td.framework.utils.L;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.reactivestreams.Subscriber;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.subscribers.ResourceSubscriber;
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
                    error = new NetError(e, NetError.HttpException);
                } else {
                    error = new NetError(e, NetError.OtherError);
                }
            } else {
                //TODO 这里捕获全局异常
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
     * 发生错误
     *
     * @param error
     */
    public void onFail(NetError error) {
        error.printStackTrace();
    }

//    @Override
//    public void onSubscribe(Subscription s) {
//        if (!NetUtils.isConnected(App.newInstance())) {
//            onError(new NetError(new RuntimeException(""), NetError.UNOKE));
//        }
//    }

    @Override
    public void onComplete() {
//        if (v != null) {
//            v.complete(null);
//        }
    }


    protected boolean useCommonErrorHandler() {
        return true;
    }


}
