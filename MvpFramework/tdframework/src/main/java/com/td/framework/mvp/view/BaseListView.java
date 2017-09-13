package com.td.framework.mvp.view;

import java.util.List;

/**
 * Created by 江俊超 on 2017/2/15 0015.
 * <p>版本:1.0.0</p>
 * <b>说明<b><br/>
 * <li>列表的View<li>
 */
public interface BaseListView<T> extends BaseView {
    /**
     * 刷新的数据
     *
     * @param infos
     */
    void refreshData(List<T> infos);

    /**
     * 加载更多的数据
     *
     * @param infos
     */
    void loadMoresData(List<T> infos);

    /**
     * 没有更多的数据了
     */
    void noMoreData();
}
