package com.example.sequencegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {
    Integer score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_game_over);

        TextView txtScore = findViewById(R.id.txtScore);

        score = getIntent().getIntExtra("score", 0);

        txtScore.setText(String.valueOf(score));
    }

    public void PressedPlayAgain(View view) {
        Intent gameActivity = new Intent(GameOverActivity.this, GameActivity.class);
        startActivity(gameActivity);
    }

    public void PressedHighScores(View view) {
        Intent gameOverActivity = new Intent(GameOverActivity.this, HighScoresActivity.class);
        gameOverActivity.putExtra("score", score);
        startActivity(gameOverActivity);
    }
}