package com.KeyforgeManagement.application.data.storage.DeckWithCards;

import android.content.Context;

import com.KeyforgeManagement.application.data.model.CardsDeckRef;
import com.KeyforgeManagement.application.data.storage.DecksDatabase;

import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Consumer;


public class DeckCardRepository {
    private final DeckWithCardsDao deckWithCardsDao;

    public DeckCardRepository(Context context) {
        DecksDatabase db = DecksDatabase.getDatabase(context);
        deckWithCardsDao = db.getCardDeckDao();
    }

    public void insertBulk(Collection<CardsDeckRef> collection, Consumer<Collection<CardsDeckRef>> callback) {
        Future<?> future = DecksDatabase.databaseWriteExecutor.submit(() -> deckWithCardsDao.bulkAdd(collection));
        try {
            future.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        callback.accept(collection);
    }

}
