package com.Keyforge_management.data.api;

import com.Keyforge_management.data.model.Deck;
import com.Keyforge_management.data.model.wrapperDecksOfKeyforge.GlobalStatistics;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DecksOfKeyforgeApi {

    @GET("decks/by-name/{name}")
    Call<List<Deck>> getDecks(@Path("name") String deckName);

    @GET("stats")
    Call<List<GlobalStatistics>> getStatistics();
}
