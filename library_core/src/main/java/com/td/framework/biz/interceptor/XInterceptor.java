package com.td.framework.biz.interceptor;

import com.td.framework.biz.RequestHandler;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 拦截器
 */

public class XInterceptor implements Interceptor {

    RequestHandler handler;

    public XInterceptor(RequestHandler handler) {
        this.handler = handler;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (handler != null) {
            request = handler.onBeforeRequest(request, chain);
        }
        Response response = chain.proceed(request);
        if (handler != null) {
            response = handler.onAfterRequest(response, response.body().string(), chain);
        }
        return response;
    }
}
