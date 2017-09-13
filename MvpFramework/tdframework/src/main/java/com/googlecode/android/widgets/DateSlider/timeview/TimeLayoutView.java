package com.googlecode.android.widgets.DateSlider.timeview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.googlecode.android.widgets.DateSlider.TimeObject;
import com.td.framework.R;

/**
 * This is a more complex implementation of the TimeView consisting of a LinearLayout with
 * two TimeViews. This allows primary text and sub-text, such as the name of the day
 * and the day of the month. This class expects the text that it is passed via
 * {@link #setVals(TimeObject)} or {@link #setVals(TimeView)} to contian the primary
 * string followed by a space and then the secondary string.
 */
public class TimeLayoutView extends RelativeLayout implements TimeView {
    private static String TAG = "TimeLayoutView";

    protected long endTime, startTime;
    protected String text;
    protected boolean isCenter = false, isOutOfBounds = false;
    protected TextView topView, bottomView, centerView;
    protected View indicatorView;

    protected int normalColor = 0xFF666666;
    protected int outBoundColor = 0xFFaaaaaa;
    //    protected int selectColor = 0xFFff8403;
    protected int selectColor = Color.WHITE;
    protected int borderColor = 0xFFd6d6d6;
    protected int borderWidth = 1;
    protected int indicatorHeight = 5;
    protected int shadowWidth = 10;
    private float density;

    /**
     * constructor
     *
     * @param context
     * @param isCenterView   true if the element is the centered view in the ScrollLayout
     * @param centerTextSize text size of the top TextView in dps
     * @param bottomTextSize text size of the bottom TextView in dps
     * @param lineHeight     LineHeight of the top TextView
     */
    public TimeLayoutView(Context context, boolean isCenterView, boolean isCenterLeftView, boolean isCenterRightView, int topTextSize, int centerTextSize, int bottomTextSize, float lineHeight) {
        super(context);
        density = getContext().getResources().getDisplayMetrics().density;
        borderWidth = (int) (borderWidth * density);
        indicatorHeight = (int) (indicatorHeight * density);
        shadowWidth = (int) (shadowWidth * density);
        setupView(context, isCenterView, isCenterLeftView, isCenterRightView, topTextSize, centerTextSize, bottomTextSize, lineHeight);
    }

    public TimeLayoutView(Context context, boolean isCenterView, boolean isCenterLeftView, boolean isCenterRightView, int topTextSize, int centerTextSize, int bottomTextSize, float lineHeight, int selectColor) {
        super(context);
        this.selectColor = selectColor;
        density = getContext().getResources().getDisplayMetrics().density;
        borderWidth = (int) (borderWidth * density);
        indicatorHeight = (int) (indicatorHeight * density);
        shadowWidth = (int) (shadowWidth * density);
        setupView(context, isCenterView, isCenterLeftView, isCenterRightView, topTextSize, centerTextSize, bottomTextSize, lineHeight);
    }

    /**
     * Setting up the top TextView and bottom TextVew
     *
     * @param context
     * @param isCenterView   true if the element is the centered view in the ScrollLayout
     * @param centerTextSize text size of the top TextView in dps
     * @param bottomTextSize text size of the bottom TextView in dps
     * @param lineHeight     LineHeight of the top TextView
     */
    protected void setupView(Context context, boolean isCenterView,
                             boolean isCenterLeftView,
                             boolean isCenterRightView,
                             int topTextSize,
                             int centerTextSize,
                             int bottomTextSize,
                             float lineHeight) {
//        setOrientation(HORIZONTAL);
        //设置高度
        int width = (int) (62.5f * context.getResources().getDisplayMetrics().density);
        int height = (int) (40f * context.getResources().getDisplayMetrics().density);
        LinearLayout linearLayout = new LinearLayout(context);
        LayoutParams linearLp = new LayoutParams(width, height);

        linearLayout.setLayoutParams(linearLp);
        linearLp.addRule(RelativeLayout.CENTER_IN_PARENT);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER);

        centerView = new TextView(context);
        centerView.setGravity(Gravity.CENTER /*| Gravity.TOP*/);
        centerView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, centerTextSize);
        topView = new TextView(context);
        topView.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        topView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, topTextSize);
        bottomView = new TextView(context);
        bottomView.setGravity(Gravity.CENTER);
        bottomView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, bottomTextSize);

        centerView.setLineSpacing(0, lineHeight);

        LinearLayout.LayoutParams indicatorLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, indicatorHeight);
        indicatorView = new View(context);
        indicatorView.setBackgroundColor(selectColor);
        indicatorView.setLayoutParams(indicatorLp);
        indicatorView.setVisibility(View.GONE);


        if (isCenterView) {
            isCenter = true;
            centerView.setTypeface(Typeface.DEFAULT);
            centerView.setTextColor(selectColor);
            topView.setTypeface(Typeface.DEFAULT_BOLD);
            topView.setTextColor(selectColor);
            bottomView.setTypeface(Typeface.DEFAULT_BOLD);
            bottomView.setTextColor(selectColor);
//            topView.setPadding(0, 0, (int) (4 * density), 0);
            topView.setPadding(0, 0, 0, 0);
            //底部边框的颜色
//            indicatorView.setVisibility(View.VISIBLE);
            indicatorView.setVisibility(View.GONE);
            bottomView.setPadding(0, 0, 0, 0);
//            bottomView.setPadding(0, 5, 0, 0);
            centerView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, centerTextSize);

            setBackgroundColor(0xff4b77fa);
        } else {
            //topView.setPadding(0, 5, (int) (4 * density), 0);
            // bottomView.setPadding(0, 0, 0, 0);
            centerView.setTextColor(normalColor);
            topView.setPadding(0, 0, 0, 0);
            bottomView.setPadding(0, 0, 0, 0);
            topView.setTextColor(normalColor);
            bottomView.setTextColor(normalColor);
            setBackgroundColor(Color.WHITE);
        }
        // linearLayout.addView(topView);
        linearLayout.addView(centerView);
        //linearLayout.addView(bottomView);

        linearLayout.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        int linearLayoutHeight = linearLayout.getMeasuredHeight();
        int linearLayoutWidth = linearLayout.getMeasuredWidth();

        LayoutParams topBorderLp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, borderWidth);
        View topBorder = new View(context);
        topBorder.setBackgroundColor(borderColor);
        topBorderLp.addRule(RelativeLayout.ALIGN_PARENT_LEFT | RelativeLayout.ALIGN_PARENT_TOP);
        topBorder.setLayoutParams(topBorderLp);

        LinearLayout.LayoutParams bottomBorderLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, borderWidth);
        View bottomBorder = new View(context);
        bottomBorder.setBackgroundColor(borderColor);
        bottomBorder.setLayoutParams(bottomBorderLp);


        View leftBorder = new View(context);
        leftBorder.setBackgroundColor(borderColor);
        LayoutParams leftLp = new LayoutParams(borderWidth, linearLayoutHeight);
        leftBorder.setLayoutParams(leftLp);

        View leftShadow = new View(context);
        View rightShadow = new View(context);
        LayoutParams leftShadowLp = new LayoutParams(shadowWidth, linearLayoutHeight);
        LayoutParams rightShadowLp = new LayoutParams(shadowWidth, linearLayoutHeight);
        leftShadow.setLayoutParams(leftShadowLp);
        rightShadow.setLayoutParams(rightShadowLp);
        rightShadowLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        Drawable leftShadowDrawable = getContext().getResources().getDrawable(R.drawable.left_shadow);
        Drawable rightShadowDrawable = getContext().getResources().getDrawable(R.drawable.right_shadow);
        leftShadow.setBackgroundDrawable(leftShadowDrawable);
        rightShadow.setBackgroundDrawable(rightShadowDrawable);

        // addView(topBorder);
//        addView(leftBorder);

//        linearLayout.addView(bottomBorder);
        linearLayout.addView(indicatorView);
        addView(leftShadow);
        addView(linearLayout);
        addView(rightShadow);

        leftShadow.setVisibility(View.GONE);
        rightShadow.setVisibility(View.GONE);
        if (isCenterLeftView) {
//            rightShadow.setVisibility(View.VISIBLE);
//            leftShadow.setVisibility(View.GONE);
        }
        if (isCenterRightView) {
//            rightShadow.setVisibility(View.GONE);
//            leftShadow.setVisibility(View.VISIBLE);
        }
    }


    public void setVals(TimeObject to) {
        text = to.text.toString();
        setText();
        this.startTime = to.startTime;
        this.endTime = to.endTime;
    }


    public void setVals(TimeView other) {
        text = other.getTimeText().toString();
        setText();
        startTime = other.getStartTime();
        endTime = other.getEndTime();
    }

    /**
     * sets the TextView texts by splitting the text into two
     */
    protected void setText() {
        String[] splitTime = text.split(" ");
//        centerView.setText(splitTime[0]);
//        topView.setText(splitTime[1]);
//        if (splitTime.length >= 3) {
//            bottomView.setText(splitTime[2]);
//        }
        // bottomView.setText(splitTime[0]);
        // topView.setText(splitTime[1]);
        if (splitTime.length >= 3) {
            centerView.setText(splitTime[2]);
        }
    }


    public String getTimeText() {
        return text;
    }


    public long getStartTime() {
        return startTime;
    }


    public long getEndTime() {
        return endTime;
    }

    public boolean isOutOfBounds() {
        return isOutOfBounds;
    }

    public void setOutOfBounds(boolean outOfBounds) {
        if (outOfBounds && !isOutOfBounds) {
            centerView.setTextColor(outBoundColor);
            topView.setTextColor(outBoundColor);
            bottomView.setTextColor(outBoundColor);
        } else if (!outOfBounds && isOutOfBounds) {
            centerView.setTextColor(outBoundColor);
            topView.setTextColor(outBoundColor);
            bottomView.setTextColor(outBoundColor);
        }
        isOutOfBounds = outOfBounds;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }
}