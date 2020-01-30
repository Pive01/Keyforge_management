package com.KeyforgeManagement.application.data.storage.Card;

import com.KeyforgeManagement.application.data.model.Card;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface CardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addCard(Card card);

    @Query("select * from cards")
    LiveData<List<Card>> getCards();
}
