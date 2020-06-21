package com.KeyforgeManagement.application.data.api;

import com.KeyforgeManagement.application.data.model.adaptation.NewDeckFormat;
import com.KeyforgeManagement.application.data.model.decksOfKeyforgeRequired.RequestBody;
import com.KeyforgeManagement.application.data.model.decksOfKeyforgeRequired.UserInfo;
import com.KeyforgeManagement.application.data.model.decksOfKeyforgeRequired.UserValidator;
import com.KeyforgeManagement.application.data.model.wrapperDecksOfKeyforge.GlobalStatistics;
import com.KeyforgeManagement.application.data.model.wrapperDecksOfKeyforge.ResponseImport;
import com.KeyforgeManagement.application.data.model.wrapperDecksOfKeyforge.SingleDeckReference;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

interface DecksOfKeyforgeApi {

    @GET("decks/by-name/{name}")
    Call<List<NewDeckFormat>> getDecks(@Path("name") String deckName);

    @GET("stats")
    Call<List<GlobalStatistics>> getStatistics();

    @GET("decks/with-synergies/{id}")
    Call<SingleDeckReference> getDeckFromId(@Path("id") String deckId);

    @Headers({"timezone: 60"})
    @POST("decks/filter")
    Call<ResponseImport> importDecks(@Body RequestBody body, @Header("authorization") String auth);

    @POST("users/login")
    Call<Void> getAuthorization(@Body UserValidator usr);

    @POST("decks/{id}/import-and-add")
    Call<Void> addDeck(@Header("authorization") String auth, @Path("id") String deckId);

    @Headers({"timezone: 60"})
    @GET("users/secured/your-user")
    Call<UserInfo> getUserName(@Header("authorization") String auth);
}
