package com.Keyforge_management.ui.detail;


import android.graphics.Color;
import android.graphics.Paint;

import com.Keyforge_management.data.model.Stats;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;


public class BarChartImplementer {
    private BarChart chart;
    private Stats statistic;
    private String label;
    private int count = 0;
    private int secondCounter = 0;


    public BarChartImplementer(BarChart chart, Stats toShow, String label) {
        this.chart = chart;
        this.statistic = toShow;
        this.label = label;
    }

    public void createSasBarChart(int Compare) {

        List<BarEntry> values = new ArrayList<>();
        List<Integer> colors = new ArrayList<>();

        statistic.getSas().forEach(item -> {
            values.add(new BarEntry(item.getX(), ((int) item.getY())));
            if (item.getX() == Compare) {
                colors.add(Color.MAGENTA);
            } else
                colors.add(Color.GREEN);
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

    public void createHousesBarChart() {
        List<BarEntry> houseValues = new ArrayList<>();
        int[] Colors = new int[]{Color.parseColor("#FFC107"), Color.RED,
                Color.parseColor("#039BE5"), Color.parseColor("#2E7D32"),
                Color.parseColor("#FBC02D"), Color.parseColor("#37474F"),
                Color.parseColor("#4CAF50"), Color.LTGRAY, Color.CYAN, Color.DKGRAY};
        List<LegendEntry> legendStr = new ArrayList<>();

        statistic.getHouseWinRate().forEach(item -> {
            houseValues.add(new BarEntry(count++, (int) (item.getY() * 100)));
            LegendEntry temp = new LegendEntry();
            temp.label = item.getX();
            temp.formColor = Colors[secondCounter++];
            legendStr.add(temp);
        });
        BarDataSet dataSet = new BarDataSet(houseValues, label);
        dataSet.setColors(Colors);
        dataSet.setDrawValues(false);
        List<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);
        chart.animateY(1000);
        BarData data = new BarData(dataSets);
        chart.setData(data);
        chart.getDescription().setText(label);
        chart.getDescription().setTextSize(16f);
        chart.getDescription().setTextAlign(Paint.Align.RIGHT);
        chart.getLegend().setCustom(legendStr);
        chart.getLegend().setOrientation(Legend.LegendOrientation.VERTICAL);
        chart.getLegend().setDrawInside(false);
        chart.getLegend().setWordWrapEnabled(true);


    }


}
