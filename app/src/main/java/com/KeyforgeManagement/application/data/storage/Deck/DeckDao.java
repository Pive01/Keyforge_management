package com.KeyforgeManagement.application.data.storage.Deck;


import com.KeyforgeManagement.application.data.model.Deck;
import com.KeyforgeManagement.application.data.model.DeckDTO;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

@Dao
public interface DeckDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addDeck(Deck deck);

    @Query("select * from decks ORDER BY sasRating DESC,rawAmber DESC")
    LiveData<List<Deck>> getDecks();

    @Query("UPDATE decks SET localWins=:wins WHERE id=:id")
    void updateWins(int wins, long id);

    @Query("UPDATE decks SET localLosses=:loss WHERE id=:id")
    void updateLosses(int loss, long id);

    @Delete
    void deleteDeck(Deck deck);

    @Query("UPDATE decks SET sasRating=:sasRating," +
            "powerLevel=:powerLevel," +
            "chains=:chains," +
            "wins=:wins," +
            "losses=:losses," +
            "aercScore=:aercScore," +
            "synergyRating=:synergyRating," +
            "antisynergyRating=:antisynergyRating where id=:id")
    void updateDeckStatus(int sasRating, int powerLevel, int chains, int wins, int losses,
                          double aercScore, double synergyRating, double antisynergyRating,
                          long id);


    @Transaction
    @Query("SELECT * FROM decks WHERE id=:id")
    List<DeckDTO> getDeckDTOs(long id);

    @Transaction
    @Query("SELECT * FROM decks")
    LiveData<List<DeckDTO>> getAllDeckDTOs();
}
