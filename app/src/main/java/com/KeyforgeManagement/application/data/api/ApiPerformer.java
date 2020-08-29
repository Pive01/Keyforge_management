package com.KeyforgeManagement.application.data.api;

import com.KeyforgeManagement.application.data.model.adaptation.NewDeckFormat;
import com.KeyforgeManagement.application.data.model.wrapperDecksOfKeyforge.SingleDeckReference;

import java.util.List;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiPerformer {
    private ApiPerformer(){}

    public static void getDecksFromName(Consumer<List<NewDeckFormat>> deckList,
                                        Consumer<Throwable> failure,String name){
        Api.getDecks(name).enqueue(new Callback<List<NewDeckFormat>>() {
            @Override
            public void onResponse(Call<List<NewDeckFormat>> call, Response<List<NewDeckFormat>> response) {
                deckList.accept(response.body());
            }
            @Override
            public void onFailure(Call<List<NewDeckFormat>> call, Throwable t) {
               failure.accept(t);
            }
        });
    }

    public static void getDecksFromId(Consumer<SingleDeckReference> deck,
                                      Consumer<Throwable> failure, String deckId){
        Api.getDeckFromId(deckId).enqueue(new Callback<SingleDeckReference>() {
            @Override
            public void onResponse(Call<SingleDeckReference> call, Response<SingleDeckReference> response) {
                deck.accept(response.body());
            }
            @Override
            public void onFailure(Call<SingleDeckReference> call, Throwable t) {
                failure.accept(t);
            }
        });
    }
}
