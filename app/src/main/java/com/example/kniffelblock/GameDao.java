package com.example.kniffelblock;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GameDao {

    //Game
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertStats(Stats game);

    //Winner
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertGame(Game game);

    @Delete
    void deleteGame(Game game);

    @Delete
    void deleteStats(Stats game);

    @Query("SELECT * FROM Spiel ORDER BY SpielId ASC")
    LiveData<List<Game>> getGameList();

    @Query("SELECT SpielId FROM Spiel WHERE SpielId = (SELECT MAX(SpielId) FROM Spiel)")
    int getLastGameId();

    @Query("SELECT * FROM Stats WHERE SpielNummer LIKE :spielNummer")
    List<Stats> getSpecificGame(int spielNummer);

    @Query("SELECT * FROM Stats WHERE Spielnummer = (SELECT MAX(Spielnummer) FROM Stats)")
    List<Stats> getLastStats();

    @Query("SELECT * FROM Stats WHERE Spielnummer = (SELECT MAX(SpielId) FROM Spiel) AND Spielernummer LIKE :playerNumber")
    List<Stats> getLastPlayerStats(int playerNumber);
}

