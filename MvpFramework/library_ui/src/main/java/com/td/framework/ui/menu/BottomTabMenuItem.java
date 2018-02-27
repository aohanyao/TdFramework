package com.td.framework.ui.menu;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.td.framework.ui.base.LinearLayout;

/**
 * Created by jc on 2018-02-27.
 * Version:1.0
 * Description: 底部菜单的item
 * ChangeLog:
 */
public class BottomTabMenuItem extends LinearLayout {
    public BottomTabMenuItem(Context context) {
        this(context, null);
    }

    public BottomTabMenuItem(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomTabMenuItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //1.顶部图片
    //2.底部文字


    private void init() {

    }


}
