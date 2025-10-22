package com.jkld.hw_2;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Locale locale = Resources.getSystem().getConfiguration().getLocales().get(0);
        String lc = locale.getLanguage();
        Toast.makeText(this, lc, Toast.LENGTH_SHORT).show();
        Button btn = findViewById(R.id.button);
        switch (lc) {
            case "uk":
                btn.setText("\uD83C\uDDFA\uD83C\uDDE6");
                break;
            case "ee":
                btn.setText("\uD83C\uDDEA\uD83C\uDDEA");
                break;
            case "en":
                btn.setText("\uD83C\uDDFA\uD83C\uDDF8");
                break;
            case "fr":
                btn.setText("\uD83C\uDDEB\uD83C\uDDF7");
                break;
            default:
                break;
        }

    }

}