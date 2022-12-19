package com.example.sequencegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        Button playGameButton = findViewById(R.id.playGameButton);
        Button viewHighScoresButton = findViewById(R.id.viewHighScoresButton);

        playGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });
    }

    public void PressedHighScores(View view) {
        Intent highScoresActivity = new Intent(MainActivity.this, HighScoresActivity.class);
        startActivity(highScoresActivity);
    }
}