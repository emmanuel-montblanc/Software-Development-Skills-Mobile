package emmanuelmontblanc.insalyon.fr.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class VictoryScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_victory_screen);

        Intent in  = getIntent();
        int end = in.getIntExtra("emmanuelmontblanc.insalyon.fr.END", -1);
        int gameType = in.getIntExtra("emmanuelmontblanc.insalyon.fr.GAMETYPE", -1);

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


    }
}