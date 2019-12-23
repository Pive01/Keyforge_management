package com.Keyforge_management.decksofkeyforge;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiResponse {

    @GET("{name}")
    Call<List<Deck>> getData(@Path("name") String deckName);
}
