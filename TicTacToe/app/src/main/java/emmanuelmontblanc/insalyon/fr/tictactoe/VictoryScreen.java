package emmanuelmontblanc.insalyon.fr.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class VictoryScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_victory_screen);

        // Get the end, gameType and gameHistory from the extras
        Intent in  = getIntent();
        int end = in.getIntExtra("emmanuelmontblanc.insalyon.fr.END", -1);
        int gameType = in.getIntExtra("emmanuelmontblanc.insalyon.fr.GAMETYPE", -1);
        int[] gameHistory = in.getIntArrayExtra("emmanuelmontblanc.insalyon.fr.GAMEHISTORY");

        // Display the winner of the game on the textView
        TextView winnerTextView = findViewById(R.id.winnerTextView);
        String winnerString;
        if(end == 2){
            winnerString = getResources().getString(R.string.draw);
        } else {
            if (end == 0) {
                winnerString = getResources().getString(R.string.player1);
            } else {
                if (gameType == 0) {
                    winnerString = getResources().getString(R.string.player2);
                } else {
                    winnerString = getResources().getString(R.string.ia);
                }
            }
            winnerString = winnerString + " wins !";
        }
        winnerTextView.setText(winnerString);

        // Button to return to the main menu
        Button mainMenuBtn = findViewById(R.id.mainMenuBtn);
        mainMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(), MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        // Trims gameHistory, so it is the right length
        int l = 4;
        for(int i = 4; i< gameHistory.length; i++){
            if(gameHistory[i] == -1){
                l = i;
                break;
            }
        }
        int[] gameHistoryTrimmed = new int[l];
        System.arraycopy(gameHistory, 0, gameHistoryTrimmed, 0, l);

        // Instantiate the recycler view
        RecyclerView historyRecyclerView = findViewById(R.id.historyRecyclerView);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        historyRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        // Sets the adapter
        HistoryAdapter historyAdapter = new HistoryAdapter(this, gameHistoryTrimmed, gameType);
        historyRecyclerView.setAdapter(historyAdapter);
    }
}