package com.td.framework.global.helper;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.td.framework.base.BaseAnimatorListener;

/**
 * Created by 江俊超 on 2017/5/16 0016.
 * <p>版本:1.0.0</p>
 * <b>说明<b>动画帮助类<br/>
 * <li></li>
 */
public class AnimatorHelper {
    /**
     * 抽屉的方式显示或者隐藏一个布局
     *
     * @param rootPickView
     */
    public static void slideView(@NonNull final View rootPickView) {
        final boolean isVisible = rootPickView.getVisibility() == View.VISIBLE;

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(isVisible ? 1 : 0, isVisible ? 0 : 1).setDuration(300);
        final int mLayoutParamsHeight = rootPickView.getLayoutParams().height;
        final int mHeight = rootPickView.getHeight();
        valueAnimator.addListener(new BaseAnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                if (!isVisible) {
                    rootPickView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ViewGroup.LayoutParams layoutParams = rootPickView.getLayoutParams();
                layoutParams.height = mLayoutParamsHeight;
                if (mLayoutParamsHeight < 0) {
                    layoutParams.height = mHeight;
                }
                rootPickView.setLayoutParams(layoutParams);
                if (isVisible) {
                    rootPickView.setVisibility(View.GONE);
                }

            }
        });
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float p = (float) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = rootPickView.getLayoutParams();
                layoutParams.height = (int) (mLayoutParamsHeight * p);
                if (mLayoutParamsHeight < 0) {
                    layoutParams.height = (int) (mHeight * p);
                }
                rootPickView.setLayoutParams(layoutParams);

            }
        });
        valueAnimator.start();
    }
}
