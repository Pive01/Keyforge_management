package com.KeyforgeManagement.application.data.model;

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
    private Boolean is_maverick;
    private Boolean is_legacy;
    private Boolean is_anomaly;

    public CardsDeckRef(@NonNull String cardId, long deckId, int count, Boolean is_maverick,
                        Boolean is_legacy, Boolean is_anomaly) {
        this.cardId = cardId;
        this.deckId = deckId;
        this.count = count;
        this.is_maverick = is_maverick;
        this.is_legacy = is_legacy;
        this.is_anomaly = is_anomaly;
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
