package com.Keyforge_management.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import static androidx.room.ForeignKey.CASCADE;


@Entity(tableName = "cards_deck_join",
        primaryKeys = {"cardId", "deckId"},
        foreignKeys = {
                @ForeignKey(
                        entity = Card.class,
                        parentColumns = "id",
                        childColumns = "cardId"),
                @ForeignKey(onDelete = CASCADE,
                        entity = Deck.class,
                        parentColumns = "id",
                        childColumns = "deckId"),
        })
public class CardsDeckRef {
    @NonNull
    private String cardId;
    private long deckId;
    private int count;

    public CardsDeckRef(String cardId, long deckId, int count) {
        this.cardId = cardId;
        this.deckId = deckId;
        this.count = count;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public long getDeckId() {
        return deckId;
    }

    public void setDeckId(int deckId) {
        this.deckId = deckId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
