package com.KeyforgeManagement.application.data.storage.DeckWithCards;

import android.content.Context;

import com.KeyforgeManagement.application.data.model.Card;
import com.KeyforgeManagement.application.data.model.CardsDeckRef;
import com.KeyforgeManagement.application.data.model.Deck;
import com.KeyforgeManagement.application.data.storage.DecksDatabase;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Consumer;

import androidx.lifecycle.LiveData;


public class DeckCardRepository {
    private DeckWithCardsDao deckWithCardsDao;

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

    public void insert(Card card, Deck deck, int count, Boolean isMaverick, Boolean isLegacy,
                       Boolean isAnomaly) {
        DecksDatabase.databaseWriteExecutor.execute(() -> deckWithCardsDao.add(
                new CardsDeckRef(card.getId(), deck.getId(), count, isMaverick, isLegacy, isAnomaly)
        ));
    }

    public LiveData<List<Card>> getCards(Deck deck) {
        return deckWithCardsDao.getCardsForDeck(deck.getId());
    }

    public void delete(Deck deck) {
        DecksDatabase.databaseWriteExecutor.execute(() -> deckWithCardsDao.delete(deck.getId()));
    }

    public LiveData<List<CardsDeckRef>> getInfoForCards(Deck deck) {
        return deckWithCardsDao.getInfoForCards(deck.getId());
    }
}
