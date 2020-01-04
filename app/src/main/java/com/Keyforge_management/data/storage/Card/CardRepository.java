package com.Keyforge_management.data.storage.Card;

import android.content.Context;

import com.Keyforge_management.data.model.Card;
import com.Keyforge_management.data.storage.DecksDatabase;

import java.util.List;

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
}
