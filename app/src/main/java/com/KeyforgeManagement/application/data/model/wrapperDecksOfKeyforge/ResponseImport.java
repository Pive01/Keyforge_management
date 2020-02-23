package com.KeyforgeManagement.application.data.model.wrapperDecksOfKeyforge;

import com.KeyforgeManagement.application.data.model.Deck;

import java.util.List;

public class ResponseImport {
    List<Deck> decks;
    int page;

    public List<Deck> getDecks() {
        return decks;
    }

    public void setDecks(List<Deck> decks) {
        this.decks = decks;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
