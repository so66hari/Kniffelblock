package com.example.kniffelblock;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class GameViewModel extends AndroidViewModel {
    private GameRepository repository;
    private LiveData<List<Game>> gameList;

    public GameViewModel(Application app) {
        super(app);
        repository = new GameRepository(app);
        gameList = repository.getGameList();
    }

    LiveData<List<Game>> getGameList() {
        return gameList;
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public void deleteOneGame(Game game) {
        repository.deleteGame(game);
    }

}