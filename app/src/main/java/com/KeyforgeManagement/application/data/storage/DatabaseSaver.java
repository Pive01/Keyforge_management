package com.KeyforgeManagement.application.data.storage;

import android.content.Context;

import com.KeyforgeManagement.application.data.model.Card;
import com.KeyforgeManagement.application.data.model.CardsDeckRef;
import com.KeyforgeManagement.application.data.model.Deck;
import com.KeyforgeManagement.application.data.model.wrapperMasterVault.Kmvresults;
import com.KeyforgeManagement.application.data.storage.Card.CardRepository;
import com.KeyforgeManagement.application.data.storage.Deck.DeckRepository;
import com.KeyforgeManagement.application.data.storage.DeckWithCards.DeckCardRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class DatabaseSaver {
    private DeckRepository deckRepository;
    private CardRepository cardRepository;
    private DeckCardRepository deckCardRepository;
    private List<String> cardList;
    private List<Boolean> tempMaverick = new ArrayList<>();
    private List<Boolean> tempAnomaly = new ArrayList<>();
    private int index = 0;
    private Collection<CardsDeckRef> cardsDeckRefCollection;
    private Collection<Card> cardsColletions;


    public DatabaseSaver(Context c) {
        this.deckRepository = new DeckRepository(c);
        this.cardRepository = new CardRepository(c);
        this.deckCardRepository = new DeckCardRepository(c);
        this.cardList = new ArrayList<>();
        this.cardsDeckRefCollection = new ArrayList<>();
        this.cardsColletions = new ArrayList<>();
    }


    public void trySaveCards(Kmvresults response, Deck deck, Consumer<Collection<CardsDeckRef>> callback) {
        tempMaverick.clear();
        tempAnomaly.clear();
        cardsDeckRefCollection.clear();
        List<String> legacy = response.getData().getSet_era_cards().getLegacy();


        response.get_linked().getCards().forEach(card -> {
            tempMaverick.add(card.getIs_maverick());
            tempAnomaly.add(card.getIs_anomaly());
            card.setIs_anomaly(false);
            card.setIs_legacy(false);
            card.setIs_maverick(false);
            cardsColletions.add(card);
        });

        cardRepository.insertBulk(cardsColletions, cards -> {
            cardList.clear();
            cardList.addAll(response.getData().get_links().getCards());
            response.get_linked().getCards().forEach(card -> {
                cardsDeckRefCollection.add(new CardsDeckRef(card.getId(), deck.getId(),
                        Collections.frequency(cardList, card.getId()), tempMaverick.get(index),
                        legacy.contains(card.getId()), tempAnomaly.get(index)));
                index++;

            });
            deckCardRepository.insertBulk(cardsDeckRefCollection, callback);
            index = 0;
        });
    }

    public void saveDeck(Deck deck) {
        deckRepository.insert(deck);
    }


}
