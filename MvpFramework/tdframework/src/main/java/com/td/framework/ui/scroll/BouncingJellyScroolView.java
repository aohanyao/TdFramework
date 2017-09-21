package com.td.framework.ui.scroll;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ScrollView;

import com.nineoldandroids.view.ViewHelper;
import com.td.framework.base.listener.BaseAnimatorListener;
import com.td.framework.utils.ScreenUtils;
import com.td.framework.utils.T;

import java.util.Random;

/**
 * <p>作者：江俊超 on 2016/8/30 14:42</p>
 * <p>邮箱：928692385@qq.com</p>
 * <p>全局略缩</p>
 */
public class BouncingJellyScroolView extends ScrollView {
    private int dowX;
    private int dowY;
    private int moveX;
    private int moveY;
    private float bouncingOffset = 2850f;
    private float v;
    private ValueAnimator animator;
    private boolean isTop = true;
    private String TAG = "BouncingJellyScroolView";
    private View childAt;
    private OnBouncingJellyListener onBouncingJellyListener;
    private boolean isBouncingJelly;
    private Random mRandom;

    public BouncingJellyScroolView(Context context) {
        super(context);
        init();
    }

    private void init() {
        bouncingOffset = ScreenUtils.getScreenHeight(getContext()) * 3;
        isBouncingJelly = true;
        mRandom = new Random();
    }

    public BouncingJellyScroolView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BouncingJellyScroolView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        childAt = getChildAt(0);
    }

    /**
     * 从顶部开始滑动
     */
    public void bouncingTo() {
        float bouncingJelly = 1.0f + v;
        ViewHelper.setPivotX(childAt, getWidth() / 2);
        ViewHelper.setPivotY(childAt, 0);
        if (isBouncingJelly)
            ViewHelper.setScaleY(childAt, 1.0f + v);
        if (onBouncingJellyListener != null) {
            onBouncingJellyListener.onBouncingJellyTop(bouncingJelly);
        }

    }

    /**
     * 从顶部开始滑动
     */
    public void bouncingBottom() {
        float bouncingJelly = 1.0f + v;
        ViewHelper.setPivotX(childAt, getWidth() / 2);
        ViewHelper.setPivotY(childAt, childAt.getHeight());
        if (isBouncingJelly)
            ViewHelper.setScaleY(childAt, bouncingJelly);
        if (onBouncingJellyListener != null) {
            onBouncingJellyListener.onBouncingJellyBottom(bouncingJelly);
        }
    }

    public void setOnBouncingJellyListener(OnBouncingJellyListener onBouncingJellyListener) {
        this.onBouncingJellyListener = onBouncingJellyListener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        Log.e(TAG, "onInterceptTouchEvent: " );
        return super.onInterceptTouchEvent(ev);
    }

    public void setBouncingJelly(boolean bouncingJelly) {
        isBouncingJelly = bouncingJelly;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dowX = (int) event.getRawX();
                dowY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = (int) event.getRawX();
                moveY = (int) event.getRawY();
                int abs = moveY - dowY;
                v = (Math.abs(abs) / bouncingOffset);
                if (v > 0.3f) {
                    v = 0.3f;
                }
                if (abs > 20 && getScrollY() == 0) {
                    isTop = true;
                    bouncingTo();
                    //  requestDisallowInterceptTouchEvent(true);
                    // return true;
                } else if (abs < -20 && getScrollY() + getHeight() >= computeVerticalScrollRange()) {//滚动到底部
                    isTop = false;
                    bouncingBottom();
                    return true;

                } else {
                    v = 0;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (v > 0) {
                    back();
                    return true;
                }
                break;
        }
//        Log.e(TAG, "dispatchTouchEvent: " );
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
//        Log.e(TAG, "onTouchEvent: " );
        return super.onTouchEvent(ev);
    }

    private void back() {
        if (animator != null && animator.isRunning()) {
            animator.cancel();
            animator = null;
            v = 0;
            bouncingTo();
        }
        animator = ValueAnimator.ofFloat(v, 0).setDuration(300);
        animator.setInterpolator(new OvershootInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v = (float) animation.getAnimatedValue();
                if (isTop) {
                    bouncingTo();
                } else {
                    bouncingBottom();
                }
            }

        });
        animator.addListener(new BaseAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (mRandom.nextInt(1000) == mRandom.nextInt(1000)) {
                    T.showToast(getContext(), "~~~~(>_<)~~~~");
                }
            }
        });
        animator.start();
    }

    public interface OnBouncingJellyListener {
        /**
         * 顶部弹跳
         *
         * @param bouncingJelly
         */
        void onBouncingJellyTop(float bouncingJelly);

        /**
         * 底部弹跳
         *
         * @param bouncingJelly
         */
        void onBouncingJellyBottom(float bouncingJelly);
    }
}
