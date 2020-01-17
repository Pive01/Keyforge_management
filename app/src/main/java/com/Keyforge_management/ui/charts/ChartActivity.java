package com.Keyforge_management.ui.charts;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.Keyforge_management.R;
import com.Keyforge_management.data.model.Stats;
import com.Keyforge_management.data.storage.typeConverters.HouseArrayTypeConverter;
import com.github.mikephil.charting.charts.BarChart;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ChartActivity extends AppCompatActivity {

    private static Stats statistic;

    public static void start(Context context, Intent i) {
        context.startActivity(new Intent(context, ChartActivity.class));
        statistic = (Stats) i.getSerializableExtra("stats");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        Toolbar infoToolbar = findViewById(R.id.charts_toolbar);
        infoToolbar.setTitle("Charts");

        setSupportActionBar(infoToolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        BarChart chart = findViewById(R.id.houses_chart);
        BarChartImplementer chartImplementer = new BarChartImplementer(chart, statistic,
                "Houses Win Rates");

        List<Bitmap> imageList = new ArrayList<>();
        statistic.getHouseWinRate().forEach(item -> {
            if (item.getX().toUpperCase().equals("STARALLIANCE"))
                item.setX("STAR_ALLIANCE");
            imageList.add(BitmapFactory.decodeResource(getResources(), HouseArrayTypeConverter.fromSingleString(item.getX().toUpperCase()).getImageId()));
        });
        chartImplementer.createHousesBarChart(getApplicationContext(), imageList);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
