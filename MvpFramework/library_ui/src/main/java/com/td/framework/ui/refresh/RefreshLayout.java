package com.td.framework.ui.refresh;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

/**
 * Created by jc on 8/3/2017.
 * <p>版本:1.0.0</p>
 * <b>说明<b><br/>
 * <li></li>
 */
public class RefreshLayout extends SwipeRefreshLayout implements RefreshViewInterface{
    public RefreshLayout(Context context) {
        super(context);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

}
