package com.KeyforgeManagement.application.data.storage.Deck;

import android.content.Context;

import com.KeyforgeManagement.application.data.model.Deck;
import com.KeyforgeManagement.application.data.model.DeckDTO;
import com.KeyforgeManagement.application.data.storage.DecksDatabase;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Consumer;

import androidx.lifecycle.LiveData;

public class DeckRepository {
    private final DeckDao mDeckDao;
    private final LiveData<List<Deck>> mAllDecks;
    private final LiveData<List<DeckDTO>> allDecksDTO;

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

    public void insertBulk(Collection<Deck> collection, Consumer<Collection<Deck>> callback) {
        Future<?> future = DecksDatabase.databaseWriteExecutor.submit(() -> mDeckDao.bulkAdd(collection));
        try {
            future.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        callback.accept(collection);
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

    public void updateStatus(Deck deck, Consumer<Deck> callback) {
        Future<?> future = DecksDatabase.databaseWriteExecutor.submit(() -> {
            mDeckDao.updateDeckStatus(deck.getSasRating(), deck.getPowerLevel(), deck.getChains(),
                    deck.getWins(), deck.getLosses(), deck.getAercScore(), deck.getSynergyRating(),
                    deck.getAntisynergyRating(), deck.getCardDrawCount(), deck.getCardArchiveCount(),
                    deck.getHouseCheating(), deck.getKeyCheatCount(), deck.getId(),
                    deck.getEfficiency(), deck.getExpectedAmber(), deck.getCreatureProtection());
        });

        try {
            future.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        callback.accept(deck);
    }

    public LiveData<List<DeckDTO>> getAllDecksDTO() {
        return allDecksDTO;
    }

    public LiveData<DeckDTO> getDeckDTO(long id) {
        return mDeckDao.getDeckDTOs(id);
    }
}
