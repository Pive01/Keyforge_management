package com.KeyforgeManagement.application.data.api;

import com.KeyforgeManagement.application.data.model.Deck;
import com.KeyforgeManagement.application.data.model.wrapperDecksOfKeyforge.GlobalStatistics;

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
