package com.jkld.hw_dialogs;

import static com.jkld.hw_dialogs.R.*;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String RUN_COUNT_KEY = "run_count";
    private static final int SHOW_DIALOG_AFTER = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int count = settings.getInt(RUN_COUNT_KEY, 0) + 1;
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(RUN_COUNT_KEY, count);
        editor.apply();

        if (count == SHOW_DIALOG_AFTER) {
            showRatingDialog();
        }
    }
    private void showRatingDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_rating, null);
        dialogBuilder.setView(dialogView);

        final RatingBar ratingBar = dialogView.findViewById(R.id.ratingBar);

        dialogBuilder.setPositiveButton("Rate", (dialog, which) -> {
            float rating = ratingBar.getRating();
            handleRating(rating);
        });

        dialogBuilder.setNegativeButton("Later", (dialog, which) -> dialog.dismiss());

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    private void handleRating(float rating) {
        if (rating >= 4) {
            showPlayStoreDialog();
        } else {
            showFeedbackDialog();
        }
    }
    private void showPlayStoreDialog() {
            new AlertDialog.Builder(this)
                    .setTitle("Very good!")
                    .setMessage("Rate us in PlayMarket!!!!!!!!")
                    .setPositiveButton("You have no choice", (dialog, which) -> {

                    })
                    .setNegativeButton("You have no choice", (dialog, which) -> dialog.dismiss())
                    .show();
    }
    private void showFeedbackDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_feedback, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextFeedback = dialogView.findViewById(R.id.editTextFeedback);

        dialogBuilder.setTitle("Now write? whats wrong with our app!");

        dialogBuilder.setPositiveButton("Send", (dialog, which) -> {
            String feedbackText = editTextFeedback.getText().toString();

            if (!feedbackText.trim().isEmpty()) {
                Toast.makeText(this, "Good job!", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            } else {
                Toast.makeText(this, "Don't be shy.", Toast.LENGTH_SHORT).show();
            }
        });

        dialogBuilder.setNegativeButton("You have no choice", (dialog, which) -> dialog.dismiss());

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }
}