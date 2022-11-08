package com.example.kniffelblock;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.PreferenceManager;
import androidx.viewpager.widget.ViewPager;

import com.example.kniffelblock.ui.main.PlaceholderFragment;
import com.example.kniffelblock.ui.main.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class GameActivity extends AppCompatActivity implements PlaceholderFragment.FragmentToActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    Boolean warning;
    public static final String EXTRA_MESSAGE = "com.example.kniffelblock.extra.MESSAGE";
    int numPlayer;
    SectionsPagerAdapter adapter;
    ViewPager viewPager;

    ArrayList<ArrayList<ArrayList<Integer>>> oneGame;
    private Boolean continueButton;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: __________________________________________");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        //home button
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //getplayerCount
        Intent intent = getIntent();
        try {
            String string = intent.getStringExtra("newButton");
            numPlayer = Integer.parseInt(string);
            continueButton = false;
        } catch (NumberFormatException e) {
            numPlayer = intent.getIntExtra("buttonContinue", 1);
            continueButton = true;
        }

        //settings for warning
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        warning = preferences.getBoolean("check_warning", true);

        adapter = new SectionsPagerAdapter(numPlayer, getSupportFragmentManager(), continueButton);

        //adapater for fragments
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (numPlayer >= 5) {
                tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
            }
        }
        else {
            if(numPlayer >= 8) {
                tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
            }
        }
        oneGame = new ArrayList<>();
        for (int i = 0; i < numPlayer; i++) {
            oneGame.add(i, null);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_saveData:
                GameCalculatorAsyncTask asyncTask = new GameCalculatorAsyncTask(numPlayer, oneGame, context);
                asyncTask.execute(continueButton);
                Toast.makeText(getApplicationContext(), "Spiel erfolgreich gespeichert.", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_newGame:
                final ButtonDialogs open = new ButtonDialogs(context);
                if(warning) {
                    open.getGameWarning(context);
                    Log.i(TAG, "onOptionsItemSelected: warning");
                }
                else {
                    open.getNewGame(context);
                    Log.i(TAG, "onOptionsItemSelected: new Game");
                }
                return true;

            case R.id.action_rules:
                Intent intentRules = new Intent(GameActivity.this, RulesActivity.class);
                startActivity(intentRules);
                Log.d(TAG, "onOptionsItemSelected: read rules");
                return true;

            case R.id.action_settings:
                Intent intentSettings = new Intent(GameActivity.this, SettingsActivity.class);
                startActivity(intentSettings);
                Log.d(TAG, "onOptionsItemSelected: change settings");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: resumed");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        warning = preferences.getBoolean("check_warning", true);
    }

    @Override
    public void communicate(ArrayList<ArrayList<Integer>> data) {
        for (int i = 0; i < numPlayer; i++) {
            if (viewPager.getCurrentItem() == i) {
                oneGame.set(i, data);
            }
        }
    }
}