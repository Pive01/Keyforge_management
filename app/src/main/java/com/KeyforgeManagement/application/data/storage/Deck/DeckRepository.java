package com.KeyforgeManagement.application.data.storage.Deck;

import android.content.Context;

import com.KeyforgeManagement.application.data.model.Deck;
import com.KeyforgeManagement.application.data.model.DeckDTO;
import com.KeyforgeManagement.application.data.storage.DecksDatabase;

import java.util.List;
import java.util.concurrent.Future;

import androidx.lifecycle.LiveData;

public class DeckRepository {
    private DeckDao mDeckDao;
    private LiveData<List<Deck>> mAllDecks;
    private LiveData<List<DeckDTO>> allDecksDTO;

    public DeckRepository(Context context) {
        DecksDatabase db = DecksDatabase.getDatabase(context);
        mDeckDao = db.getDeckDao();
        mAllDecks = mDeckDao.getDecks();
        allDecksDTO = mDeckDao.getAllDeckDTOs();
    }

    public LiveData<List<Deck>> getAllDecks() {
        return mAllDecks;
    }

    public Future<Deck> add(Deck deck) {
        return DecksDatabase.execute(() -> {
            mDeckDao.addDeck(deck);
            return deck;
        });
    }

    public void insert(Deck deck) {
        DecksDatabase.databaseWriteExecutor.execute(() -> mDeckDao.addDeck(deck));
    }

    public void delete(Deck deck) {
        DecksDatabase.databaseWriteExecutor.execute(() -> mDeckDao.deleteDeck(deck));
    }

    public void updateWins(int wins, long id) {
        DecksDatabase.databaseWriteExecutor.execute(() -> mDeckDao.updateWins(wins, id));
    }

    public void updateLosses(int losses, long id) {
        DecksDatabase.databaseWriteExecutor.execute(() -> mDeckDao.updateLosses(losses, id));
    }

    public void updateStatus(Deck deck) {
        DecksDatabase.databaseWriteExecutor.execute(() -> {
            mDeckDao.updateDeckStatus(deck.getSasRating(), deck.getPowerLevel(), deck.getChains(),
                    deck.getWins(), deck.getLosses(), deck.getAercScore(), deck.getSynergyRating(),
                    deck.getAntisynergyRating(), deck.getId());
        });
    }

    public LiveData<List<DeckDTO>> getAllDecksDTO() {
        return allDecksDTO;
    }
}
