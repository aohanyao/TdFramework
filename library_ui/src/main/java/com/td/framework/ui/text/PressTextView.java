package com.td.framework.ui.text;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import com.td.framework.ui.R;


/**
 * Created by 江俊超 on 2018/8/29.
 * Version:1.0
 * Description:
 * ChangeLog:
 */
public class PressTextView extends TextView {
    /**
     * /**
     * 画笔
     */
    private Paint mPaint;
    /**
     * 背景
     */
    private RectF mBackgroundRectF;
    /**
     * 边框
     */
    private RectF mBorderRectF;

    private PressTextConfig mPressTextConfig;

    public PressTextView(Context context) {
        this(context, null);
    }

    public PressTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PressTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    /**
     * 初始化相关
     */
    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mPressTextConfig = new PressTextConfig();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PressTextView);
        mPressTextConfig.mRadius = typedArray.getDimension(R.styleable.PressTextView_Radius, 0);
        //------------背景颜色
        // 按下背景颜色
        mPressTextConfig.mPressBackgroundColor = typedArray.getColor(R.styleable.PressTextView_PressBackgroundColor, Color.TRANSPARENT);
        // 正常情况下背景颜色
        mPressTextConfig.mNormalBackgroundColor = typedArray.getColor(R.styleable.PressTextView_NormalBackgroundColor, Color.TRANSPARENT);
        // 禁用情况下背景颜色
        mPressTextConfig.mDisableBackgroundColor = typedArray.getColor(R.styleable.PressTextView_DisableBackgroundColor, Color.TRANSPARENT);
        // 选中状态下背景颜色
        mPressTextConfig.mSelectBackgroundColor = typedArray.getColor(R.styleable.PressTextView_SelectBackgroundColor, Color.TRANSPARENT);
        //------------背景颜色

        //------------字体颜色
        // 按下情况下字体颜色
        mPressTextConfig.mPressTextColor = typedArray.getColor(R.styleable.PressTextView_PressTextColor, Color.WHITE);
        // 正常情况下字体颜色
        mPressTextConfig.mNormalTextColor = typedArray.getColor(R.styleable.PressTextView_NormalTextColor, Color.WHITE);
        // 禁用情况下字体颜色
        mPressTextConfig.mDisableTextColor = typedArray.getColor(R.styleable.PressTextView_DisableTextColor, Color.WHITE);
        // 选中情况下字体颜色
        mPressTextConfig.mSelectTextColor = typedArray.getColor(R.styleable.PressTextView_SelectTextColor, Color.WHITE);

        //------------字体颜色

        // 释放资源
        typedArray.recycle();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        setClickable(true);
        setFocusable(true);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        postInvalidate();
        return super.onTouchEvent(event);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);

        mPaint.setColor(switchBackgroundColor());
        if (mBackgroundRectF == null) {
            mBackgroundRectF = new RectF(0, 0, getWidth(), getHeight());
        }
        // 绘制背景
        canvas.drawRoundRect(mBackgroundRectF, mPressTextConfig.mRadius, mPressTextConfig.mRadius, mPaint);
        canvas.restore();

        // 绘制边框
//        if (mBorderRectF == null) {
//            mBorderRectF = new RectF(mPressTextConfig.mBorderWidth / 3,
//                    mPressTextConfig.mBorderWidth / 3,
//                    getWidth() - mPressTextConfig.mBorderWidth / 3, getHeight() - mPressTextConfig.mBorderWidth / 3);
//        }
//        canvas.save();
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setAntiAlias(true);
//        mPaint.setColor(0xff999999);
//        mPaint.setStrokeWidth(mPressTextConfig.mBorderWidth);
//        canvas.drawRoundRect(mBorderRectF, mPressTextConfig.mRadius, mPressTextConfig.mRadius, mPaint);
//        canvas.restore();


        super.onDraw(canvas);

        setTextColor(switchTextColor());
    }

    private int switchTextColor() {
        // 禁用
        if (!isEnabled()) {
            return mPressTextConfig.mDisableTextColor;
        }
        // 选择
        if (isSelected()) {
            return mPressTextConfig.mSelectTextColor;
        }

        // 按下
        if (isPressed()) {
            return mPressTextConfig.mPressTextColor;
        }

        return mPressTextConfig.mNormalTextColor;
    }


    /**
     * 选择背景颜色
     */
    private int switchBackgroundColor() {
        // 禁用
        if (!isEnabled()) {
            return mPressTextConfig.mDisableBackgroundColor;
        }
        // 选择
        if (isSelected()) {
            return mPressTextConfig.mSelectBackgroundColor;
        }

        // 按下
        if (isPressed()) {
            return mPressTextConfig.mPressBackgroundColor;
        }
        return mPressTextConfig.mNormalBackgroundColor;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
    }
}
