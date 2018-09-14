package com.td.framework.ui.list;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by jc on 2018-03-24.
 * Version:1.0
 * Description:
 * ChangeLog:
 */

public class UIRecyclerView extends RecyclerView{
    public UIRecyclerView(Context context) {
        super(context);
    }

    public UIRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public UIRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    public boolean onTouchEvent(MotionEvent e) {
//        stopNestedScroll();
        return super.onTouchEvent(e);
    }
}
