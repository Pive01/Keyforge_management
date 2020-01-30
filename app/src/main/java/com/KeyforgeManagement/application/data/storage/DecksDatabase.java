package com.KeyforgeManagement.application.data.storage;

import android.content.Context;

import com.KeyforgeManagement.application.data.model.Card;
import com.KeyforgeManagement.application.data.model.CardsDeckRef;
import com.KeyforgeManagement.application.data.model.Deck;
import com.KeyforgeManagement.application.data.storage.Card.CardDao;
import com.KeyforgeManagement.application.data.storage.Deck.DeckDao;
import com.KeyforgeManagement.application.data.storage.DeckWithCards.DeckWithCardsDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Deck.class, Card.class, CardsDeckRef.class}, version = 1, exportSchema = false)
public abstract class DecksDatabase extends RoomDatabase {

    private static final int NUMBER_OF_THREADS = 2;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static volatile DecksDatabase INSTANCE;//so there is only 1 instance of it

    public static DecksDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DecksDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DecksDatabase.class, "deck_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract DeckDao getDeckDao();

    public abstract CardDao getCardDao();

    public abstract DeckWithCardsDao getCardDeckDao();
}
