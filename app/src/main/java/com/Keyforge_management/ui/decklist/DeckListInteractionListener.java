package com.Keyforge_management.ui.decklist;

import com.Keyforge_management.data.model.Deck;

public interface DeckListInteractionListener {

    void onDeckClicked(Deck deck);

    void onLongDeckClicked(Deck deck);
}
