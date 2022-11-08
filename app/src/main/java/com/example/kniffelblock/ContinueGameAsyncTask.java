package com.example.kniffelblock;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

public class ContinueGameAsyncTask extends AsyncTask<Integer, Void, List<Stats>> {

    private Context context;
    private List<Stats> gameList;

    ContinueGameAsyncTask(Context context) {
        this.context = context;
    }

    private void setList(List<Stats> gameList) {
        this.gameList = gameList;
    }

    @Override
    protected List<Stats> doInBackground(Integer... params) {
        GameRoomDatabase database = GameRoomDatabase.getDatabase(context);
        GameDao gameDao = database.gameDao();
        gameList = gameDao.getLastPlayerStats(params[0]);
        return gameList;
    }

    @Override
    protected void onPostExecute(List<Stats> game) {
        setList(gameList);
    }

}
