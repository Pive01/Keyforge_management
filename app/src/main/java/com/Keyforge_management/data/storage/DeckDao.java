package com.Keyforge_management.data.storage;


import com.Keyforge_management.data.model.Deck;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface DeckDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addDeck(Deck deck);

    @Query("select * from decks")
    LiveData<List<Deck>> getDecks();

    @Delete
    void deleteDeck(Deck deck);
}
