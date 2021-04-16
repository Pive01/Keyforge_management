package com.KeyforgeManagement.application.data.storage.Card;

import android.content.Context;

import com.KeyforgeManagement.application.data.model.Card;
import com.KeyforgeManagement.application.data.storage.DecksDatabase;

import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public class CardRepository {
    private final CardDao cardDao;

    public CardRepository(Context context) {
        cardDao = DecksDatabase.getDatabase(context).getCardDao();
    }

    public void insertBulk(Collection<Card> collection, Consumer<Collection<Card>> callback) {
        Future<?> future = DecksDatabase.databaseWriteExecutor.submit(() -> cardDao.bulkAdd(collection));
        try {
            future.get();
        } catch (ExecutionException | InterruptedException e) {

            e.printStackTrace();

        }
        callback.accept(collection);
    }

}
