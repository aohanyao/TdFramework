package com.td.framework.ui.chart;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.td.framework.R;
import com.td.framework.utils.DensityUtils;

import java.text.DecimalFormat;

/**
 * Created by 江俊超 on 2017/4/26 0026.
 * <p>版本:1.0.0</p>
 * <b>说明<b><br/>
 * <li></li>
 * //TODO 中心点 发散 阴影
 */
public class HorizontalChart extends View {

    private float mWidth;
    private float mHeight;
    private float mPercentageValue;
    private String mPercentageText = "100%";
    private Paint mPaint;
    private LinearGradient mLinearGradient;
    private static final int TEXT_MARGIN_LEFT = 26;
    private int mShadowHeight = 5;
    private Bitmap mShadowBitmap;
    private float mPercentageWidth;
    private Bitmap mShadowBackgroundBitmap;
    private final int DEFAULT_TEXT_SIZE = 12;
    private int mTextSize;
    private final int DEFAULT_TEXT_COLOR = 0xff1d9ff0;
    private int mTextColor;

    public HorizontalChart(Context context) {
        this(context, null);
    }

    public HorizontalChart(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mShadowHeight = DensityUtils.dp2px(context, 5);


        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HorizontalChart);
        mTextSize = typedArray.getInteger(R.styleable.HorizontalChart_chart_text_size, DEFAULT_TEXT_SIZE);
        mTextColor = typedArray.getColor(R.styleable.HorizontalChart_chart_text_color, DEFAULT_TEXT_COLOR);
        typedArray.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h - getPaddingBottom();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //略缩bitmap，做成背景

        //绘制阴影背景
        if (mPercentageWidth > 30) {
            if (mShadowBitmap == null || mShadowBitmap.isRecycled()) {
                mShadowBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.horizontal_chart_shadow);
            }
            try {
                mShadowBackgroundBitmap.recycle();
            } catch (Exception e) {

            }
            mShadowBackgroundBitmap = scaleImage(mShadowBitmap, mPercentageWidth /*+ DensityUtils.sp2px(getContext(), 2)*/, mHeight);
            mPaint.setAlpha(200);
            canvas.drawBitmap(mShadowBackgroundBitmap, 0, 0, mPaint);
        }

        mPaint.setAlpha(255);
        mLinearGradient = new LinearGradient(0, 0, mPercentageWidth, mHeight - mShadowHeight, 0xff1d9eef, 0xff3cd1fb, Shader.TileMode.CLAMP);
//        }
        mPaint.setShader(mLinearGradient);
        RectF mRect = new RectF(0, 0, (int) mPercentageWidth, (int) mHeight - mShadowHeight);

        canvas.drawRoundRect(mRect, 5, 5, mPaint);

        mPaint.setShader(null);
        mPaint.setColor(mTextColor);
        mPaint.setTextSize(DensityUtils.sp2px(getContext(), mTextSize));

        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        int baseline = (int) ((mRect.bottom + mRect.top - fontMetrics.bottom - fontMetrics.top) / 2);
        // 下面这行是实现水平居中，drawText对应改为传入targetRect.centerX()
        mPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(mPercentageText, mPercentageWidth + DensityUtils.dp2px(getContext(), TEXT_MARGIN_LEFT), baseline, mPaint);
    }

    //TODO 这里的数据应该是有问题的
    public void setData(final float percentage) {
        this.mPercentageValue = percentage;
        this.mPercentageText = String.valueOf(formatFloat(percentage * 100));

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1).setDuration((long) (percentage * 100 * 33));
//        valueAnimator.setInterpolator(new AnticipateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float v = (float) animation.getAnimatedValue();
                mPercentageWidth = mWidth * 0.8f * mPercentageValue * v;
                mPercentageText = String.valueOf(formatFloat(percentage * 100 * v) + "%");
                invalidate();

            }
        });
        valueAnimator.start();

    }

    private String formatFloat(float scale) {
        DecimalFormat fnum = new DecimalFormat("##0.0");
        return fnum.format(scale);
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

}
