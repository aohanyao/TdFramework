package com.td.framework.ui.chart;

import android.animation.Animator;
import android.animation.FloatEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.td.framework.R;
import com.td.framework.base.listener.BaseAnimatorListener;
import com.td.framework.utils.DensityUtils;

import java.text.DecimalFormat;


/**
 * 直角的，带阴影的进度条
 */
public class RoundShadowProcess extends View {

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

    // 画笔的粗细（默认为40f, 在 onLayout 已修改）
    private float mStrokeWidth = 40f;
    private ValueAnimator mAnimator;
    private Bitmap mShadowBackgroundBitmap;
    private float mShadowWidth;
    private String mText;

    private float[] mPieSweep;
    //初始画弧所在的角度
    private static final int START_DEGREE = -90;

    private static final int PIE_ANIMATION_VALUE = 100;
    //外圆边框的宽度
    private static int OUTER_LINE_WIDTH = 3;
    //动画时间
    private static final int ANIMATION_DURATION = 800;

    private RectF mRectF = new RectF();
    //圆周率
    private static final float PI = 3.1415f;

    private static final int PART_ONE = 1;

    private static final int PART_TWO = 2;

    private static final int PART_THREE = 3;

    private static final int PART_FOUR = 4;
    private float mRadius;
    private int dowX;
    private int dowY;
    private RectF oval;


    public void setOnSpecialTypeClickListener(OnSpecialTypeClickListener listener) {
        this.mListener = listener;
    }

    private OnSpecialTypeClickListener mListener;

    public RoundShadowProcess(Context context) {
        this(context, null);
    }

    public RoundShadowProcess(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundShadowProcess(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 初始化属性
        initAttrs(context, attrs, defStyleAttr);
        mShadowWidth = DensityUtils.dp2px(getContext(), 8f);

        setClickable(true);
        setFocusable(true);

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
        try {
            a = context.obtainStyledAttributes(attrs, R.styleable.RoundShadowProcess);

            roundColor = a.getColor(R.styleable.RoundShadowProcess_s_roundColor, 0xff3cd1fa);
            roundProgressColor = a.getColor(R.styleable.RoundShadowProcess_s_roundProgressColor, 0xff1d9ff0);
            textColor = a.getColor(R.styleable.RoundShadowProcess_s_textColor, 0xff666666);
            textSize = a.getDimension(R.styleable.RoundShadowProcess_s_textSize, DensityUtils.sp2px(getContext(), 15f));
            mText = a.getString(R.styleable.RoundShadowProcess_s_text);
            if (TextUtils.isEmpty(mText)) {
                mText = "出租率";
            }
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

        if (mShadowBackgroundBitmap != null) {
            canvas.drawBitmap(mShadowBackgroundBitmap, -10, -6, mPaint);
        }
        // 第一步：绘制一个圆环
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setColor(roundColor);
        float cx = (mWidth - mShadowWidth) / 2.0f;
        float cy = (mHeight - mShadowWidth) / 2.0f;
        float radius = mWidth / 2.0f - mStrokeWidth / 2.0f - mShadowWidth / 2.0f;
        canvas.drawCircle(cx, cy, radius, mPaint);

        // 第二步：绘制文字
//        String text = formatFloat(progress / maxProgress * 100) + "%";
        Rect bounds = new Rect();
        mTextPaint.setTextSize(textSize);
        mTextPaint.getTextBounds(mText, 0, mText.length(), bounds);
        canvas.drawText(mText, mWidth / 2 - bounds.width() / 2 - mShadowWidth, mHeight / 2 + bounds.height() / 2, mTextPaint);

        // 第三步：绘制动态进度圆环
        mPaint.setDither(true);
        mPaint.setStrokeJoin(Paint.Join.BEVEL);
//        mPaint.setStrokeCap(Paint.Cap.ROUND); //  设置笔触为圆形

        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setColor(roundProgressColor);
        if (oval == null) {
            oval = new RectF(0 + mStrokeWidth / 2, 0 + mStrokeWidth / 2,
                    (mWidth - mShadowWidth) - mStrokeWidth / 2, (mHeight - mShadowWidth) - mStrokeWidth / 2);
        }

        canvas.drawArc(oval, -90, progress / maxProgress * 360, false, mPaint);


    }

    /**
     * 设置当前显示的进度条
     *
     * @param progress
     */
    public void setProgress(float progress) {
        if (progress > 0) {
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
        mAnimator.addListener(new BaseAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {

                mShadowBackgroundBitmap = scaleImage(BitmapFactory.decodeResource(getResources(), R.drawable.ring_projection), mWidth, mHeight);
                postInvalidate();

            }
        });

        mAnimator.setDuration((long) (targetProgress * 33));
        mAnimator.start();
    }

    /**
     * 按新的宽高缩放图片
     *
     * @param bm
     * @param newWidth
     * @param newHeight
     * @return
     */
    public static Bitmap scaleImage(Bitmap bm, float newWidth, float newHeight) {
        if (bm == null) {
            return null;
        }
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = newWidth / width;
        float scaleHeight = newHeight / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,
                true);
        if (bm != null & !bm.isRecycled()) {
            bm.recycle();
            bm = null;
        }
        return newbm;
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dowX = (int) event.getRawX();
                dowY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                //偏移在20以内 才算是一次点击
                if (Math.abs(dowX - event.getRawX()) < 20 && Math.abs(dowY - event.getRawY()) < 20) {
                    doOnSpecialTypeClick(event);
                    return true;
                }
                break;
        }
        return super.onTouchEvent(event);
    }


    private void doOnSpecialTypeClick(MotionEvent event) {
        mRadius = mWidth / 2.0f;
        float eventX = event.getX();
        float eventY = event.getY();
        double alfa = 0;
        float startArc = 0;
        //点击的位置到圆心距离的平方
        double distance = Math.pow(eventX - mRadius, 2) + Math.pow(eventY - mRadius, 2);
        //判断点击的坐标是否在环内
        if (distance < Math.pow(mRadius, 2) && distance > Math.pow(0.72 * mRadius, 2)) {
            int which = touchOnWhichPart(event);
            switch (which) {
                case PART_ONE:
                    alfa = Math.atan2(eventX - mRadius, mRadius - eventY) * 180 / PI;
                    break;
                case PART_TWO:
                    alfa = Math.atan2(eventY - mRadius, eventX - mRadius) * 180 / PI + 90;
                    break;
                case PART_THREE:
                    alfa = Math.atan2(mRadius - eventX, eventY - mRadius) * 180 / PI + 180;
                    break;
                case PART_FOUR:
                    alfa = Math.atan2(mRadius - eventY, mRadius - eventX) * 180 / PI + 270;
                    break;
            }
            startArc = startArc + progress / maxProgress * 360;
            if (alfa != 0 /*&& alfa < startArc*/) {
//                Toast.makeText(getContext(), "startArc:"+startArc, Toast.LENGTH_SHORT).show();

                if (alfa < startArc) {
                    if (mListener != null) {
                        mListener.onSpecialTypeClick(progress);
                    }
                } else {
                    if (mListener != null) {
                        mListener.onSpecialTypeClick(100f - progress);
                    }
                }
            }
        }
    }

    /**
     * 4 |  1
     * -----|-----
     * 3 |  2
     * 圆被分成四等份，判断点击在园的哪一部分
     */
    private int touchOnWhichPart(MotionEvent event) {
        if (event.getX() > mRadius) {
            if (event.getY() > mRadius) return PART_TWO;
            else return PART_ONE;
        } else {
            if (event.getY() > mRadius) return PART_THREE;
            else return PART_FOUR;
        }
    }

    public interface OnSpecialTypeClickListener {
        void onSpecialTypeClick(float type);
    }


}