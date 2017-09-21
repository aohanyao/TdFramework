package com.td.framework.global.router;

/**
 * Created by 江俊超 on 8/11/2017.
 * <p>版本:1.0.0</p>
 * <b>说明<b><br/>
 * <li>基础的 不可拆分的</li>
 */
public interface RouterBasePath {
    /**
     * 用户登陆
     */
    String Login = "/entrance/login";
    /**
     * 条目选择
     */
    String  SelectItem = "/General/SelectItem";
    /**
     * 检测新版本。全量更新
     */
    String CheckVersion = "/Version/NewVersion";
}
