package com.Keyforge_management.data.api;

import com.Keyforge_management.data.model.Deck;
import com.Keyforge_management.data.model.wrapper.Kmvresults;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class Api {

    private static final String BASE_URL_DOK = "https://decksofkeyforge.com/api/";
    private static final String BASE_URL_KMV = "https://www.keyforgegame.com/api/";

    private static final KeyforgeMasterVaultApi APIKMV = new Retrofit.Builder()
            .baseUrl(BASE_URL_KMV)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(KeyforgeMasterVaultApi.class);

    private static final DecksOfKeyforgeApi APIDOK = new Retrofit.Builder()
            .baseUrl(BASE_URL_DOK)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DecksOfKeyforgeApi.class);

    public static Call<List<Deck>> getDecks(String deckName) {
        return APIDOK.getDecks(deckName);
    }

    public static Call<Kmvresults> getCards(String deckId) {
        return APIKMV.getCards(deckId);
    }

    private Api() {
    }
}
