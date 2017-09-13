package com.td.framework.ui.chart.helper;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.td.framework.R;
import com.td.framework.ui.chart.DayAxisValueFormatter;
import com.td.framework.ui.chart.LinearChartMarkerView;
import com.td.framework.ui.chart.MyAxisValueFormatter;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by 江俊超 on 2017/5/8 0008.
 * <p>版本:1.0.0</p>
 * <b>说明<b>线性图帮助类<br/>
 * <li></li>
 */
public class LineChartHelper {
    public static void initLineChart(Context mContext, LineChart mChart, boolean useMarkView, String... bottomLable) {
        mChart.setDoubleTapToZoomEnabled(false);
        // no description text
        mChart.getDescription().setEnabled(false);
        // enable touch gestures
        mChart.setTouchEnabled(true);

        mChart.setDragDecelerationFrictionCoef(1.9f);
        final DecimalFormat mFormat = new DecimalFormat("###########");
        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(true);
        mChart.setHighlightPerDragEnabled(true);
        // mChart.setDrawYLabels(false);
        mChart.setGridBackgroundColor(0x1a1d9ff0);
        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);
        // set an alternative background color
        mChart.setBackgroundColor(Color.WHITE);
        // add data


        // get the legend (only possible after setting data)
        mChart.getLegend().setEnabled(false);
        mChart.setHorizontalScrollBarEnabled(true);

        //底部  start
        IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(mChart, bottomLable);
        XAxis xAxis = mChart.getXAxis();
//        xAxis.setTypeface(mTfLight);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(6, false);
        xAxis.setDrawAxisLine(true);
        xAxis.setAxisLineColor(0xffd6d6d6);
        xAxis.setValueFormatter(xAxisFormatter);
        xAxis.setTextSize(12f);
        xAxis.setTextColor(0xff4d4d4d);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //底部  end


        //左边
        IAxisValueFormatter custom = new MyAxisValueFormatter();
        YAxis leftAxis = mChart.getAxisLeft();
//        leftAxis.setTypeface(mTfLight);
        leftAxis.setLabelCount(6, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(12f);
        leftAxis.setTextSize(12f);
        leftAxis.setGridColor(Color.WHITE);
        leftAxis.setAxisLineColor(0xffd6d6d6);
        leftAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mFormat.format(value);
            }
        });
        leftAxis.setDrawZeroLine(true);
        leftAxis.setZeroLineColor(0xffd6d6d6);
        leftAxis.setTextColor(0xff666666);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        leftAxis.setXOffset(10f);
        //左边 end


//        mChart.setExtraRightOffset(30f);
        mChart.getAxisRight().setEnabled(false);
        //图例  用View来遮挡吧
        mChart.getLegend().setEnabled(true);
        mChart.setClipValuesToContent(true);
        mChart.setExtraRightOffset(10f);


        if (useMarkView) {
            LinearChartMarkerView mv = new LinearChartMarkerView(mContext, R.layout.linear_marker_view);
            mv.setChartView(mChart); // For bounds control
            mChart.setMarker(mv);
        }

    }

    public static void setData(Context mContext, LineChart mChart, LineDataSet.Mode mode, int count, float range, boolean isScal, boolean drawValueText) {


        final DecimalFormat mFormat = new DecimalFormat("###########");

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        float start = 0f;
        //----------------模拟数据
        for (int i = (int) start; i < start + count + 1; i++) {
            float mult = (range + 1);
            float val = (float) (Math.random() * mult);
            if (i == start) {
                //第一个
                yVals1.add(new BarEntry(i + 1, LinearChartMarkerView.GONE_CHART));
            } else if (i == start + count) {
                //最后一个
                yVals1.add(new BarEntry(i + 1, LinearChartMarkerView.GONE_CHART));
            } else {
                // 1 -- 11 个
                yVals1.add(new BarEntry(i + 1, val));
            }
        }
        //----------------模拟数据


        LineDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(yVals1, " ");

            set1.setAxisDependency(YAxis.AxisDependency.RIGHT);
            set1.setColor(0xff3cd1fa);
            set1.setCircleColor(0xff3cd1fa);
            set1.setFormLineWidth(10f);

            set1.setLineWidth(2f);
            set1.setCircleRadius(2.5f);
            set1.setCircleHoleRadius(2f);
            set1.setCircleColorHole(0xff3cd1fa);
            set1.setDrawCircleHole(true);
            if (mode == LineDataSet.Mode.HORIZONTAL_BEZIER) {
                set1.setDrawFilled(true);
                if (Utils.getSDKInt() >= 18) {
                    // fill drawable only supported on api level 18 and above
                    Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.linear_chart_fill);
                    set1.setFillDrawable(drawable);
                } else {
                    set1.setFillColor(0x6624abff);
                }
            }else {
                set1.setDrawFilled(false);
            }
            set1.setMode(mode);

            // create a data object with the datasets
            LineData data = new LineData(set1);
            data.setValueTextColor(0xff1d9ff0);
            data.setValueTextSize(12f);
            data.setDrawValues(drawValueText);
            data.setValueFormatter(new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    if (entry.getX() == 1.0 || entry.getX() == 14.0) {
                        return "";
                    }
                    return mFormat.format(value);
//                    return "";
                }
            });
            // set data
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
