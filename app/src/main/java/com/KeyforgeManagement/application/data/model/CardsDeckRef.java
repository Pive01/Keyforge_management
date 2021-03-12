package com.KeyforgeManagement.application.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import org.jetbrains.annotations.NotNull;

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
    private final String cardId;
    private final long deckId;
    private final int count;
    private final Boolean is_maverick;
    private final Boolean is_legacy;
    private final Boolean is_anomaly;
    private Boolean is_enhanced;


    public CardsDeckRef(@NonNull String cardId, long deckId, int count, Boolean is_maverick,
                        Boolean is_legacy, Boolean is_anomaly, Boolean is_enhanced) {
        this.cardId = cardId;
        this.deckId = deckId;
        this.count = count;
        this.is_maverick = is_maverick;
        this.is_legacy = is_legacy;
        this.is_anomaly = is_anomaly;
        this.is_enhanced = is_enhanced;
    }

    public Boolean getIs_enhanced() {
        return is_enhanced;
    }

    public void setIs_enhanced(Boolean is_enhanced) {
        this.is_enhanced = is_enhanced;
    }

    @Override
    public String toString() {
        return "CardsDeckRef{" +
                "cardId='" + cardId + '\'' +
                ", deckId=" + deckId +
                ", count=" + count +
                ", is_maverick=" + is_maverick +
                ", is_legacy=" + is_legacy +
                ", is_anomaly=" + is_anomaly +
                ", is_enhanced=" + is_enhanced +
                '}';
    }

    public Boolean getIs_maverick() {
        return is_maverick;
    }

    public Boolean getIs_legacy() {
        return is_legacy;
    }

    public Boolean getIs_anomaly() {
        return is_anomaly;
    }

    @NotNull
    public String getCardId() {
        return cardId;
    }

    public long getDeckId() {
        return deckId;
    }

    public int getCount() {
        return count;
    }

}
