package com.KeyforgeManagement.application.data.storage.Card;

import com.KeyforgeManagement.application.data.model.Card;

import java.util.Collection;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

@Dao
public interface CardDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void bulkAdd(Collection<Card> cardsDeckRefCollection);

}
