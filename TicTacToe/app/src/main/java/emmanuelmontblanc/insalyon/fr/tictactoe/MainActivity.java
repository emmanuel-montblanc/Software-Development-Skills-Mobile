package emmanuelmontblanc.insalyon.fr.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int circleImg = R.drawable.circle;
        int crossImg = R.drawable.cross;
        ImageView crossImageView = (ImageView) findViewById(R.id.crossImageView);
        ImageView circleImageView = (ImageView) findViewById(R.id.circleImageView);
        scaleImg(crossImageView, crossImg);
        scaleImg(circleImageView, circleImg);


        Button pvpBtn = (Button) findViewById(R.id.pvpBtn);
        Button pviaBtn = (Button) findViewById(R.id.pviaBtn);

        pvpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent showDetailActivity = new Intent(getApplicationContext(), Game.class);
                showDetailActivity.putExtra("emmanuelmontblanc.insalyon.fr.GAMETYPE", 0);
                startActivity(showDetailActivity);
            }
        });

        pviaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent showDetailActivity = new Intent(getApplicationContext(), Game.class);
                showDetailActivity.putExtra("emmanuelmontblanc.insalyon.fr.GAMETYPE", 1);
                startActivity(showDetailActivity);
            }
        });
    }

    private void scaleImg(ImageView img, int pic){
        Display screen = getWindowManager().getDefaultDisplay();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), pic, options);

        int imgWidth = options.outWidth;
        int screenWidth = screen.getWidth();

        int ratio = Math.round( (float)imgWidth / ((float)screenWidth * (float)0.3) );
        options.inSampleSize = ratio;

        options.inJustDecodeBounds = false;
        Bitmap scaledImg = BitmapFactory.decodeResource(getResources(), pic, options);
        img.setImageBitmap(scaledImg);
    }
}