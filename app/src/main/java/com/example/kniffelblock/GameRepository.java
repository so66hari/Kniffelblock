package com.example.kniffelblock;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import java.util.List;

public class GameRepository {
    private GameDao gameDao;
    GameRoomDatabase db;

    GameRepository(Application app) {
        db = GameRoomDatabase.getDatabase(app);
        gameDao = db.gameDao();
    }

    LiveData<List<Game>> getGameList() {
        return gameDao.getGameList();
    }

    public void deleteAll()  {
        new deleteAllGamesAsyncTask(db).execute();
    }

    public void deleteGame(Game game) {
        new deleteGameAsyncTask(gameDao).execute(game);
    }

    private static class deleteAllGamesAsyncTask extends AsyncTask<Void, Void, Void> {
        private GameRoomDatabase mAsyncTaskDao;

        deleteAllGamesAsyncTask(GameRoomDatabase dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.clearAllTables();
            return null;
        }
    }

    private static class deleteGameAsyncTask extends AsyncTask<Game, Void, Void> {
        private GameDao mAsyncTaskDao;

        deleteGameAsyncTask(GameDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Game... params) {
            List<Stats> list = mAsyncTaskDao.getSpecificGame(params[0].gameId);
            for(Stats stats : list) {
                mAsyncTaskDao.deleteStats(stats);
            }
            mAsyncTaskDao.deleteGame(params[0]);
            return null;
        }
    }
}
