package com.googlecode.android.widgets.DateSlider.labeler;

import com.googlecode.android.widgets.DateSlider.TimeObject;

import java.util.Calendar;

/**
 * A Labeler that displays days
 */
public class DayLabeler extends Labeler {
    private  String mFormatString;

    public DayLabeler(String formatString) {//%td %ta %tb
        super(70, 40);
        mFormatString = formatString;
    }

    @Override
    public TimeObject add(long time, int val) {
        return timeObjectfromCalendar(Util.addDays(time, val));
    }

    @Override
    protected TimeObject timeObjectfromCalendar(Calendar c) {
        return Util.getDay(c, mFormatString);
    }
}