package com.td.framework.ui.adapter;


import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.yida.cloud.ui.R;

/**
 * 自定义的加载视图
 */

public final class CustomLoadMoreView extends LoadMoreView {

    @Override public int getLayoutId() {
        return R.layout.list_view_load_more;
    }

    @Override protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    @Override protected int getLoadEndViewId() {
        return R.id.load_more_load_end_view;
    }
}
