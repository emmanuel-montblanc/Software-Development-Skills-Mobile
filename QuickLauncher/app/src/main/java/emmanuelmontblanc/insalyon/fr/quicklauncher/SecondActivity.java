package emmanuelmontblanc.insalyon.fr.quicklauncher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        if (getIntent().hasExtra("emmanuelmontblanc.insalyon.fr.quicklauncher.TEXT")) {
            TextView tv = findViewById(R.id.textView);
            String text = getIntent().getExtras().getString("emmanuelmontblanc.insalyon.fr.quicklauncher.TEXT");
            tv.setText(text);
        }
    }
}