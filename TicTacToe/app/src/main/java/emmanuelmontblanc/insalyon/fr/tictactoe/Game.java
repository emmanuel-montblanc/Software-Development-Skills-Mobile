package emmanuelmontblanc.insalyon.fr.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Arrays;

public class Game extends AppCompatActivity implements GameAdapter.ItemClickListener{

    GameAdapter adapter;
    TextView turnTextView;
    TextView gametypeTextView;
    int turn;
    int[] gameState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gametypeTextView = (TextView) findViewById(R.id.gametypeTextView);
        turnTextView = (TextView) findViewById(R.id.turnTextView);

        gameState = new int[9];
        Arrays.fill(gameState, -1);
        turn = 0;
        turnTextView.setText("Player 1");

        Intent in  = getIntent();
        int gametype = in.getIntExtra("emmanuelmontblanc.insalyon.fr.GAMETYPE", -1);

        if (gametype > -1){
            if (gametype == 0){
                gametypeTextView.setText("Player Vs Player");
            } else {
                gametypeTextView.setText("Player vs IA");
            }
        }


        int[] data = new int[9];
        Arrays.fill(data, R.drawable.blank);

        RecyclerView recyclerView = findViewById(R.id.gameRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));

        adapter = new GameAdapter(this, data);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        ImageView caseImageView = view.findViewById(R.id.caseImageView);

        if(gameState[position] == -1) {
            if (turn == 0) {
                caseImageView.setImageResource(R.drawable.cross);
                gameState[position] = 0;
                turn = 1;
                turnTextView.setText("Player 2");
            } else {
                caseImageView.setImageResource(R.drawable.circle);
                gameState[position] = 1;
                turn = 0;
                turnTextView.setText("Player 1");
            }
        } else {
            Toast.makeText(this, "Invalid play !", Toast.LENGTH_SHORT).show();
        }
    }

}