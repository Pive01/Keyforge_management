package com.KeyforgeManagement.application.ui.deckDTOlist;

import com.KeyforgeManagement.application.data.model.DeckDTO;

public interface DeckDTOListInteractionListener {

    void onDeckClicked(DeckDTO deckDTO);

    void onLongDeckClicked(DeckDTO deckDTO);
}
