package com.Keyforge_management.data.storage.DeckWithCards;

import android.content.Context;

import com.Keyforge_management.data.model.Card;
import com.Keyforge_management.data.model.CardsDeckRef;
import com.Keyforge_management.data.model.Deck;
import com.Keyforge_management.data.storage.DecksDatabase;

import java.util.List;

import androidx.lifecycle.LiveData;


public class DeckCardRepository {
    private DeckWithCardsDao mDeckWithCardsDao;

    public DeckCardRepository(Context context) {
        DecksDatabase db = DecksDatabase.getDatabase(context);
        mDeckWithCardsDao = db.getCardDeckDao();

    }

    public void insert(Card card, Deck deck, int count) {
        DecksDatabase.databaseWriteExecutor.execute(() -> {
            mDeckWithCardsDao.add(new CardsDeckRef(card.getId(), deck.getId(), count));
        });
    }

    public LiveData<List<Card>> getCards(Deck deck) {

        return mDeckWithCardsDao.getCardsForDeck(deck.getId());
    }

}
