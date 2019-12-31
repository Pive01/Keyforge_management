package com.Keyforge_management.data.model.wrapper;

import com.Keyforge_management.data.model.Card;

import java.util.List;

public class Linked {
    private List<Card> cards;

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    @Override
    public String toString() {
        return "Linked{" +
                "cards=" + cards +
                '}';
    }
}
