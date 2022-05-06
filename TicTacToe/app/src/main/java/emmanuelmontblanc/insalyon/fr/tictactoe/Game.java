package emmanuelmontblanc.insalyon.fr.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Arrays;

public class Game extends AppCompatActivity {

    ItemAdapter adapter;

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


//        String[] data = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
        int[] data = new int[9];
        Arrays.fill(data, R.drawable.circle);

        RecyclerView recyclerView = findViewById(R.id.gameRecyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(new ItemAdapter(data));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));

    }
}