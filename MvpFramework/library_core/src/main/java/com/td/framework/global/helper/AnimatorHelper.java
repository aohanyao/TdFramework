package com.td.framework.global.helper;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.td.framework.base.listener.BaseAnimatorListener;

/**
 * Created by jc on 2017/5/16 0016.
 * <p>版本:1.0.0</p>
 * <b>说明<b>动画帮助类<br/>
 * <li></li>
 */
public class AnimatorHelper {
    public static void slideView(@NonNull final View rootPickView, final View arraw) {
        final boolean isVisible = rootPickView.getVisibility() == View.VISIBLE;

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(isVisible ? 1 : 0, isVisible ? 0 : 1).setDuration(150);
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
                    if (arraw != null) {
                        arraw.setRotation(-90);
                    }
                } else {
                    if (arraw != null) {
                        arraw.setRotation(90);
                    }
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

//                if (isVisible) {//显示的情况， 90
//                    if (arraw != null) {
//                        arraw.setRotation(Math.abs(1 - p) * 180);
//                    }
//                } else {//旋转会-90
//                    if (arraw != null) {
//                        arraw.setRotation(Math.abs(p) * 180);
//                    }
//                }

            }
        });
        valueAnimator.start();
    }

    /**
     * 抽屉的方式显示或者隐藏一个布局
     *
     * @param rootPickView
     */
    public static void slideView(@NonNull final View rootPickView) {
        slideView(rootPickView, null);
    }

    /**
     * 显示 或者隐藏布局
     *
     * @param rv    *
     * @param arraw
     */
    public static void displayArrowView(RecyclerView rv, ImageView arraw, RelativeLayout rlRoot) {
        if (rv.getVisibility() == View.GONE) {
            arraw.setRotation(-90f);
            rv.setVisibility(View.VISIBLE);
            rlRoot.setVisibility(View.VISIBLE);
        } else {
            arraw.setRotation(90f);
            rv.setVisibility(View.GONE);
            rlRoot.setVisibility(View.GONE);
        }
    }
}
