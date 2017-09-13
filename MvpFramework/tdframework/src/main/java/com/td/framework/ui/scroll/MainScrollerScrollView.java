package com.td.framework.ui.scroll;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.td.framework.utils.DensityUtils;


/**
 * Created by Administrator on 2016/12/14 0014.
 * <p>首页的滑动</p>
 */

public class MainScrollerScrollView extends NestedScrollView {
    onScrollChangeListener onScrollChangeListener;
    private int dp2px;
    private float scrollY;
    private float alpha;
    private String TAG;
    private int moveX;
    private int moveY;
    private int dowY;
    private int dowX;
    private int downX;
    private int downY;
    private int mTouchSlop;

    public void setOnScrollChangeListener(MainScrollerScrollView.onScrollChangeListener onScrollChangeListener) {
        this.onScrollChangeListener = onScrollChangeListener;
    }

    public MainScrollerScrollView(Context context) {
        this(context, null);
    }

    public MainScrollerScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainScrollerScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        dp2px = DensityUtils.dp2px(context, 112);
        TAG = getClass().toString();
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }


    //    @Override
//    public boolean onInterceptTouchEvent(MotionEvent e) {
//        int action = e.getAction();
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                downX = (int) e.getRawX();
//                downY = (int) e.getRawY();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                moveX = (int) e.getRawX();
//                moveY = (int) e.getRawY();
//                int abs = moveY - dowY;
//                if (abs > 20 && getScrollY() + getHeight() >= computeVerticalScrollRange() - 100) {//滚动到底部
//                    Log.e(TAG, "啦啦啦: ");
////                    return true;
//
//                } else {
////                    requestDisallowInterceptTouchEvent(false);
//                    Log.e(TAG, "onInterceptTouchEvent: " + abs + " getScrollY() + getHeight()  " + getScrollY() + getHeight() + "  computeVerticalScrollRange" + computeVerticalScrollRange());
//                    return true;
//                }
//        }
//        return super.onInterceptTouchEvent(e);
//    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {

//        if (Build.VERSION.SDK_INT >= 21) {//5.0以上版本
//            int action = e.getAction();
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                downX = (int) e.getRawX();
//                downY = (int) e.getRawY();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                moveX = (int) e.getRawX();
//                moveY = (int) e.getRawY();
//                int abs = moveY - dowY;
//                if (abs > 20 && getScrollY() + getHeight() >= computeVerticalScrollRange() - 100) {//滚动到底部
//                    Log.e(TAG, "啦啦啦: ");
////                    return true;
//
//                } else {
////                    requestDisallowInterceptTouchEvent(false);
//                    Log.e(TAG, "onInterceptTouchEvent: " + abs + " getScrollY() + getHeight()  " + getScrollY() + getHeight() + "  computeVerticalScrollRange" + computeVerticalScrollRange());
//                    return true;
//                }
//        }
//            return super.onInterceptTouchEvent(e);
//        } else {
        int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) e.getRawX();
                downY = (int) e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) e.getRawY();
               // L.e(TAG,"移动："+moveY+ "  Math.abs(moveY - downY) :"+Math.abs(moveY - downY) );
                if (Math.abs(moveY - downY) > mTouchSlop) {
                    return true;
                }
        }
        return super.onInterceptTouchEvent(e);
//        }

    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        alpha = (float) t / dp2px;
        if (alpha > 1)
            alpha = 1;
        if (alpha < 0)
            alpha = 0;
        if (onScrollChangeListener != null) {
            onScrollChangeListener.onScrollChangeListener(alpha);
        }

        super.onScrollChanged(l, t, oldl, oldt);
    }

    public interface onScrollChangeListener {
        void onScrollChangeListener(float alpha);
    }
}
