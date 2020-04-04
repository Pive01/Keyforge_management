package com.KeyforgeManagement.application.data.storage.DeckWithCards;

import com.KeyforgeManagement.application.data.model.CardsDeckRef;

import java.util.Collection;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

@Dao
public interface DeckWithCardsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void add(CardsDeckRef cardsDeckRef);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void bulkAdd(Collection<CardsDeckRef> cardsDeckRefCollection);

}



