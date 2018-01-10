package com.td.framework.ui.chart;

import android.animation.FloatEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.td.framework.utils.DensityUtils;
import com.yida.cloud.ui.R;

import java.text.DecimalFormat;
import java.util.Random;


/**
 * 圆角的进度条
 */
public class RoundFilletProcess extends View {

    /**
     * 自定义属性：
     * <p/>
     * 1. 外层圆的颜色 roundColor
     * <p/>
     * 2. 弧形进度圈的颜色 rouncProgressColor
     * <p/>
     * 3. 中间百分比文字的颜色 textColor
     * <p/>
     * 4. 中间百分比文字的大小 textSize
     * <p/>
     * 5. 圆环的宽度（以及作为弧形进度的圆环宽度）
     * <p/>
     * 6. 圆环的风格（Paint.Style.FILL  Paint.Syle.Stroke）
     */


    private static final String TAG = "MyRounProcess";
    private int[] mProcessColor = {0xfff2db43, 0xffeb5f7c, 0xff53deae, 0xffb971ea, 0xff7bd876, 0xff79a3f0};
    private int[] mProcessBgColor = {0x33f2db43, 0x33eb5f7c, 0x3353deae, 0x33b971ea, 0x337bd876, 0x3379a3f0};

    private int mWidth;
    private int mHeight;

    private Paint mPaint;
    private Paint mTextPaint;

    private float progress = 0f;
    private final float maxProgress = 100f; // 不可以修改的最大值

    //region 自定义属性的值
    int roundColor;
    int roundProgressColor;
    int textColor;
    float textSize;
    //endregion
    private boolean isRound;

    // 画笔的粗细（默认为40f, 在 onLayout 已修改）
    private float mStrokeWidth = 40f;
    private ValueAnimator mAnimator;
    private Random mRandom;

    public RoundFilletProcess(Context context) {
        this(context, null);
    }

    public RoundFilletProcess(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundFilletProcess(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 初始化属性
        initAttrs(context, attrs, defStyleAttr);
    }

    /**
     * 初始化属性
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = null;
        mRandom = new Random();
        int mRandomIndex = mRandom.nextInt(mProcessColor.length - 1);
        try {
            a = context.obtainStyledAttributes(attrs, R.styleable.RoundFilletProcess);

            roundColor = a.getColor(R.styleable.RoundFilletProcess_roundColor, mProcessBgColor[mRandomIndex]);
            roundProgressColor = a.getColor(R.styleable.RoundFilletProcess_roundProgressColor, mProcessColor[mRandomIndex]);
            textColor = a.getColor(R.styleable.RoundFilletProcess_textColor, mProcessColor[mRandomIndex]);
            textSize = a.getDimension(R.styleable.RoundFilletProcess_textSize, DensityUtils.sp2px(getContext(), 15f));
            isRound = a.getBoolean(R.styleable.RoundFilletProcess_chart_round, true);
        } finally {
            // 注意，别忘了 recycle
            a.recycle();
        }
    }


    /**
     * 当开始布局时候调用
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        // 获取总的宽高
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        // 初始化各种值
        initValue();

        // 设置圆环画笔
        setupPaint();

        // 设置文字画笔
        setupTextPaint();
    }

    /**
     * 初始化各种值
     */
    private void initValue() {
        // 画笔的粗细为总宽度的 0.14
        mStrokeWidth = mWidth * 0.14f;
    }

    /**
     * 设置圆环画笔
     */
    private void setupPaint() {
        // 创建圆环画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(roundColor);
        mPaint.setStyle(Paint.Style.STROKE); // 边框风格
        mPaint.setStrokeWidth(mStrokeWidth);
    }

    /**
     * 设置文字画笔
     */
    private void setupTextPaint() {
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(textColor);
        mTextPaint.setTextSize(textSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 第一步：绘制一个圆环
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setColor(roundColor);
        float cx = mWidth / 2.0f;
        float cy = mHeight / 2.0f;
        float radius = mWidth / 2.0f - mStrokeWidth / 2.0f;
        canvas.drawCircle(cx, cy, radius, mPaint);

        // 第二步：绘制文字
        String text = formatFloat(progress / maxProgress * 100) + "%";
        Rect bounds = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), bounds);
        canvas.drawText(text, mWidth / 2 - bounds.width() / 2, mHeight / 2 + bounds.height() / 2, mTextPaint);

        // 第三步：绘制动态进度圆环
        mPaint.setDither(true);
        mPaint.setStrokeJoin(Paint.Join.BEVEL);
        if (isRound) {
            mPaint.setStrokeCap(Paint.Cap.ROUND); //  设置笔触为圆形
        } else {
            mPaint.setStrokeCap(Paint.Cap.BUTT); //  设置笔触为方
        }

        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setColor(roundProgressColor);
        RectF oval = new RectF(0 + mStrokeWidth / 2, 0 + mStrokeWidth / 2,
                mWidth - mStrokeWidth / 2, mHeight - mStrokeWidth / 2);

        canvas.drawArc(oval, -90, progress / maxProgress * 360, false, mPaint);
    }

    /**
     * 设置当前显示的进度条
     *
     * @param progress
     */
    public void setProgress(float progress) {
        if (progress > 0) {
            int mRandomIndex = mRandom.nextInt(mProcessColor.length - 1);
            roundColor = mProcessBgColor[mRandomIndex];
            roundProgressColor = mProcessColor[mRandomIndex];
            textColor = mProcessColor[mRandomIndex];
            // 取消动画
            cancelAnimate();
            // 重新开启动画
            runAnimate(progress);
        }
    }


    /**
     * 开始执行动画
     *
     * @param targetProgress 最终到达的进度
     */
    public void runAnimate(float targetProgress) {
        // 运行之前，先取消上一次动画
        cancelAnimate();

        mAnimator = ValueAnimator.ofObject(new FloatEvaluator(), 0, targetProgress);
        // 设置差值器
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                progress = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });

        mAnimator.setDuration((long) (targetProgress * 15));
        mAnimator.start();
    }

    /**
     * 取消动画
     */
    public void cancelAnimate() {
        if (mAnimator != null && mAnimator.isRunning()) {
            mAnimator.cancel();
        }
    }

    private String formatFloat(float scale) {
        DecimalFormat fnum = new DecimalFormat("##0.0");
        return fnum.format(scale);
    }
}