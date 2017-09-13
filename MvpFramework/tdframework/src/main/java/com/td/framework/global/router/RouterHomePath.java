package com.td.framework.global.router;

/**
 * Created by 江俊超 on 2017/6/29 0029.
 * <p>版本:1.0.0</p>
 * <b>说明<b><br/>
 * <li> * 首页模块路由</li>
 */
public interface RouterHomePath {
    /**
     * 待办相关 路由
     */
    interface Todo {
        /**
         * 待办详情
         */
        String TodoDetails = "/Home/TodoDetails";
    }
}
