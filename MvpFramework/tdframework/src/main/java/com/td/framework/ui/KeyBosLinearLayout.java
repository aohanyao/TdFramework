package com.td.framework.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by 江俊超 on 2016/2/22.
 * <p>自定义控件</p>
 * <li>1. 解决软键盘弹出将布局向上顶</li>
 */
public class KeyBosLinearLayout extends LinearLayout {
    private KeyboardChangeListener listener;

    public KeyBosLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    /**
     * 当前活动主窗口大小改变时调用
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (null != listener) {

            if (oldh > 0) {
                if (h < oldh && oldh - h > 400) {
                    listener.onKeyboardChange(true, oldh - h);
                } else if (h > oldh && h - oldh > 400) {//关闭
                    listener.onKeyboardChange(false, h - oldh);
                }
            }
        }
    }

    public void setOnKeyboardChangeListener(KeyboardChangeListener listener) {
        this.listener = listener;
    }

    /**
     * Activity主窗口大小改变时的回调接口(本示例中，等价于软键盘显示隐藏时的回调接口)
     *
     * @author mo
     */
    public interface KeyboardChangeListener {
        public void onKeyboardChange(boolean isOpen, int size);
    }
}
