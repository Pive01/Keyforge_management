package com.KeyforgeManagement.application.ui.decklist;

import com.KeyforgeManagement.application.data.model.Deck;

public interface DeckListInteractionListener {

    void onDeckClicked(Deck deck);

    void onLongDeckClicked(Deck deck);
}
