package com.example.kniffelblock;

import android.content.Context;
import android.os.AsyncTask;

class DeleteAsyncTask extends AsyncTask<Void, Void, Void> {

    private Context context;

    DeleteAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        GameRoomDatabase base = GameRoomDatabase.getDatabase(context);
        base.clearAllTables();
        return null;
    }
}
