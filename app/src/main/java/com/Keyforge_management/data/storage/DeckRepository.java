package com.Keyforge_management.data.storage;

import android.content.Context;

import com.Keyforge_management.data.model.Deck;

import java.util.List;

import androidx.lifecycle.LiveData;

public class DeckRepository {
    private DeckDao mDeckDao;
    private LiveData<List<Deck>> mAllDecks;

    public DeckRepository(Context context) {
        DecksDatabase db = DecksDatabase.getDatabase(context);
        mDeckDao = db.getDao();
        mAllDecks = mDeckDao.getDecks();
    }

    public LiveData<List<Deck>> getAllDecks() {
        return mAllDecks;
    }

    public void insert(Deck deck) {
        DecksDatabase.databaseWriteExecutor.execute(() -> {
            mDeckDao.addDeck(deck);
        });
    }

    public void delete(Deck deck) {
        DecksDatabase.databaseWriteExecutor.execute(() -> {
            mDeckDao.deleteDeck(deck);
        });
    }
}
