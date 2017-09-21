package com.td.framework.ui;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

import com.td.framework.utils.statusbar.StatusBarUtil;

/**
 * Created by 江俊超 on 2017/7/14 0014.
 * <p>版本:1.0.0</p>
 * <b>说明<b><br/>
 * <li>填充状态栏</li>
 */
public class VStatusBar extends View {
    public VStatusBar(Context context) {
        super(context);
    }

    public VStatusBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public VStatusBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        WindowManager systemService = (WindowManager) (getContext().getSystemService(Context.WINDOW_SERVICE));
        setMeasuredDimension(systemService.getDefaultDisplay().getWidth(),
                StatusBarUtil.getStatusBarHeight(getContext()));

        setVisibility(Build.VERSION.SDK_INT > 19 ? VISIBLE : GONE);
    }
}
