package com.td.framework.biz.cookie;

import android.content.Context;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * 自动管理Cookies
 */
public class CookiesManager implements CookieJar {
    private  PersistentCookieStore cookieStore;

    public CookiesManager(Context context) {
        cookieStore = new PersistentCookieStore(context);
    }


    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {

                try {
                    cookieStore.add(url, item);
                } catch (Exception ignored) {
                    ignored.printStackTrace();
                }
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest( HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url);
        return cookies;
    }
}