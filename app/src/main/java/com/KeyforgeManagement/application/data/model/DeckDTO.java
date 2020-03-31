package com.KeyforgeManagement.application.data.model;

import java.io.Serializable;
import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;

public class DeckDTO implements Serializable {

    @Embedded
    private Deck deck;

    @Relation(parentColumn = "id", entityColumn = "deckId", entity = CardsDeckRef.class)
    private List<CardMetadataDTO> cards;

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public List<CardMetadataDTO> getCards() {
        return cards;
    }

    public void setCards(List<CardMetadataDTO> cards) {
        this.cards = cards;
    }

    @Override
    public String toString() {
        return "DeckDTO{" +
                "deck=" + deck + "," +
                " cards=" + cards + "" +
                '}';
    }
}

