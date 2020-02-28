package com.KeyforgeManagement.application.data.api;

import com.KeyforgeManagement.application.data.model.wrapperMasterVault.Kmvresults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

interface KeyforgeMasterVaultApi {

    @GET("decks/{keyforgeId}/?links=cards")
    Call<Kmvresults> getCards(@Path("keyforgeId") String deckId);
}
