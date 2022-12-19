package com.example.sequencegame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.IntentCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HighScoresActivity extends AppCompatActivity {

    Integer score = 0;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_high_scores);

        TextView txtScore = findViewById(R.id.txtScore);

        score = getIntent().getIntExtra("score", 0);

        txtScore.setText(String.valueOf(score));

        listView = findViewById(R.id.lv);

        DatabaseHandler db = new DatabaseHandler(this);

        db.emptyHiScores(); // empty table, remove if you want to keep high scores

        db.addHiScore(new HiScore("20 OCT 2020", "Benen", 12));
        db.addHiScore(new HiScore("28 OCT 2020", "Mark", 8));
        db.addHiScore(new HiScore("20 NOV 2020", "John", 6));
        db.addHiScore(new HiScore("20 NOV 2020", "Sarah", 3));
        db.addHiScore(new HiScore("22 NOV 2020", "Gemma", 2));

        if (score > 0)
        {
            db.addHiScore(new HiScore("16 DEC 2022", "Austin", score));
        }

        List<HiScore> top5HiScores = db.getTopFiveScores();

        top5HiScores = db.getTopFiveScores();
        List<String> scoresStr;
        scoresStr = new ArrayList<>();

        for (HiScore hs : top5HiScores)
        {
            scoresStr.add(hs.getScore() + "\t     " + hs.getPlayer_name());
        }

        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, scoresStr);
        listView.setAdapter(itemsAdapter);

    }

    public void PressedMainMenu(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}