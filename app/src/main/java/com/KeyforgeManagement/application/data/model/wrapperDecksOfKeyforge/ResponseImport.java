package com.KeyforgeManagement.application.data.model.wrapperDecksOfKeyforge;

import com.KeyforgeManagement.application.common.Utils;
import com.KeyforgeManagement.application.data.model.Deck;
import com.KeyforgeManagement.application.data.model.adaptation.NewDeckFormat;

import java.util.List;

public class ResponseImport {
    private List<NewDeckFormat> decks;
    private int page;

    public List<Deck> getDecks() {
        return Utils.convertToOldList(decks);
    }

    public void setDecks(List<NewDeckFormat> decks) {
        this.decks = decks;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
