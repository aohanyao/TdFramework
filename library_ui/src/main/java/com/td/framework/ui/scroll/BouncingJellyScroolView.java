package com.td.framework.ui.scroll;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ScrollView;

import com.td.framework.utils.ScreenUtils;

import java.security.SecureRandom;
import java.util.Random;

/**
 * <p>作者：jc on 2016/8/30 14:42</p>
 * <p>
 * <p>全局略缩</p>
 */
public class BouncingJellyScroolView extends ScrollView {
    private int dowX;
    private int dowY;
    private int moveX;
    private int moveY;
    private float v;
    private ValueAnimator animator;
    private boolean isTop = true;
    private String TAG = "BouncingJellyScroolView";
    private View childAt;
    private OnBouncingJellyListener onBouncingJellyListener;
    private boolean isBouncingJelly;
    private Random mRandom;
    /**
     * 弹跳偏移
     */
    private float bouncingOffset = 2850f;
    /**
     * 弹跳系数
     */
    private float BOUNCE_COEFFICIENT = 0.4f;

    public BouncingJellyScroolView(Context context) {
        super(context);
        init();
    }

    private void init() {
        bouncingOffset = ScreenUtils.getScreenHeight(getContext()) * 3;
        isBouncingJelly = true;
        mRandom = new SecureRandom();
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
        childAt.setPivotX(getWidth() / 2);
        childAt.setPivotY(0);

        if (isBouncingJelly)
            childAt.setScaleY(bouncingJelly);
        if (onBouncingJellyListener != null) {
            onBouncingJellyListener.onBouncingJellyTop(bouncingJelly);
        }

    }

    /**
     * 从顶部开始滑动
     */
    public void bouncingBottom() {
        float bouncingJelly = 1.0f + v;
        childAt.setPivotX(getWidth() / 2);
        childAt.setPivotY(childAt.getHeight());
        if (isBouncingJelly)
            childAt.setScaleY(bouncingJelly);

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
                if (v > BOUNCE_COEFFICIENT) {
                    v = BOUNCE_COEFFICIENT;
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

    protected void back() {
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
//        animator.addListener(new BaseAnimatorListener() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                if (mRandom.nextInt(1000) == mRandom.nextInt(1000)) {
//                    T.showToast(getContext(), "~~~~(>_<)~~~~");
//                }
//            }
//        });
        animator.start();
    }

    public interface OnBouncingJellyListener {
        /**
         * 顶部弹跳
         *
         * @param bouncingJelly 弹跳系数
         */
        void onBouncingJellyTop(float bouncingJelly);

        /**
         * 底部弹跳
         *
         * @param bouncingJelly 弹跳系数
         */
        void onBouncingJellyBottom(float bouncingJelly);
    }
}
