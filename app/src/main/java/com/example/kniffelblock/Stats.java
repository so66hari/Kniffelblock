package com.example.kniffelblock;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.Insert;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "Stats")
public class Stats {

    //irrelevant for gamecalculation
    @PrimaryKey
    @ColumnInfo(name = "Key")
    @NonNull
    public String key;

    //gamenumber
    @ColumnInfo(name = "SpielNummer")
    public int gameNumber;

    @ColumnInfo(name = "Spielerzahl")
    private int playerCount;

    @ColumnInfo(name = "Spielernummer")
    private int player;

    @ColumnInfo(name = "Runde")
    private int round;

    @ColumnInfo(name = "Summe")
    public int sumPoints;

    @ColumnInfo(name = "PunkteArray")
    private String points;

    public Stats(int gameNumber, int playerCount, int player, int round, int sumPoints, @NonNull String points, @NonNull String key) {
        this.playerCount = playerCount;
        this.player = player;
        this.round = round;
        this.sumPoints = sumPoints;
        this.points = points;
        this.key = key;
        this.gameNumber = gameNumber;
    }

    public int getGameNumber() {
        return this.gameNumber;
    }

    public int getPlayerCount() {
        return this.playerCount;
    }

    public int getRound() {
        return this.round;
    }

    public String getKey(){
        return this.key;
    }

    public int getPlayer() {
        return player;
    }

    public int getSumPoints() {
        return this.sumPoints;
    }

    public String getPoints() {
        return this.points;
    }

}

