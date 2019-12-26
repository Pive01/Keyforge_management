package com.Keyforge_management.data.storage;

import android.content.Context;

import com.Keyforge_management.data.model.Deck;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Deck.class}, version = 1)
public abstract class DecksDatabase extends RoomDatabase {

    private static final int NUMBER_OF_THREADS = 2;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static volatile DecksDatabase INSTANCE;//so there is only 1 instance of it

    static DecksDatabase getDatabase(final Context context) {
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

    public abstract DeckDao getDao();
}
