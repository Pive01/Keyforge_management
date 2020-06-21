package com.KeyforgeManagement.application.data.model.wrapperDecksOfKeyforge;

import com.KeyforgeManagement.application.data.model.adaptation.NewDeckFormat;

public class SingleDeckReference {

    private NewDeckFormat deck;

    public NewDeckFormat getDeck() {
        return deck;
    }

    public void setDeck(NewDeckFormat deck) {
        this.deck = deck;
    }
}
