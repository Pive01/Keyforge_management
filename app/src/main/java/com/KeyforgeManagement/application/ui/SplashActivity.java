package com.KeyforgeManagement.application.ui;

import android.os.Bundle;
import android.widget.Toast;

import com.KeyforgeManagement.application.R;
import com.KeyforgeManagement.application.data.storage.stats.StatsRepository;
import com.KeyforgeManagement.application.ui.main.MainActivity;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        StatsRepository.refresh(getApplicationContext(), this::openMain, throwable -> failed());
    }

    private void failed() {
        Toast.makeText(this, "There has been an error while loading data", Toast.LENGTH_LONG).show();
        openMain();
    }

    private void openMain() {
        MainActivity.start(this);
    }
}
