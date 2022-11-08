package com.example.kniffelblock;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    Boolean warning;
    SharedPreferences.Editor editor;
    int continueNumber;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("start Application", "___________________________________________________________");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        warning = preferences.getBoolean("check_edit", true);
        editor = preferences.edit();

        final Button newGame = findViewById(R.id.button_new);
        final ButtonDialogs open = new ButtonDialogs(context);
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (warning) {
                    open.getGameWarning(context);
                } else {
                    open.getNewGame(context);
                }
            }
        });


        Button continueGame = findViewById(R.id.button_continue);
        continueGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (continueNumber == 0) {
                    Toast.makeText(getApplicationContext(), "Keine Spiele gefunden", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, GameActivity.class);
                    intent.putExtra("buttonContinue", continueNumber);
                    Log.i(TAG, "onClick: continueNumber: " + continueNumber);
                    startActivity(intent);
                }
            }
        });

        Button gameList = findViewById(R.id.button_list);
        gameList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentList = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intentList);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_rules:
                Intent intentRules = new Intent(MainActivity.this, RulesActivity.class);
                startActivity(intentRules);
                return true;

            case R.id.action_settings:
                Intent intentSettings = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intentSettings);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onResume() {
        super.onResume();
        new LastGameAsyncTask().execute();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        warning = preferences.getBoolean("check_warning", true);
        preferences.getBoolean("check_design", true);
    }

    class LastGameAsyncTask extends AsyncTask<Void, Void, Integer> {
        GameRoomDatabase base = GameRoomDatabase.getDatabase(getApplicationContext());
        GameDao dao = base.gameDao();

        @Override
        protected Integer doInBackground(Void... voids) {
            List<Stats> games = dao.getLastStats();
            try {
                Stats oneGame = games.get(0);
                continueNumber = oneGame.getPlayerCount();
            } catch (IndexOutOfBoundsException i) {
                continueNumber = 0;
            }
            return continueNumber;
        }

        @Override
        protected void onPostExecute(Integer result) {
            returnPlayerCount(continueNumber);
        }
    }

    public void returnPlayerCount(int number) {
        continueNumber = number;
    }
}
