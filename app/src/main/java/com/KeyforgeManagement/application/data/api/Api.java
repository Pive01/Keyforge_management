package com.KeyforgeManagement.application.data.api;

import com.KeyforgeManagement.application.data.model.adaptation.NewDeckFormat;
import com.KeyforgeManagement.application.data.model.decksOfKeyforgeRequired.RequestBody;
import com.KeyforgeManagement.application.data.model.decksOfKeyforgeRequired.UserInfo;
import com.KeyforgeManagement.application.data.model.decksOfKeyforgeRequired.UserValidator;
import com.KeyforgeManagement.application.data.model.wrapperDecksOfKeyforge.GlobalStatistics;
import com.KeyforgeManagement.application.data.model.wrapperDecksOfKeyforge.ResponseImport;
import com.KeyforgeManagement.application.data.model.wrapperDecksOfKeyforge.SingleDeckReference;
import com.KeyforgeManagement.application.data.model.wrapperMasterVault.Kmvresults;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class Api {

    private static final String BASE_URL_DOK = "https://decksofkeyforge.com/api/";
    private static final String BASE_URL_KMV = "https://www.keyforgegame.com/api/";

    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build();
    private static final KeyforgeMasterVaultApi APIKMV = new Retrofit.Builder()
            .baseUrl(BASE_URL_KMV)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(KeyforgeMasterVaultApi.class);

    private static final DecksOfKeyforgeApi APIDOK = new Retrofit.Builder()
            .baseUrl(BASE_URL_DOK)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DecksOfKeyforgeApi.class);

    public static Call<List<NewDeckFormat>> getDecks(String deckName) {
        return APIDOK.getDecks(deckName);
    }

    public static Call<Kmvresults> getCards(String deckId) {
        return APIKMV.getCards(deckId);
    }

    public static Call<List<GlobalStatistics>> getStats() {
        return APIDOK.getStatistics();
    }

    public static Call<SingleDeckReference> getDeckFromId(String deckId) {
        return APIDOK.getDeckFromId(deckId);
    }

    public static Call<ResponseImport> importDecks(RequestBody req, String auth) {
        return APIDOK.importDecks(req, auth);
    }

    public static Call<Void> getAuthorization(UserValidator usr) {
        return APIDOK.getAuthorization(usr);
    }

    public static Call<Boolean> addDeck(String auth, String deckId) {
        return APIDOK.addDeck(auth, deckId);
    }

    public static Call<UserInfo> getUserName(String auth) {
        return APIDOK.getUserName(auth);
    }

    private Api() {
    }
}
