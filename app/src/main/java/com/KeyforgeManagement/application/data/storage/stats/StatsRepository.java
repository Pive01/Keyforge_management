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
    private static final String FILE_NAME = "statsFile";
    private static final int MAX_AGE_MILLIS = 86400000 * 3; // 3 days

    private static Stats STATS;

    public static void refresh(Context context, Runnable onSuccess, Consumer<Throwable> onFailure) {
        if (isStatsExpired(context)) {
            getStatsFromNet(stats -> {
                STATS = stats;
                store(context, stats);
                onSuccess.run();
            }, onFailure);
        } else {
            try {
                STATS = read(context);
                onSuccess.run();
            } catch (IOException e) {
                e.printStackTrace();
                onFailure.accept(e);
            }
        }
    }

    public static Stats get(Context context) {
        if (STATS == null) {
            try {
                STATS = read(context);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return STATS;
    }

    private static boolean isStatsExpired(Context context) {
        long now = currentTimeMillis();
        SharedPreferences sharedPref = getSharedPreferences(context);
        return !sharedPref.contains(SHARED_PREFERENCES_DATE_KEY)
                || (now - sharedPref.getLong(SHARED_PREFERENCES_DATE_KEY, 0)) >= MAX_AGE_MILLIS;
    }

    private static void store(Context context, Stats stats) {
        try {
            FileUtils.write(context, new Gson().toJson(stats), FILE_NAME);
            getSharedPreferences(context)
                    .edit()
                    .putLong(SHARED_PREFERENCES_DATE_KEY, currentTimeMillis())
                    .apply();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Stats read(Context context) throws IOException {
        return new Gson().fromJson(FileUtils.read(context, FILE_NAME), Stats.class);
    }

    private static void getStatsFromNet(Consumer<Stats> onResponse, Consumer<Throwable> onFailure) {
        Api.getStats().enqueue(new Callback<List<GlobalStatistics>>() {
            @Override
            public void onResponse(Call<List<GlobalStatistics>> call, Response<List<GlobalStatistics>> response) {
                assert response.body() != null;
                onResponse.accept(response.body().get(0).getStats());
            }

            @Override
            public void onFailure(Call<List<GlobalStatistics>> call, Throwable t) {
                onFailure.accept(t);
            }
        });
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }
}
