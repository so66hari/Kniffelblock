package com.example.kniffelblock;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Game.class, Stats.class}, version = 20, exportSchema = false)
public abstract class GameRoomDatabase extends RoomDatabase {
    public abstract GameDao gameDao();

    private static volatile GameRoomDatabase INSTANCE;

    public static GameRoomDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (GameRoomDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            GameRoomDatabase.class,
                            "Spieldatenbank")
                            .addCallback(callback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static GameRoomDatabase.Callback callback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final GameDao dao;

        PopulateDbAsync(GameRoomDatabase db) {
            dao = db.gameDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            return null;
        }
    }
}

