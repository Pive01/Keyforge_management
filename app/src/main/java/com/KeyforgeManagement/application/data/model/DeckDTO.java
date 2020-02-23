package com.KeyforgeManagement.application.data.model;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;

public class DeckDTO {

    @Embedded
    public Deck deck;

    @Relation(parentColumn = "id", entityColumn = "deckId", entity = CardsDeckRef.class)
    public List<CardMetadataDTO> cards;

    @Override
    public String toString() {
        return "DeckDTO{" +
                "deck=" + deck +
                ", cards=" + cards +
                '}';
    }
}

