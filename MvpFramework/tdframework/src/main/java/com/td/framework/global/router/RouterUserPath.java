package com.td.framework.global.router;

/**
 * Created by 江俊超 on 2017/6/29 0029.
 * <p>版本:1.0.0</p>
 * <b>说明<b><br/>
 * <li>用户模块的路由</li>
 */
public interface RouterUserPath {

    /**
     * 进入相关
     * <p>登陆之前的</p>
     */
    interface Entrance {
        /**
         * 用户登陆
         */
        String Login = "/entrance/login";
        /**
         * 注册
         */
        String Register = "/entrance/register";
    }

    /**
     * 用户相关
     * <p>登陆之后的</p>
     */
    interface User {
        /**
         * 用户信息
         */
        String UserInfo = "/user/UserInfo";
    }
}
