package com.KeyforgeManagement.application.data.storage;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

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

@Database(entities = {Deck.class, Card.class, CardsDeckRef.class}, version = 5, exportSchema = false)
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
            database.execSQL("CREATE TABLE `decks_new`" +
                    "  ( " +
                    "     `id`                 INTEGER NOT NULL, " +
                    "     `keyforgeId`         TEXT," +
                    "     `name`               TEXT," +
                    "     `expansion`          TEXT," +
                    "     `creatureCount`      INTEGER NOT NULL," +
                    "     `actionCount`        INTEGER NOT NULL," +
                    "     `artifactCount`      INTEGER NOT NULL," +
                    "     `upgradeCount`       INTEGER NOT NULL," +
                    "     `sasRating`          INTEGER NOT NULL," +
                    "     `powerLevel`         INTEGER NOT NULL," +
                    "     `chains`             INTEGER NOT NULL," +
                    "     `wins`               INTEGER NOT NULL," +
                    "     `losses`             INTEGER NOT NULL," +
                    "     `totalPower`         INTEGER NOT NULL," +
                    "     `totalArmor`         INTEGER NOT NULL," +
                    "     `localWins`          INTEGER NOT NULL," +
                    "     `localLosses`        INTEGER NOT NULL," +
                    "     `artifactControl`    REAL NOT NULL," +
                    "     `creatureControl`    REAL NOT NULL," +
                    "     `creatureProtection` REAL NOT NULL," +
                    "     `efficiency`         REAL NOT NULL," +
                    "     `amberControl`       REAL NOT NULL," +
                    "     `expectedAmber`      REAL NOT NULL," +
                    "     `disruption`         REAL NOT NULL," +
                    "     `effectivePower`     INTEGER NOT NULL," +
                    "     `aercScore`          REAL NOT NULL," +
                    "     `synergyRating`      REAL NOT NULL," +
                    "     `antisynergyRating`  REAL NOT NULL," +
                    "     `cardDrawCount`      INTEGER NOT NULL," +
                    "     `cardArchiveCount`   INTEGER NOT NULL," +
                    "     `keyCheatCount`      INTEGER NOT NULL," +
                    "     `rawAmber`           INTEGER NOT NULL," +
                    "     `houses`             TEXT," +
                    "     PRIMARY KEY(`id`)" +
                    "  ) ");
            database.execSQL("Alter table decks add efficiency Real not null default 0");
            database.execSQL("Alter table decks add creatureProtection Real not null default 0");

            database.execSQL("Insert into decks_new(id,keyforgeId,name,expansion,creatureCount," +
                    "actionCount,artifactCount,upgradeCount,sasRating,powerLevel,chains,wins," +
                    "losses,totalPower,totalArmor,localWins,localLosses,artifactControl," +
                    "creatureControl,amberControl,expectedAmber,disruption,effectivePower," +
                    "aercScore,synergyRating,antisynergyRating,cardDrawCount,cardArchiveCount," +
                    "keyCheatCount,rawAmber,houses,efficiency,creatureProtection)" +
                    "Select id,keyforgeId,name,expansion,creatureCount,actionCount,artifactCount," +
                    "upgradeCount,sasRating,powerLevel,chains,wins,losses,totalPower,totalArmor," +
                    "localWins,localLosses,artifactControl,creatureControl,amberControl," +
                    "expectedAmber,disruption,effectivePower,aercScore,synergyRating," +
                    "antisynergyRating,cardDrawCount,cardArchiveCount,keyCheatCount,rawAmber," +
                    "houses,efficiency,creatureProtection from decks");

            database.execSQL("drop table decks");
            database.execSQL("alter table decks_new rename to decks");
        }
    };

    private static final Migration MIGRATION_3_4 = new Migration(3, 4) {

        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("alter table cards_deck_join add is_enhanced INTEGER DEFAULT 0");
            database.execSQL("alter table cards add is_enhanced INTEGER DEFAULT 0");
        }
    };

    private static final Migration MIGRATION_4_5 = new Migration(4, 5) {

        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("alter table decks add metaScores INTEGER NOT NULL DEFAULT 0");
        }
    };
    public static DecksDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DecksDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DecksDatabase.class, "deck_database")
                            .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4,MIGRATION_4_5)
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
