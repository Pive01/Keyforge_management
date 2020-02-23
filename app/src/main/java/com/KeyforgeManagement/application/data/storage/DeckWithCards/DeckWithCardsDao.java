package com.KeyforgeManagement.application.data.storage.DeckWithCards;

import com.KeyforgeManagement.application.data.model.Card;
import com.KeyforgeManagement.application.data.model.CardsDeckRef;

import java.util.Collection;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

@Dao
public interface DeckWithCardsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void add(CardsDeckRef cardsDeckRef);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void bulkAdd(Collection<CardsDeckRef> cardsDeckRefCollection);

    @Transaction
    @Query("SELECT * FROM cards INNER JOIN cards_deck_join" +
            " ON cards.id=cards_deck_join.cardId WHERE cards_deck_join.deckId =:deckId")
    LiveData<List<Card>> getCardsForDeck(final long deckId);

    @Query("SELECT * FROM cards_deck_join " +
            "WHERE cards_deck_join.deckId =:deckId")
    LiveData<List<CardsDeckRef>> getInfoForCards(final long deckId);

    @Query("DELETE FROM cards_deck_join WHERE cards_deck_join.deckId=:deckId ")
    void delete(final long deckId);


}



