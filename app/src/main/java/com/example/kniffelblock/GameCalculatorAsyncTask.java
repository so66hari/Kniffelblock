package com.example.kniffelblock;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

public class GameCalculatorAsyncTask extends AsyncTask<Boolean, Void, String> {

    private int numPlayer;
    private ArrayList<ArrayList<ArrayList<Integer>>> gameStats;
    private Context context;
    private String result;

    GameCalculatorAsyncTask(int numPlayer, ArrayList<ArrayList<ArrayList<Integer>>> gameToSave, Context context) {
        this.gameStats = gameToSave;
        this.numPlayer = numPlayer;
        this.context = context;
    }

    private void setResult(String result) {
        this.result = result;
    }

    String getResult() {
        return this.result;
    }

    @Override
    protected String doInBackground(Boolean... params) {
        boolean continueButton = params[0];
        GameRoomDatabase base = GameRoomDatabase.getDatabase(context);
        GameDao dao = base.gameDao();
        String result;

        ArrayList<ArrayList<Integer>> onePlayer;
        ArrayList<Integer> oneRound;
        int maxSum = 0;
        int maxSumPlayer = 0;
        int lastGameId;

        try {
            if(continueButton) {
                lastGameId = dao.getLastGameId();
            }
            else {
                lastGameId = dao.getLastGameId() + 1;
            }
        } catch (IllegalStateException e) {
            lastGameId = 0;
        }
        Game game = new Game(lastGameId, maxSumPlayer, numPlayer, maxSum);
        dao.insertGame(game);

        try {
            for (int i = 0; i < gameStats.size(); i++) {
                onePlayer = gameStats.get(i);
                try {
                    for (int j = 1; j < onePlayer.size(); j++) {
                        oneRound = onePlayer.get(j);
                        int intSum = oneRound.get(16);
                        if (intSum > maxSum) {
                            maxSumPlayer = gameStats.indexOf(gameStats.get(i)) + 1;
                            maxSum = intSum;
                            game = new Game(lastGameId, maxSumPlayer, numPlayer, maxSum);
                            dao.insertGame(game);
                        }
                        ArrayList<String> intToStr = new ArrayList<>();
                        for (int k = 0; k < oneRound.size(); k++) {
                            intToStr.add(oneRound.get(k).toString());

                        }
                        StringBuilder sb = new StringBuilder();
                        for (String s : intToStr) {
                            sb.append(s);
                            sb.append("\t");
                        }
                        String points = sb.toString();
                        String key = Integer.toString(game.getGameId()) + i + j;
                        Stats gameStats = new Stats(lastGameId, numPlayer, i, j, intSum, points, key);
                        dao.insertStats(gameStats);
                    }
                } catch (NullPointerException e) {
                    result = "Jeder Spieler muss mindestens einen Wert eingetragen haben.";
                }
            }
            result = "Spiel wurde erfolgreich gespeichert.";
        } catch (NullPointerException e) {
            result = "Jeder Spieler muss mindestens einen Wert eingetragen haben";
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        setResult(result);
    }
}
