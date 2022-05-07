package emmanuelmontblanc.insalyon.fr.tictactoe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import android.widget.TextView;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private int[] history;
    private int gameType;
    private LayoutInflater mInflater;
    private Context context;

    HistoryAdapter(Context context, int[] history, int gameType) {
        this.mInflater = LayoutInflater.from(context);
        this.history = history;
        this.gameType = gameType;
        this.context = context;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.history_layout, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the views
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // Set the turn text
        String turnStr = "Turn " + (position+1) + ":";
        holder.roundTextView.setText(turnStr);

        // Sets the player text
        String player;
        if( position%2 == 0){
            player = context.getResources().getString(R.string.player1);
        } else {
            if(gameType == 0){
                player = context.getResources().getString(R.string.player2);
            } else {
                player = context.getResources().getString(R.string.ia);
            }
        }
        holder.playerTextView.setText(player);

        // Sets the play text
        int row = history[position]/3;
        int col = history[position]%3;
        holder.playTextView.setText("row: " + row + " | col: " + col);

    }

    // total number of cells
    @Override
    public int getItemCount() {
        return history.length;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView roundTextView;
        TextView playerTextView;
        TextView playTextView;

        ViewHolder(View itemView) {
            super(itemView);
            roundTextView = itemView.findViewById(R.id.roundTextView);
            playerTextView = itemView.findViewById(R.id.playerTextView);
            playTextView = itemView.findViewById(R.id.playTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
        }
    }
}