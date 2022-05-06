package emmanuelmontblanc.insalyon.fr.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Game extends AppCompatActivity implements GameAdapter.ItemClickListener{

    GameAdapter adapter;
    TextView turnTextView;
    TextView gameTypeTextView;
    RecyclerView recyclerView;

    int turn;
    int[][] gameState;
    int end;
    int gameType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameTypeTextView = (TextView) findViewById(R.id.gametypeTextView);
        turnTextView = (TextView) findViewById(R.id.turnTextView);

        gameState = new int[][] { {-1,-1,-1}, {-1,-1,-1}, {-1,-1,-1}};
        turn = 0;
        end = -1;

        Intent in  = getIntent();
        gameType = in.getIntExtra("emmanuelmontblanc.insalyon.fr.GAMETYPE", -1);

        if (gameType > -1){
            if (gameType == 0){
                gameTypeTextView.setText("Player Vs Player");
                turnTextView.setText("Player 1");

            } else {
                gameTypeTextView.setText("Player vs IA");
                turnTextView.setText("");

            }
        }


        int[] data = new int[9];
        Arrays.fill(data, R.drawable.blank);

        recyclerView = findViewById(R.id.gameRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));

        adapter = new GameAdapter(this, data);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        int col = position / 3;
        int row = position % 3;

        makePlay(view, col, row);

        if(gameType != 0){
            int[] iaPlay = getRandomPlay();
            if (iaPlay[0] > -1){
                turnTextView.setText("ia play : " + iaPlay[0] + " " +iaPlay[1]);
                GameAdapter.ViewHolder iaViewholder = (GameAdapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(iaPlay[0]*3+iaPlay[1]);
                View iaView = iaViewholder.itemView.findViewById(R.id.caseImageView);
                makePlay(iaView, iaPlay[0], iaPlay[1]);
            }
        }

    }

    public void makePlay(View view, int col, int row){

        if(end == -1) {
            ImageView caseImageView = view.findViewById(R.id.caseImageView);

            if (gameState[col][row] == -1) {
                if (turn == 0) {
                    caseImageView.setImageResource(R.drawable.cross);
                    gameState[col][row] = 0;
                } else {
                    caseImageView.setImageResource(R.drawable.circle);
                    gameState[col][row] = 1;
                }

                end = checkEnd(col, row, turn);

                if (end == -1) {
                    if (turn == 0) {
                        turn = 1;
                        if(gameType == 0) turnTextView.setText("Player 2");
                    } else {
                        turn = 0;
                        if(gameType == 0) turnTextView.setText("Player 1");
                    }
                } else {
                    Toast.makeText(this, "end !" + end, Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this, "Invalid play !", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public int checkEnd(int col, int row, int turn){

        int end = -1;

        // Checks draw
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(gameState[i][j] == -1){
                    break;
                }

                if(i == 2 & j == 2){
                    end = 2;
                }
            }
        }

        // Checks col
        for(int i = 0; i < 3; i++){
            if(gameState[col][i] != turn)
                break;
            if(i == 2){
                end = turn;
            }
        }

        // Checks row
        for(int i = 0; i < 3; i++){
            if(gameState[i][row] != turn)
                break;
            if(i == 2){
                end = turn;
            }
        }

        // Checks diag
        if(row == col){
            //we're on a diagonal
            for(int i = 0; i < 3; i++){
                if(gameState[i][i] != turn)
                    break;
                if(i == 2){
                    end = turn;
                }
            }
        }

        // Checks anti diag
        if(row + col == 2){
            for(int i = 0; i < 3; i++){
                if(gameState[i][2-i] != turn)
                    break;
                if(i == 2){
                    end = turn;
                }
            }
        }

        return end;
    }

    public int[] getRandomPlay(){
        if (end == -1) {
            while (true) {
                int i = ThreadLocalRandom.current().nextInt(0, 3);
                int j = ThreadLocalRandom.current().nextInt(0, 3);;
                if (gameState[i][j] == -1) return new int[]{i, j};
            }
        }
        return new int[]{-1, -1};
    }

}