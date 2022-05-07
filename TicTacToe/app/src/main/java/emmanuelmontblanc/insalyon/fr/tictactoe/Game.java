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

    GameAdapter gameAdapter;
    TextView turnTextView;
    TextView gameTypeTextView;
    RecyclerView gameRecyclerView;

    int turn;
    int[][] gameState;
    int end;
    int gameType;
    int round;
    int[] gameHistory;

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
        turnTextView = findViewById(R.id.roundTextView);

        // Get strings from resources for the textViews
        pvp = getResources().getString(R.string.pvp);
        pvIA = getResources().getString(R.string.pvIA);
        player1 = getResources().getString(R.string.player1);
        player2 = getResources().getString(R.string.player2);
        invalid = getResources().getString(R.string.invalid);

        // Initialize the game variables
        gameState = new int[][] { {-1,-1,-1}, {-1,-1,-1}, {-1,-1,-1}};
        turn = 0;
        end = -1;
        round = 0;
        gameHistory = new int[9];
        Arrays.fill(gameHistory, -1);

        // Get the type of game
        Intent in  = getIntent();
        gameType = in.getIntExtra("emmanuelmontblanc.insalyon.fr.GAMETYPE", -1);

        // Checks the type of game and set the turn text view accordingly
        if (gameType > -1){
            if (gameType == 0){
                gameTypeTextView.setText(pvp);
                turnTextView.setText(player1);

            } else {
                gameTypeTextView.setText(pvIA);
                turnTextView.setText("");

            }
        }

        // creates data to fill the grid with blank image at first
        int[] data = new int[9];
        Arrays.fill(data, R.drawable.blank);

        // Instantiates the recyclerView with a grid of 3 columns
        gameRecyclerView = findViewById(R.id.gameRecyclerView);
        gameRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        gameRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        gameRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));

        // Sets the adapter
        gameAdapter = new GameAdapter(this, data);
        gameAdapter.setClickListener(this);
        gameRecyclerView.setAdapter(gameAdapter);
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

                // Get the viewholder corresponding to the case of the chosen play, then gets the view inside the viewholder
                GameAdapter.ViewHolder iaViewholder = (GameAdapter.ViewHolder) gameRecyclerView.findViewHolderForAdapterPosition(iaPlay[0]*3+iaPlay[1]);
                View iaView = iaViewholder.itemView.findViewById(R.id.caseImageView);

                // Make the play on the view, with the play randomly chosen
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
                gameHistory[round] = col*3 + row;
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
                    Intent victoryActivity = new Intent(getApplicationContext(), VictoryScreen.class);
                    victoryActivity.putExtra("emmanuelmontblanc.insalyon.fr.GAMETYPE", gameType);
                    victoryActivity.putExtra("emmanuelmontblanc.insalyon.fr.END", end);
                    victoryActivity.putExtra("emmanuelmontblanc.insalyon.fr.GAMEHISTORY", gameHistory);
                    startActivity(victoryActivity);
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
        if (round == 9 & end == -1) end = 2;

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