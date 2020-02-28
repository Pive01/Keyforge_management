package com.KeyforgeManagement.application.data.model;

import androidx.room.Embedded;
import androidx.room.Relation;

public class CardMetadataDTO {
    @Embedded
    private CardsDeckRef cardsDeckRef;

    @Relation(parentColumn = "cardId", entityColumn = "id")
    private Card card;

    public CardsDeckRef getCardsDeckRef() {
        return cardsDeckRef;
    }

    public void setCardsDeckRef(CardsDeckRef cardsDeckRef) {
        this.cardsDeckRef = cardsDeckRef;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    @Override
    public String toString() {
        return "CardMetadataDTO{" +
                "cardsDeckRef=" + cardsDeckRef +
                ", card=" + card +
                '}';
    }
}