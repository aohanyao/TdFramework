package com.td.framework.ui.refresh;

/**
 * Created by 江俊超 on 8/3/2017.
 * <p>版本:1.0.0</p>
 * <b>说明<b><br/>
 * <li></li>
 */
public interface RefreshViewInterface {
    /**
     * 开始刷新
     */
    void setRefreshing(boolean refreshing);

    /**
     * 获取是否正在刷新
     * @return
     */
    boolean isRefreshing();
}
