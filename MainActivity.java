package com.example.clickto5million;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    TextView textScore;
    TextView textHighScore;
    Button oneClick;
    Button tenThousandClicks;
    Button oneHundredThousandClicks;

    Button resetButton;

    Button confirmButton;
    Button cancelButton;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    int score;
    int highScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void createResetPopup() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View popup = getLayoutInflater().inflate(R.layout.popup, null);

        dialogBuilder.setView(popup);
        dialog = dialogBuilder.create();
        dialog.show();

        confirmButton = popup.findViewById(R.id.confirmButton);
        cancelButton = popup.findViewById(R.id.cancelButton);

        confirmButton.setOnClickListener(view -> {
            score = 0;
            editor.putInt("Score", score);
            editor.apply();


            //resetHighScore(); //TODO delete

            updateScoreText();
            dialog.dismiss();
        });

        cancelButton.setOnClickListener(view -> {
            //close here
            dialog.dismiss();
        });
    }

    //TODO delete
    private void resetHighScore() {
        //TODO delete this section
        highScore = 0;
        editor.putInt("HighScore", highScore);
        editor.apply();
        updateHighScoreText();
        textHighScore.setText("0");
    }

    /**
     * A method that begins when the app begins.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onStart() {
        super.onStart();
    }

    /**
     * A method to hide unnecessary windows on the app.
     */
    private void hideWindow() {
        //requestWindowFeature(Window.FEATURE_ACTION_BAR);
        Objects.requireNonNull(getSupportActionBar()).hide();
    }

    private void configureButtonOneClick() {
        oneClick.setOnClickListener(view -> {
            editor.putInt("Score", ++score);
            editor.apply();

            updateScoreText();
            updateHighScore();
        });
    }

    private void configureButtonTenThousandClicks() {
        tenThousandClicks.setOnClickListener(view -> {
            score += 10000;
            editor.putInt("Score", score);
            editor.apply();

            updateScoreText();
            updateHighScore();
        });
    }

    private void configureButtonOneHundredThousandClicks() {
        oneHundredThousandClicks.setOnClickListener(view -> {
            score += 100000;
            editor.putInt("Score", score);
            editor.apply();

            updateScoreText();
            updateHighScore();
        });
    }

    //TODO make it so this has options
    private void configureResetButton() {
        resetButton.setOnClickListener(view -> {
            createResetPopup();
        });
    }

    private void updateScoreText() {
        textScore.setText(NumberFormat.getNumberInstance(Locale.US).format(score));
    }

    private void updateHighScore() {
        if(score > highScore) {
            highScore = score;
            editor.putInt("HighScore", highScore);
            editor.apply();
        }
        updateHighScoreText();
    }

    private void updateHighScoreText() {
        textHighScore.setText(NumberFormat.getNumberInstance(Locale.US).format(highScore));
    }

    private void initSP() {
        sp = getSharedPreferences("Click To 5 Million", Context.MODE_PRIVATE);
        editor = sp.edit();

        score = sp.getInt("Score", 0);
        highScore = sp.getInt("HighScore", 0);
    }

    private void initButtonConfigures() {
        configureButtonOneClick();
        configureButtonTenThousandClicks();
        configureButtonOneHundredThousandClicks();
        configureResetButton();
    }

    private void init() {
        textScore = findViewById(R.id.counterTextView);
        textHighScore = findViewById(R.id.highScoreCountTextView);
        oneClick = findViewById(R.id.oneButton);
        tenThousandClicks = findViewById(R.id.tenThousandButton);
        oneHundredThousandClicks = findViewById(R.id.oneHundredThousandButton);
        resetButton = findViewById(R.id.resetButton);

        initSP();
        initButtonConfigures();
        updateScoreText();
        updateHighScore();

        hideWindow();
    }
}