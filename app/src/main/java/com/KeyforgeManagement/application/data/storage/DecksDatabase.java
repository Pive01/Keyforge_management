package com.KeyforgeManagement.application.data.storage;

import android.content.Context;

import com.KeyforgeManagement.application.data.model.Card;
import com.KeyforgeManagement.application.data.model.CardsDeckRef;
import com.KeyforgeManagement.application.data.model.Deck;
import com.KeyforgeManagement.application.data.storage.Card.CardDao;
import com.KeyforgeManagement.application.data.storage.Deck.DeckDao;
import com.KeyforgeManagement.application.data.storage.DeckWithCards.DeckWithCardsDao;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Deck.class, Card.class, CardsDeckRef.class}, version = 3, exportSchema = false)
public abstract class DecksDatabase extends RoomDatabase {

    private static final int NUMBER_OF_THREADS = 2;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static volatile DecksDatabase INSTANCE;

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE decks" +
                    " ADD houseCheating REAL NOT NULL DEFAULT 0");
            database.execSQL("ALTER TABLE decks" +
                    " ADD aercScore REAL  NOT NULL DEFAULT 0");
            database.execSQL("ALTER TABLE decks" +
                    " ADD synergyRating REAL NOT NULL DEFAULT 0");
            database.execSQL("ALTER TABLE decks" +
                    " ADD antisynergyRating REAL NOT NULL DEFAULT 0");
            database.execSQL("ALTER TABLE decks" +
                    " ADD cardDrawCount INTEGER NOT NULL DEFAULT 0");
            database.execSQL("ALTER TABLE decks" +
                    " ADD cardArchiveCount INTEGER NOT NULL DEFAULT 0");
            database.execSQL("ALTER TABLE decks" +
                    " ADD keyCheatCount INTEGER NOT NULL DEFAULT 0");
        }
    };

    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE decks" +
                    " ADD efficiency REAL NOT NULL DEFAULT 0");
            database.execSQL("ALTER TABLE decks" +
                    " ADD creatureProtection REAL NOT NULL DEFAULT 0");
        }
    };

    public static DecksDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DecksDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DecksDatabase.class, "deck_database")
                            .addMigrations(MIGRATION_1_2)
                            .addMigrations(MIGRATION_2_3)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static <V> Future<V> execute(Callable<V> command) {
        return databaseWriteExecutor.submit(command);
    }

    public abstract DeckDao getDeckDao();

    public abstract CardDao getCardDao();

    public abstract DeckWithCardsDao getCardDeckDao();
}
