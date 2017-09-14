package com.td.framework.biz;

import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * 网络提供者
 * <p>在Application中使用</p>
 */
public interface NetProvider {
    /**
     * IP地址
     * @return
     */
    String configBaseUrl();

    /**
     * 拦截器
     * @return
     */
    Interceptor[] configInterceptors();

    /**
     * https配置
     * @param builder
     */
    void configHttps(OkHttpClient.Builder builder);

    /**
     * cookie配置
     * @return
     */
    CookieJar configCookie();

    /**
     * 配置处理器
     * @return
     */
    RequestHandler configHandler();

    /**
     * 连接超时时间
     * @return
     */
    long configConnectTimeoutMills();

    /**
     * 读取超时时间
     * @return
     */
    long configReadTimeoutMills();

    /**
     * 是否调试模式
     * @return
     */
    boolean configLogEnable();

    /**
     * 处理错误
     * @param error
     * @return
     */
    boolean handleError(NetError error);
}
