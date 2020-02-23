package com.KeyforgeManagement.application.data.model;

import androidx.room.Embedded;
import androidx.room.Relation;

public class CardMetadataDTO {
    @Embedded
    public CardsDeckRef cardsDeckRef;

    @Relation(parentColumn = "cardId", entityColumn = "id")
    public Card card;

    @Override
    public String toString() {
        return "CardMetadataDTO{" +
                "cardsDeckRef=" + cardsDeckRef +
                ", card=" + card +
                '}';
    }
}