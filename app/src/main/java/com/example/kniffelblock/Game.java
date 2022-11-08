package com.example.kniffelblock;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Spiel")
public class Game {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "SpielId")
    int gameId;

    @ColumnInfo(name = "Gewinner")
    int playerNumber;

    @ColumnInfo(name = "Spielerzahl")
    int playerCount;

    @ColumnInfo(name = "Points")
    int points;

    public Game(int gameId, int playerNumber, int playerCount, int points) {
        this.gameId = gameId;
        this.playerNumber = playerNumber;
        this.playerCount = playerCount;
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    @NonNull
    public int getGameId() {
        return gameId;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public int getPlayerCount() {
        return playerCount;
    }
}
