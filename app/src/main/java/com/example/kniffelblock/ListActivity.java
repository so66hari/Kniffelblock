package com.example.kniffelblock;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class ListActivity extends AppCompatActivity {

    private GameViewModel gameViewModel;
    Context context = this;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = findViewById(R.id.list_games);
        final GameListAdapter adapter = new GameListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        gameViewModel = ViewModelProviders.of(this).get(GameViewModel.class);
        gameViewModel.getGameList().observe(this, new Observer<List<Game>>() {
            @Override
            public void onChanged(List<Game> games) {
                adapter.setWords(games);
            }
        });

        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Game game = adapter.getGame(position);
                        Toast.makeText(ListActivity.this, "Spiel wurde gelöscht", Toast.LENGTH_SHORT).show();
                        gameViewModel.deleteOneGame(game);
                    }
                });

        helper.attachToRecyclerView(recyclerView);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        getSettings();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_clear) {
            boolean warning = preferences.getBoolean("check_warning", true);
            if(warning) {
                final ButtonDialogs open = new ButtonDialogs(context);
                AlertDialog dialog = open.getDeleteWarning(context);
                dialog.show();
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        if(open.getBoolean()) {
                            gameViewModel.deleteAll();
                        }
                    }
                });
            }
            else {
                gameViewModel.deleteAll();
                Toast.makeText(getApplicationContext(), "Spieldaten gelöscht", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private SharedPreferences getSettings() {
        return preferences;
    }
}
