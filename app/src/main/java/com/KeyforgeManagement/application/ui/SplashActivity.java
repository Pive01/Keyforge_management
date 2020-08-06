package com.KeyforgeManagement.application.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.KeyforgeManagement.application.R;
import com.KeyforgeManagement.application.data.model.Stats;
import com.KeyforgeManagement.application.data.storage.stats.StatsRepository;
import com.KeyforgeManagement.application.ui.main.MainActivity;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private ProgressBar loading;
    private Stats statistics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImageView img = findViewById(R.id.imageView);
        img.setImageResource(R.drawable.logosplash);
        loading = findViewById(R.id.progress_bar_spalsh);
        loading.setVisibility(View.VISIBLE);

        StatsRepository.refresh(getApplicationContext(), stats -> {
            statistics = stats;
            if (stats == null)
                failed();
            else
                openMain();
        });

    }

    private void failed() {
        loading.setVisibility(View.GONE);
        Toast.makeText(this, "There has been an error while loading data", Toast.LENGTH_LONG).show();
        openMain();
    }

    private void openMain() {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("stats", statistics);
        MainActivity.start(this, i);
        loading.setVisibility(View.GONE);
    }
}
