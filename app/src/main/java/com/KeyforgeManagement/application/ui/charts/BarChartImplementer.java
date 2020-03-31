package com.KeyforgeManagement.application.ui.charts;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;

import com.KeyforgeManagement.application.data.model.Stats;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;



public class BarChartImplementer {
    private final BarChart chart;
    private final Stats statistic;
    private final String label;
    private int count = 0;

    public BarChartImplementer(BarChart chart, Stats toShow, String label) {
        this.chart = chart;
        this.statistic = toShow;
        this.label = label;
    }

    public void createSasBarChart(int Compare) {
        if (statistic == null)
            return;

        List<BarEntry> values = new ArrayList<>();
        List<Integer> colors = new ArrayList<>();

        statistic.getSas().forEach(item -> {
            values.add(new BarEntry(item.getX(), ((int) item.getY())));
            if (item.getX() == Compare) {
                colors.add(Color.parseColor("#ffc400"));
            } else
                colors.add(Color.parseColor("#80DEEA"));
        });


        BarDataSet bardataset = new BarDataSet(values, label);
        bardataset.setColors(colors);
        bardataset.setDrawValues(false);
        List<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(bardataset);
        chart.animateY(1000);
        BarData data = new BarData(dataSets);
        data.setBarWidth(1f);
        chart.setData(data);
        chart.getLegend().setEnabled(false);

        chart.getDescription().setText(label);
        chart.getDescription().setTextSize(16f);
        chart.getDescription().setTextAlign(Paint.Align.RIGHT);


    }

    void createHousesBarChart(Context context, List<Bitmap> imageList) {
        if (statistic == null)
            return;
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(7);
        xAxis.setDrawLabels(false);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setDrawGridLines(false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setEnabled(false);


        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);
        chart.getDescription().setEnabled(false);
        chart.setPinchZoom(false);
        chart.setDrawGridBackground(false);

        chart.getAxisRight().setEnabled(false);
        List<BarEntry> houseValues = new ArrayList<>();
        int Colors = Color.parseColor("#ffc400");


        statistic.getHouseWinRate().forEach(item -> {

            houseValues.add(new BarEntry(count++, (int) ((item.getY() * 100) - 4800)));
            LegendEntry temp = new LegendEntry();
            temp.label = item.getX();
        });
        BarDataSet dataSet = new BarDataSet(houseValues, "");
        dataSet.setColor(Colors);
        dataSet.setDrawValues(false);
        List<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);
        chart.animateY(2000);
        BarData data = new BarData(dataSets);
        chart.setData(data);
        chart.getDescription().setText(label);
        chart.getDescription().setTextSize(16f);
        chart.getDescription().setTextAlign(Paint.Align.RIGHT);

        chart.getLegend().setEnabled(false);
        chart.setScaleEnabled(false);
        chart.setRenderer(new BarChartCustomRenderer(chart, chart.getAnimator(), chart.getViewPortHandler(), imageList, context));
        chart.setExtraOffsets(0, 0, 0, 30);

    }

}
