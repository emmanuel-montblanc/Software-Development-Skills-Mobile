package emmanuelmontblanc.insalyon.fr.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Button for choosing the type of game
        // Start the game activity when clicked
        Button pvpBtn = findViewById(R.id.pvpBtn);
        Button pviaBtn = findViewById(R.id.pviaBtn);

        pvpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gameActivity = new Intent(getApplicationContext(), Game.class);
                gameActivity.putExtra("emmanuelmontblanc.insalyon.fr.GAMETYPE", 0);
                startActivity(gameActivity);
            }
        });

        pviaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gameActivity = new Intent(getApplicationContext(), Game.class);
                gameActivity.putExtra("emmanuelmontblanc.insalyon.fr.GAMETYPE", 1);
                startActivity(gameActivity);
            }
        });
    }
}
