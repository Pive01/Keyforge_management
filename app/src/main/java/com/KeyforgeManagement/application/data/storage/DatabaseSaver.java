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
    private final DeckRepository deckRepository;
    private final CardRepository cardRepository;
    private final DeckCardRepository deckCardRepository;
    private final List<String> cardList;
    private final List<Boolean> tempMaverick = new ArrayList<>();
    private final List<Boolean> tempAnomaly = new ArrayList<>();
    private final List<Boolean> tempEnhanced = new ArrayList<>();
    private int index = 0;
    private final Collection<CardsDeckRef> cardsDeckRefCollection;
    private final Collection<Card> cardsColletions;


    public DatabaseSaver(Context c) {
        this.deckRepository = new DeckRepository(c);
        this.cardRepository = new CardRepository(c);
        this.deckCardRepository = new DeckCardRepository(c);
        this.cardList = new ArrayList<>();
        this.cardsDeckRefCollection = new ArrayList<>();
        this.cardsColletions = new ArrayList<>();
    }


    public void trySaveCards(Kmvresults response, Deck deck, Consumer<Collection<CardsDeckRef>> callback) {
        response.get_linked().getCards().removeIf(n-> n.getId().equals("37377d67-2916-4d45-b193-bea6ecd853e3"));//remove tide card
        tempEnhanced.clear();
        tempMaverick.clear();
        tempAnomaly.clear();
        cardsDeckRefCollection.clear();
        List<String> legacy = response.getData().getSet_era_cards().getLegacy();


        response.get_linked().getCards().forEach(card -> {
            tempMaverick.add(card.getIs_maverick());
            tempAnomaly.add(card.getIs_anomaly());
            tempEnhanced.add(card.getIs_enhanced());
            card.setIs_enhanced(false);
            card.setIs_anomaly(false);
            card.setIs_legacy(false);
            card.setIs_maverick(false);
            card.setCard_text("");
        });
        cardRepository.insertBulk(cardsColletions, cards -> {
            cardList.clear();
            cardList.addAll(response.getData().get_links().getCards());
            response.get_linked().getCards().forEach(card -> {
                cardsDeckRefCollection.add(new CardsDeckRef(card.getId(), deck.getId(),
                        Collections.frequency(cardList, card.getId()), tempMaverick.get(index),
                        legacy.contains(card.getId()), tempAnomaly.get(index), tempEnhanced.get(index)));
                index++;

            });
            deckCardRepository.insertBulk(cardsDeckRefCollection, callback);
            index = 0;
        });
    }

    public void saveDeck(Deck deck) {
        deckRepository.insert(deck);
    }

    public void saveMultipleDecks(Collection<Deck> deckCollection, Consumer<Collection<Deck>> callback) {
        deckRepository.insertBulk(deckCollection, callback);
    }


}
