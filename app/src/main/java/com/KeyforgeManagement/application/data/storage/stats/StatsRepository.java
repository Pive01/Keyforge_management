package com.KeyforgeManagement.application.data.storage.stats;

import android.content.Context;
import android.content.SharedPreferences;

import com.KeyforgeManagement.application.common.FileUtils;
import com.KeyforgeManagement.application.data.api.Api;
import com.KeyforgeManagement.application.data.model.Stats;
import com.KeyforgeManagement.application.data.model.wrapperDecksOfKeyforge.GlobalStatistics;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.System.currentTimeMillis;


public class StatsRepository {

    private static final String SHARED_PREFERENCES_NAME = "stats";
    private static final String SHARED_PREFERENCES_DATE_KEY = "statsDate";

    private static Stats statistics;
    private static final int MAX_AGE_MILLIS = 86400000 * 3; // 3 days
    private static final String filename = "statsFile";

    public static void refresh(Context context, Consumer<Stats> refreshComplete) {
        long now = currentTimeMillis();

        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);


        if (!sharedPref.contains(SHARED_PREFERENCES_DATE_KEY)
                || (now - sharedPref.getLong(SHARED_PREFERENCES_DATE_KEY, 0)) >= MAX_AGE_MILLIS) {
            getStatsFromNet(stats -> {
                statistics = stats;
                try {
                    FileUtils.write(context, new Gson().toJson(stats), filename);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                sharedPref.edit()
                        .putLong(SHARED_PREFERENCES_DATE_KEY, currentTimeMillis())
                        .apply();
                refreshComplete.accept(stats);
            });


        } else {
            try {
                statistics = new Gson().fromJson(FileUtils.read(context, filename), Stats.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            refreshComplete.accept(statistics);
        }
    }

    public static Stats get() {
        return statistics;
    }

    private static void getStatsFromNet(Consumer<Stats> onResponse) {
        Api.getStats().enqueue(new Callback<List<GlobalStatistics>>() {
            @Override
            public void onResponse(Call<List<GlobalStatistics>> call, Response<List<GlobalStatistics>> response) {
                assert response.body() != null;
                onResponse.accept(response.body().get(0).getStats());

            }

            @Override
            public void onFailure(Call<List<GlobalStatistics>> call, Throwable t) {
                //TODO handle failed
            }
        });
    }
}
