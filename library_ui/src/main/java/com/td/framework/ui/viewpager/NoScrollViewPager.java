package com.td.framework.ui.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2015/12/22.
 * <p>不能滑动的viewPager</p>
 */
public class NoScrollViewPager extends ViewPager {
    private boolean isScrooll = false;

    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if (isScrooll) {
            return super.onTouchEvent(arg0);
        }
        return true;
    }


    public void setScrooll(boolean scrooll) {
        isScrooll = scrooll;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (isScrooll) {
            return super.onInterceptTouchEvent(arg0);
        }
        return isScrooll;
    }

}
