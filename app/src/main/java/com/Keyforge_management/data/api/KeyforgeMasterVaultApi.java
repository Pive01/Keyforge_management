package com.Keyforge_management.data.api;

import com.Keyforge_management.data.model.wrapper.Kmvresults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface KeyforgeMasterVaultApi {
    @GET("decks/{keyforgeId}/?links=cards")
    Call<Kmvresults> getCards(@Path("keyforgeId") String deckId);
}
