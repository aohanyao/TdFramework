package com.td.framework.ui.chart.helper;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Matrix;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.td.framework.ui.chart.renderer.BarChartRoundRenderer;
import com.td.framework.ui.chart.DayAxisValueFormatter;
import com.td.framework.ui.chart.MyAxisValueFormatter;
import com.td.framework.utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by 江俊超 on 2017/5/5 0005.
 * <p>版本:1.0.0</p>
 * <b>说明<b>柱状图帮助类<br/>
 * <li></li>
 */
public class BarChartHelper {
    /**
     * 初始化
     * @param mContext
     * @param mChart
     * @param leftLabelCount
     * @param bottomLable
     */
    public static void initBarChart(Context mContext, BarChart mChart, int leftLabelCount, String... bottomLable) {
        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);

        mChart.getDescription().setEnabled(false);
        mChart.setNoDataText("暂时没有相关数据");
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(true);
//        mChart.setScal
        mChart.setDrawGridBackground(true);
        // mChart.setDrawYLabels(false);
        mChart.setGridBackgroundColor(0x1a1d9ff0);        // mChart.setDrawYLabels(false);

        IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(mChart, bottomLable);
        //设置圆角
        mChart.setRenderer(new BarChartRoundRenderer(mChart, mChart.getAnimator(), mChart.getViewPortHandler(), DensityUtils.dp2px(mContext, 2)));
        //---------------------------底部标签文字
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setTypeface(mTfLight);
        xAxis.setDrawGridLines(false);
//        xAxis.setGridColor(Color.WHITE);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(10);
        xAxis.setDrawAxisLine(true);
        xAxis.setYOffset(3);
        //设置值
        xAxis.setValueFormatter(xAxisFormatter);
        xAxis.setTextSize(12);
        xAxis.setTextColor(0xff4d4d4d);
        //旋转角度
        xAxis.setLabelRotationAngle(0f);

        //---------------------------底部标签文字


        //---------------------------左边的文字
        IAxisValueFormatter custom = new MyAxisValueFormatter();
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setLabelCount(leftLabelCount, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(10f);
        leftAxis.setGridColor(Color.WHITE);
        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setTextColor(0xff666666);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        //---------------------------左边的文字


        //---------------------------右边的文字
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);
        //---------------------------右边的文字


        mChart.getLegend().setEnabled(false);
        //点击后 显示的视图
//        XYMarkerView mv = new XYMarkerView(mActivity, xAxisFormatter);
//        mv.setChartView(mChart); // For bounds control
//        mChart.setMarker(mv); // Set the marker to the chart
        //当为true时,放大图
        // 为了使 柱状图成为可滑动的,将水平方向 放大 2.5倍

    }

    public static void setData(BarChart mChart, int count, float range, boolean isScal, int... colors) {
        float start = 1f;
        Random mRandom = new Random();
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        //----------------模拟数据
        for (int i = (int) start; i < start + count + 1; i++) {
            float val = mRandom.nextFloat() * range;
            yVals1.add(new BarEntry(i, val));
        }
        //----------------模拟数据

        BarDataSet barDataSet;

        //通知数据更改
        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            barDataSet = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            barDataSet.setValues(yVals1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            //重新创建数据
            barDataSet = new BarDataSet(yVals1, "");

            barDataSet.setDrawIcons(false);
            //设置柱状图颜色
            barDataSet.setColors(colors);

//            barDataSet.set
            //值  文本颜色
            List<Integer> mColors = new ArrayList<>();
            for (int i = 0; i < colors.length; i++) {
                mColors.add(colors[i]);

            }
            barDataSet.setValueTextColors(mColors);
            //值  字体大小
            barDataSet.setValueTextSize(12);

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(barDataSet);

            BarData barData = new BarData(dataSets);
            barData.setValueTextSize(12f);
//            barData.setValueTypeface(mTfLight);
            barData.setBarWidth(0.4f);
            mChart.setData(barData);
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
