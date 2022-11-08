package com.example.kniffelblock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.GameViewHolder> {

    class GameViewHolder extends RecyclerView.ViewHolder {
        private final TextView gameLabel;
        private final TextView playerCount;
        private final TextView winner;

        private GameViewHolder(View view) {
            super(view);
            gameLabel = view.findViewById(R.id.game_title);
            playerCount = view.findViewById(R.id.num_player);
            winner = view.findViewById(R.id.num_winner);
        }
    }

    private final LayoutInflater inflater;
    private List<Game> games;

    GameListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public GameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.wordlist_item, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GameViewHolder holder, int position) {
        if (games != null && getItemCount() != 0) {
            Game current = games.get(position);
            //title in orange
            holder.gameLabel.setText(new StringBuilder().append("Spiel ").append(current.getGameId()));
            //playercount
            holder.playerCount.setText(new StringBuilder().append("Spielerzahl: ").append(current.getPlayerCount()));
            //winner
//
            holder.winner.setText(new StringBuilder().append("Gewinner: Spieler ").append(current.getPlayerNumber()).append(" mit ").append(current.getPoints()).append(" Punkten"));
        } else {
            // Covers the case of data not being ready yet.
            String empty = "Keine Spiele";
            holder.gameLabel.setText(empty);
        }
    }

    void setWords(List<Game> words){
        games = words;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (games != null)
            return games.size();
        else return 0;
    }

    public Game getGame(int pos) {
        return games.get(pos);
    }
}
