package emmanuelmontblanc.insalyon.fr.quicklauncher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Attempt to launch an activity within our own apps
        Button secondActivityBtn = (Button) findViewById(R.id.secondActivityBtn);
        secondActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), SecondActivity.class);
                // How to pass information to second activity :
                startIntent.putExtra("emmanuelmontblanc.insalyon.fr.quicklauncher.TEXT", "HELLO WOLRD !");
                startActivity(startIntent);

            }
        });

        // Attempt to launch activity outside our app
        Button googleBtn = (Button) findViewById(R.id.googleBtn);
        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String googleLink = "https://www.youtube.com";
                Uri webaddress = Uri.parse(googleLink);

                Intent gotoGoogle = new Intent(Intent.ACTION_VIEW, webaddress);
                // Checks if there's an app that can answer the request
                if (gotoGoogle.resolveActivity(getPackageManager()) != null){
                    startActivity(gotoGoogle);
                }
                startActivity(gotoGoogle);

            }
        });

    }
}