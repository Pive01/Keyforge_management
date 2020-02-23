package com.KeyforgeManagement.application.data.storage.Card;

import android.content.Context;

import com.KeyforgeManagement.application.data.model.Card;
import com.KeyforgeManagement.application.data.storage.DecksDatabase;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Consumer;

import androidx.lifecycle.LiveData;

public class CardRepository {
    private CardDao mCardDao;
    private LiveData<List<Card>> mAllCards;

    public CardRepository(Context context) {
        DecksDatabase db = DecksDatabase.getDatabase(context);
        mCardDao = db.getCardDao();
        mAllCards = mCardDao.getCards();
    }

    public LiveData<List<Card>> getAllCards() {
        return mAllCards;
    }

    public void insert(Card card) {
        DecksDatabase.databaseWriteExecutor.execute(() -> {
            mCardDao.addCard(card);
        });
    }

    public void insertBulk(Collection<Card> collection, Consumer<Collection<Card>> callback) {
        Future<?> future = DecksDatabase.databaseWriteExecutor.submit(() -> mCardDao.bulkAdd(collection));
        try {
            future.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        callback.accept(collection);
    }
}
