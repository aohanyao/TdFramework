package com.td.framework.biz;

import android.text.TextUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.CustGsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 基类  API
 */
public class BaseApi {
    private static NetProvider provider = null;
    private Retrofit retrofit = null;
    private OkHttpClient client = null;
    public static final String AUTHORIZATION = "Authorization";
    private static final long connectTimeoutMills = 10 * 1000L;
    private static final long readTimeoutMills = 10 * 1000L;

    private static BaseApi instance;

    private BaseApi() {

    }


    public static synchronized Retrofit createRetrofit() {
        return getInstance().getRetrofit(true);
    }

    public static synchronized BaseApi getInstance() {
        if (instance == null)
            instance = new BaseApi();
        return instance;
    }
//    public static BaseApi getInstance() {
//        if (instance == null) {
//            synchronized (BaseApi.class) {
//                if (instance == null) {
//                    instance = new BaseApi();
//                }
//            }
//        }
//        return instance;
//    }


    /**
     * 统一RUL
     *
     * @param service 服务
     * @param <S>     泛型
     * @return 泛型
     */
    public static <S> S get(Class<S> service) {
        return getInstance().getRetrofit(true).create(service);
    }

    /***
     * 外部传递url
     *
     * @param service 服务
     * @param baseUrl url
     * @param <S> 泛型
     * @return 泛型
     */
    public static <S> S get(Class<S> service, String baseUrl) {
        return getInstance().getRetrofit(true, baseUrl).create(service);
    }

    public static void registerProvider(NetProvider provider) {
        BaseApi.provider = provider;
    }


    public Retrofit getRetrofit(boolean useRx) {
        checkProvider();
        if (retrofit == null) {
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(provider.configBaseUrl())
                    .client(getClient())
                    .addConverterFactory(CustGsonConverterFactory.create());
            if (useRx) {
                builder.addCallAdapterFactory(RxJavaCallAdapterFactory.create());
            }
            retrofit = builder.build();
        }
        return retrofit;
    }

    /**
     * 传递url
     *
     * @param useRx
     * @param baseUrl
     * @return
     */
    public Retrofit getRetrofit(boolean useRx, String baseUrl) {
        checkProvider();
        if (retrofit == null) {
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(getClient())
                    .addConverterFactory(CustGsonConverterFactory.create());
            if (useRx) {
                builder.addCallAdapterFactory(RxJavaCallAdapterFactory.create());
            }
            retrofit = builder.build();
        }
        return retrofit;
    }

    public OkHttpClient getClient() {
        checkProvider();

        if (client == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            builder.connectTimeout(provider.configConnectTimeoutMills() != 0
                    ? provider.configConnectTimeoutMills()
                    : connectTimeoutMills, TimeUnit.MILLISECONDS);
            builder.readTimeout(provider.configReadTimeoutMills() != 0
                    ? provider.configReadTimeoutMills() : readTimeoutMills, TimeUnit.MILLISECONDS);

            CookieJar cookieJar = provider.configCookie();
            if (cookieJar != null) {
                builder.cookieJar(cookieJar);
            }
            provider.configHttps(builder);

            RequestHandler handler = provider.configHandler();
            if (handler != null) {
                builder.addInterceptor(new XInterceptor(handler));
            }

            Interceptor[] interceptors = provider.configInterceptors();
            if (interceptors != null && interceptors.length > 0) {
                for (Interceptor interceptor : interceptors) {
                    builder.addInterceptor(interceptor);
                }
            }
            if (provider.configLogEnable()) {//打印log？
                HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
                logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(logInterceptor);
            }
            client = builder.build();
        }
        //在请求头中间增加认证参数
//        final String mAuthorization = (String) SPUtils.get(App.newInstance(), AUTHORIZATION, "");
//        if (!TextUtils.isEmpty(mAuthorization)) {
//            //添加http请求头
//            builder.addInterceptor(new Interceptor() {
//                @Override
//                public Response intercept(Chain chain) throws IOException {
//                    Request re = chain.request().newBuilder()
//
//                            .addHeader("Authorization", mAuthorization)
//                            .build();
//                    L.e("添加请求头："+mAuthorization);
//                    return chain.proceed(re);
//                }
//            });
//        }
        return client;
    }


    private static void checkProvider() {
        if (provider == null
                || TextUtils.isEmpty(provider.configBaseUrl())) {
            throw new IllegalStateException("must register provider first");
        }
    }

    public static NetProvider getProvider() {
        return provider;
    }

    /**
     * 线程切换
     *
     * @return
     */
    public static <T> Observable.Transformer<T, T> getScheduler() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }




}
