package com.Keyforge_management.decksofkeyforge;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class Api {

    private static final String BASE_URL = "https://decksofkeyforge.com/api/";
    private static final DecksOfKeyforgeApi API = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DecksOfKeyforgeApi.class);

    public static Call<List<Deck>> getDecks(String deckName) {
        return API.getDecks(deckName);
    }

    private Api() {
    }
}
