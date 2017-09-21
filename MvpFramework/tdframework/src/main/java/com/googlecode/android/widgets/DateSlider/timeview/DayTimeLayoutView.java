package com.googlecode.android.widgets.DateSlider.timeview;

import java.util.Calendar;

import android.content.Context;

import com.googlecode.android.widgets.DateSlider.TimeObject;

/**
 * This is a subclass of the TimeLayoutView that represents a day.
 */
public class DayTimeLayoutView extends TimeLayoutView {

    protected boolean isSunday = false;

    /**
     * Constructor
     *
     * @param context
     * @param isCenterView   true if the element is the centered view in the ScrollLayout
     * @param topTextSize text size of the top TextView in dps
     * @param centerTextSize text size of the center TextView in dps
     * @param bottomTextSize text size of the bottom TextView in dps
     * @param lineHeight     LineHeight of the top TextView
     */
    public DayTimeLayoutView(Context context, boolean isCenterView, boolean isCenterLeftView, boolean isCenterRightView,
                             int topTextSize, int centerTextSize, int bottomTextSize, float lineHeight) {
        super(context, isCenterView, isCenterLeftView, isCenterRightView, topTextSize, centerTextSize, bottomTextSize, lineHeight);
    }

    public DayTimeLayoutView(Context context, boolean isCenterView, boolean isCenterLeftView, boolean isCenterRightView,
                             int topTextSize, int centerTextSize, int bottomTextSize, float lineHeight, int selectColor) {
        super(context, isCenterView, isCenterLeftView, isCenterRightView, topTextSize, centerTextSize, bottomTextSize, lineHeight, selectColor);
    }
    @Override
    public void setVals(TimeObject to) {
        super.setVals(to);
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(to.endTime);
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY && !isSunday) {
            isSunday = true;
            colorMeSunday();
        } else if (isSunday && c.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            isSunday = false;
            colorMeWorkday();
        }
    }

    /**
     * this method is called when the current View takes a Sunday as time unit
     */
    protected void colorMeSunday() {
        if (isOutOfBounds) return;
        if (isCenter) {
            bottomView.setTextColor(selectColor);
            topView.setTextColor(selectColor);
            centerView.setTextColor(selectColor);
        } else {
            bottomView.setTextColor(normalColor);
            topView.setTextColor(normalColor);
            centerView.setTextColor(normalColor);
        }
    }


    /**
     * this method is called when the current View takes no Sunday as time unit
     */
    protected void colorMeWorkday() {
        if (isOutOfBounds) return;
        if (isCenter) {
            topView.setTextColor(selectColor);
            bottomView.setTextColor(selectColor);
            centerView.setTextColor(selectColor);
        } else {
            topView.setTextColor(normalColor);
            bottomView.setTextColor(normalColor);
            centerView.setTextColor(normalColor);
        }
    }

    @Override
    public void setVals(TimeView other) {
        super.setVals(other);
        DayTimeLayoutView otherDay = (DayTimeLayoutView) other;
        if (otherDay.isSunday && !isSunday) {
            isSunday = true;
            colorMeSunday();
        } else if (isSunday && !otherDay.isSunday) {
            isSunday = false;
            colorMeWorkday();
        }
    }

}