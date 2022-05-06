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
    int round;

    String pvp;
    String pvIA;
    String player1;
    String player2;
    String invalid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameTypeTextView = findViewById(R.id.gametypeTextView);
        turnTextView = findViewById(R.id.turnTextView);

        pvp = getResources().getString(R.string.pvp);
        pvIA = getResources().getString(R.string.pvIA);
        player1 = getResources().getString(R.string.player1);
        player2 = getResources().getString(R.string.player2);
        invalid = getResources().getString(R.string.invalid);

        gameState = new int[][] { {-1,-1,-1}, {-1,-1,-1}, {-1,-1,-1}};
        turn = 0;
        end = -1;
        round = 1;

        Intent in  = getIntent();
        gameType = in.getIntExtra("emmanuelmontblanc.insalyon.fr.GAMETYPE", -1);

        // Checks the type of game
        if (gameType > -1){
            if (gameType == 0){
                gameTypeTextView.setText(pvp);
                turnTextView.setText(player1);

            } else {
                gameTypeTextView.setText(pvIA);
                turnTextView.setText("");

            }
        }

        // create data to fill the grid with blank image at first
        int[] data = new int[9];
        Arrays.fill(data, R.drawable.blank);

        // Instantiate the recyclerView with a grid of 3 columns
        recyclerView = findViewById(R.id.gameRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));

        // Set the adapter
        adapter = new GameAdapter(this, data);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {

        // Get the column and row corresponding to the position
        int col = position / 3;
        int row = position % 3;

        // Make the play corresponding
        makePlay(view, col, row);

        // If the game is vs IA, find a random valid play and plays it
        if(gameType != 0){
            int[] iaPlay = getRandomPlay();
            if (iaPlay[0] > -1){

                // Get the viewholder corresponding to the case of the chosen play, then the view inside the viewholder
                GameAdapter.ViewHolder iaViewholder = (GameAdapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(iaPlay[0]*3+iaPlay[1]);
                View iaView = iaViewholder.itemView.findViewById(R.id.caseImageView);
                makePlay(iaView, iaPlay[0], iaPlay[1]);
            }
        }

    }

    public void makePlay(View view, int col, int row){

        if(end == -1) {
            ImageView caseImageView = view.findViewById(R.id.caseImageView);

            // Check that the play is valid, then draw either a cross or a circle and change the value in gameState
            if (gameState[col][row] == -1) {
                if (turn == 0) {
                    caseImageView.setImageResource(R.drawable.cross);
                    gameState[col][row] = 0;
                } else {
                    caseImageView.setImageResource(R.drawable.circle);
                    gameState[col][row] = 1;
                }

                // After the play, increase the round counter and check if the game ended
                round += 1;
                end = checkEnd(col, row, turn);

                // If the game didn't end, change the turn
                if (end == -1) {
                    if (turn == 0) {
                        turn = 1;
                        if(gameType == 0) turnTextView.setText(player2);
                    } else {
                        turn = 0;
                        if(gameType == 0) turnTextView.setText(player1);
                    }
                } else {
                    // if it did end go to victory screen
                    Toast.makeText(this, "end !" + end, Toast.LENGTH_SHORT).show();
                }

            } else {
                // If the play is invalid a small message pop up and the play isn't made
                Toast.makeText(this, invalid, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public int checkEnd(int col, int row, int turn){
        // returns :
        // -1 if the game isn't ended
        // 0 if player 1 won
        // 1 if player 2 won
        // 2 if the game is a draw

        int end = -1;

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

        // Checks draw
        if (round == 10 & end == -1) end = 2;

        return end;
    }

    public int[] getRandomPlay(){
        // Checks that the game is not already over
        if (end == -1) {

            // Select random plays until it finds a valid play
            while (true) {
                int i = ThreadLocalRandom.current().nextInt(0, 3);
                int j = ThreadLocalRandom.current().nextInt(0, 3);
                if (gameState[i][j] == -1) return new int[]{i, j};
            }
        }
        return new int[]{-1, -1};
    }

}