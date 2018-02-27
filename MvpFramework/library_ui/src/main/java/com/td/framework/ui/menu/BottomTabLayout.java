package com.td.framework.ui.menu;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.td.framework.ui.base.LinearLayout;

/**
 * Created by jc on 2018-02-27.
 * Version:1.0
 * Description:
 * ChangeLog:
 */

public class BottomTabLayout extends LinearLayout {
    public BottomTabLayout(Context context) {
        this(context, null);
    }

    public BottomTabLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomTabLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
