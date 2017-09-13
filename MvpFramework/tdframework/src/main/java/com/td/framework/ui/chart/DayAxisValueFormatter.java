package com.td.framework.ui.chart;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * Created by philipp on 02/06/16.
 */
public class DayAxisValueFormatter implements IAxisValueFormatter {

    protected String[] mDatas;

    private BarLineChartBase<?> chart;

    public DayAxisValueFormatter(BarLineChartBase<?> chart, String[] data) {
        this.chart = chart;
        this.mDatas = data;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        String monthName = "";
        try {
            monthName = mDatas[((int) value) - 1];
        } catch (Exception e) {
            e.printStackTrace();
        }
//        L.e(value+"   ");
        return monthName;
    }


}
