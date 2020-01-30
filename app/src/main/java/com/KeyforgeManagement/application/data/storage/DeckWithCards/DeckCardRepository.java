package com.KeyforgeManagement.application.data.storage.DeckWithCards;

import android.content.Context;

import com.KeyforgeManagement.application.data.model.Card;
import com.KeyforgeManagement.application.data.model.CardsDeckRef;
import com.KeyforgeManagement.application.data.model.Deck;
import com.KeyforgeManagement.application.data.storage.DecksDatabase;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;


public class DeckCardRepository {
    private DeckWithCardsDao mDeckWithCardsDao;
    private List<Card> cardList;
    private List<CardsDeckRef> refList;

    public DeckCardRepository(Context context) {
        DecksDatabase db = DecksDatabase.getDatabase(context);
        mDeckWithCardsDao = db.getCardDeckDao();
        cardList = new ArrayList<>();
        refList = new ArrayList<>();

    }

    public void insert(Card card, Deck deck, int count, Boolean isMaverick, Boolean
            isLegacy, Boolean isAnomaly) {
        DecksDatabase.databaseWriteExecutor.execute(() -> {
            mDeckWithCardsDao.add(new CardsDeckRef(card.getId(), deck.getId(),
                    count, isMaverick, isLegacy, isAnomaly));
        });
    }

    public LiveData<List<Card>> getCards(Deck deck) {
        return mDeckWithCardsDao.getCardsForDeck(deck.getId());
    }

    public void delete(Deck deck) {
        DecksDatabase.databaseWriteExecutor.execute(() -> mDeckWithCardsDao.delete(deck.getId()));

    }

    public LiveData<List<CardsDeckRef>> getInfoForCards(Deck deck) {

        return mDeckWithCardsDao.getInfoForCards(deck.getId());

    }



}
