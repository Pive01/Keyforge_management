package com.KeyforgeManagement.application.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.KeyforgeManagement.application.R;
import com.KeyforgeManagement.application.data.storage.stats.StatsRepository;
import com.KeyforgeManagement.application.ui.main.MainActivity;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        loading = findViewById(R.id.progress_bar_spalsh);
        loading.setVisibility(View.VISIBLE);

        StatsRepository.refresh(getApplicationContext(), stats -> {
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
        MainActivity.start(this);
        loading.setVisibility(View.GONE);
    }
}
