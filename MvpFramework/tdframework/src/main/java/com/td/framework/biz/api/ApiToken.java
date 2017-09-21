package com.td.framework.biz.api;

import com.td.framework.biz.BaseApi;
import com.td.framework.biz.service.TokenService;

/**
 * Created by 江俊超 on 7/24/2017.
 * <p>版本:1.0.0</p>
 * <b>说明<b><br/>
 * <li>获取Token相关</li>
 */
public class ApiToken {
    private static TokenService tokenService;

    public static TokenService getTokenService() {
        if (tokenService == null) {
            tokenService = BaseApi.getInstance()
                    .getRetrofit(true)
                    .create(TokenService.class);
        }
        return tokenService;
    }
}
