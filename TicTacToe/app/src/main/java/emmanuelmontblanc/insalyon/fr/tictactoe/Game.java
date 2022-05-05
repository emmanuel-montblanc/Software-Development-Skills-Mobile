package emmanuelmontblanc.insalyon.fr.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Game extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        TextView gametypeTextView = (TextView) findViewById(R.id.gametypeTextView);

        Intent in  = getIntent();
        int gametype = in.getIntExtra("emmanuelmontblanc.insalyon.fr.GAMETYPE", -1);

        if (gametype > -1){
            if (gametype == 0){
                gametypeTextView.setText("Player Vs Player");
            } else {
                gametypeTextView.setText("Player vs IA");
            }
        }

    }
}