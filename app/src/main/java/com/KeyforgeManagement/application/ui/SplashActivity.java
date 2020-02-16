package com.KeyforgeManagement.application.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.KeyforgeManagement.application.R;
import com.KeyforgeManagement.application.data.api.Api;
import com.KeyforgeManagement.application.data.model.Stats;
import com.KeyforgeManagement.application.data.model.wrapperDecksOfKeyforge.GlobalStatistics;
import com.KeyforgeManagement.application.ui.main.MainActivity;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {


    ProgressBar loading;
    private Stats statistics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        loading = findViewById(R.id.progress_bar_spalsh);
        loading.setVisibility(View.VISIBLE);

        Api.getStats().enqueue(new Callback<List<GlobalStatistics>>() {
            @Override
            public void onResponse(Call<List<GlobalStatistics>> call, Response<List<GlobalStatistics>> response) {
                assert response.body() != null;
                statistics = response.body().get(0).getStats();
                openMain();

            }

            @Override
            public void onFailure(Call<List<GlobalStatistics>> call, Throwable t) {
                failed();
            }
        });
    }

    private void failed() {
        loading.setVisibility(View.GONE);
        Toast.makeText(this, "There has been an error while loading data", Toast.LENGTH_LONG).show();
    }

    private void openMain() {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("stats", statistics);
        MainActivity.start(this, i);
        loading.setVisibility(View.GONE);

    }
}
