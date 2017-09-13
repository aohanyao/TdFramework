package com.td.framework.ui.chart.helper;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Matrix;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.td.framework.ui.chart.renderer.HorizontalBarChartRoundRenderer;
import com.td.framework.utils.SpannableStringUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by 江俊超 on 2017/5/9 0009.
 * <p>版本:1.0.0</p>
 * <b>说明<b>水平柱状图邦租赁<br/>
 * <li></li>
 */
public class HorizontalBarHelper {
    /**
     * 初始化水平柱状图
     *
     * @param mContext
     * @param mChart
     * @param labelCount
     */
    public static void initHorizontalBar(Context mContext, final HorizontalBarChart mChart, int labelCount) {

        mChart.setDrawBarShadow(false);
        mChart.setDrawBorders(false);
        mChart.setBorderColor(0xffd6d6d6);
        mChart.setDrawValueAboveBar(true);
        mChart.setGridBackgroundColor(Color.WHITE);
        mChart.setDrawGridBackground(true);

        mChart.getDescription().setEnabled(false);
        mChart.setRenderer(new HorizontalBarChartRoundRenderer(mChart, mChart.getAnimator(), mChart.getViewPortHandler(), 5));
        mChart.setPinchZoom(false);




        YAxis yl = mChart.getAxisLeft();
        yl.setEnabled(false);
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(true);
        yl.setAxisLineColor(Color.RED);
        yl.setGridColor(Color.BLUE);
        yl.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        final DecimalFormat mFormat = new DecimalFormat("###########");

        YAxis yr = mChart.getAxisRight();
        yr.setDrawAxisLine(true);
        yr.setAxisLineColor(0xffd6d6d6);
        yr.setDrawGridLines(false);
//        yr.setAxisMaximum(700f);
        yr.setLabelCount(labelCount, false);
        yr.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                //400 415
                //600  610
                if (mChart.getYMax() > value) {
                    return mFormat.format(value);
                }
                return SpannableStringUtils.getBuilder(mFormat.format(value))
                        .append("(个)")
                        .setForegroundColor(0xff999999)
                        .create()
                        .toString();
            }
        });
        yr.setTextColor(0xff666666);
        yr.setTextSize(11f);
        yr.setAxisMinimum(0f);
        mChart.getLegend().setEnabled(false);


        XAxis xl = mChart.getXAxis();
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setAxisLineColor(0xffd6d6d6);
        xl.setTextColor(0x00000000);
        xl.setGridColor(Color.BLACK);
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);

    }

    public static void setData(HorizontalBarChart mChart, int count, float range, boolean isScal, int... colors) {

        float start = 1f;
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        //----------------模拟数据
        for (int i = (int) start; i < start + count + 1; i++) {
            float mult = (range + 1);
            float val = (float) (Math.random() * mult);
            yVals1.add(new BarEntry(i, val));
        }
        //----------------模拟数据

        BarDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "DataSet 1");
            set1.setColors(colors);

            set1.setDrawIcons(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(12f);
            data.setBarWidth(0.6f);
            data.setValueTextColor(0xff1d9ff0);
            mChart.setData(data);
        }

        if (isScal) {
            mChart.invalidate();
            Matrix mMatrix = new Matrix();
            mMatrix.postScale(2f, 1f);
            mChart.getViewPortHandler().refresh(mMatrix, mChart, false);
        }
        mChart.animateY(1500);
    }
}
