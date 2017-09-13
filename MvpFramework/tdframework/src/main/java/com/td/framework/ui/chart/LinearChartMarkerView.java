package com.td.framework.ui.chart;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.td.framework.R;

import java.text.DecimalFormat;

/**
 * Custom implementation of the MarkerView.
 *
 * @author Philipp Jahoda
 */
public class LinearChartMarkerView extends MarkerView {
    public static final int GONE_CHART=200;
    private TextView tvContent;
    private final DecimalFormat mFormat;

    public LinearChartMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        mFormat = new DecimalFormat("###########");
        tvContent = (TextView) findViewById(R.id.tvContent);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {


        tvContent.setText(mFormat.format(e.getY()));

        super.refreshContent(e, highlight);
        findViewById(R.id.rl_root).setVisibility(e.getY() == GONE_CHART ? GONE : VISIBLE);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
