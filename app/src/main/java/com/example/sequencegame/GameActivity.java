package com.example.sequencegame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import java.security.AccessController;
import java.util.Arrays;

public class GameActivity extends AppCompatActivity implements SensorEventListener {
    private final int BLUE = 1;
    private final int RED = 2;
    private final int YELLOW = 3;
    private final int GREEN = 4;

    Button bRed, bBlue, bYellow, bGreen, fb, bRedGameDisplay, bBlueGameDisplay, bYellowGameDisplay, bGreenGameDisplay;
    int sequenceCount = 4, n = 0, counter = 0, centeredCounter = 1, amountOfTurnsCounter = 0;
    private Object mutex = new Object();
    int[] gameSequence = new int[120];
    int[] playerSequence = new int[120];
    int arrayIndex = 0, playerArrayIndex = 0;
    boolean gameInPlay = false;
    private SensorManager mSensorManager;
    private Sensor mSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_game);

        // find views for buttons
        bRed = findViewById(R.id.btnRed);
        bBlue = findViewById(R.id.btnBlue);
        bYellow = findViewById(R.id.btnYellow);
        bGreen = findViewById(R.id.btnGreen);
        bRedGameDisplay = findViewById(R.id.btnRedGameDisplay);
        bBlueGameDisplay = findViewById(R.id.btnBlueGameDisplay);
        bYellowGameDisplay = findViewById(R.id.btnYellowGameDisplay);
        bGreenGameDisplay = findViewById(R.id.btnGreenGameDisplay);

        // set buttons colors
        bRed.setBackgroundColor(Color.RED);
        bBlue.setBackgroundColor(Color.BLUE);
        bYellow.setBackgroundColor(Color.YELLOW);
        bGreen.setBackgroundColor(Color.GREEN);
        bRedGameDisplay.setBackgroundColor(Color.RED);
        bBlueGameDisplay.setBackgroundColor(Color.BLUE);
        bYellowGameDisplay.setBackgroundColor(Color.YELLOW);
        bGreenGameDisplay.setBackgroundColor(Color.GREEN);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // start game
        DisplaySequence();
    }

    protected void onResume() {
        super.onResume();
        // turn on the sensor
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @SuppressLint("SetTextI18n")
    public void onSensorChanged(SensorEvent event) {

        float x, y, z;
        x = Math.abs(event.values[0]);
        y = event.values[1];
        z = Math.abs(event.values[2]);

        if(y < -3)
        {
            // tvDirection.setText("up");

            if(centeredCounter > counter && gameInPlay == true)
            {
                counter++;
                playerSequence[playerArrayIndex++] = BLUE;
                Log.d("Score added", "BLUE");
                PlayGame();
            }
        }
        else if(y > 3)
        {
            // tvDirection.setText("down");

            if(centeredCounter > counter && gameInPlay == true)
            {
                counter++;
                playerSequence[playerArrayIndex++] = YELLOW;
                Log.d("Score added", "YELLOW");
                PlayGame();
            }
        }
        else if(x > 9)
        {
            // tvDirection.setText("right");

            if(centeredCounter > counter && gameInPlay == true)
            {
                counter++;
                playerSequence[playerArrayIndex++] = GREEN;
                Log.d("Score added", "GREEN");
                PlayGame();
            }
        }
        else if(x < 3)
        {
            // tvDirection.setText("left");

            if(centeredCounter > counter && gameInPlay == true)
            {
                counter++;
                playerSequence[playerArrayIndex++] = RED;
                Log.d("Score added", "RED");
                PlayGame();
            }
        }
        else
        {
            if(counter >= centeredCounter && gameInPlay == true)
            {
                centeredCounter++;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not using
    }

    // display the 4 new colors to the user
    public void DisplaySequence() {
        ct.start();
        counter = 0;
        centeredCounter = 1;
    }

    // play game
    public void PlayGame() {
        if (counter == 4) {
            CheckSequence();
        }
    }

    // uncomment if you want to use buttons to play
    public void PressedBlue(View view) {
//        counter++;
//        playerSequence[playerArrayIndex++] = BLUE;
//        PlayGame();
    }

    public void PressedRed(View view) {
//        counter++;
//        playerSequence[playerArrayIndex++] = RED;
//        PlayGame();
    }

    public void PressedGreen(View view) {
//        counter++;
//        playerSequence[playerArrayIndex++] = GREEN;
//        PlayGame();
    }

    public void PressedYellow(View view) {
//        counter++;
//        playerSequence[playerArrayIndex++] = YELLOW;
//        PlayGame();
    }

    // check if game is right, if it is show them 4 new, otherwise send them to GameOverActivity
    public void CheckSequence() {
        gameInPlay = false;
        if (Arrays.equals(gameSequence, playerSequence) == true)
        {
            DisplaySequence();
        } else {
            int finalScore = 0;

            for (int i = 0; i < arrayIndex; i++) {
                if (playerSequence[i] == gameSequence[i])
                {
                    finalScore++;
                }
            }

            Intent gameOverActivity = new Intent(GameActivity.this, GameOverActivity.class);
            gameOverActivity.putExtra("score", finalScore);
            startActivity(gameOverActivity);
        }
    }

    CountDownTimer ct = new CountDownTimer(6000,  1500) {
        public void onTick(long millisUntilFinished) {
            // hide all buttons
            bRedGameDisplay.setVisibility(View.INVISIBLE);
            bBlueGameDisplay.setVisibility(View.INVISIBLE);
            bYellowGameDisplay.setVisibility(View.INVISIBLE);
            bGreenGameDisplay.setVisibility(View.INVISIBLE);
            bRed.setVisibility(View.INVISIBLE);
            bBlue.setVisibility(View.INVISIBLE);
            bYellow.setVisibility(View.INVISIBLE);
            bGreen.setVisibility(View.INVISIBLE);
            oneButton();
        }

        public void onFinish() {
            bRedGameDisplay.setVisibility(View.VISIBLE);
            bBlueGameDisplay.setVisibility(View.VISIBLE);
            bYellowGameDisplay.setVisibility(View.VISIBLE);
            bGreenGameDisplay.setVisibility(View.VISIBLE);

            gameInPlay = true;

            for (int i = 0; i< arrayIndex; i++)
                Log.d("game sequence", String.valueOf(gameSequence[i]));
        }
    };

    private void oneButton() {
        n = getRandom(sequenceCount);

        switch (n) {
            case 1:
                flashButton(bBlue);
                gameSequence[arrayIndex++] = BLUE;
                break;
            case 2:
                flashButton(bRed);
                gameSequence[arrayIndex++] = RED;
                break;
            case 3:
                flashButton(bYellow);
                gameSequence[arrayIndex++] = YELLOW;
                break;
            case 4:
                flashButton(bGreen);
                gameSequence[arrayIndex++] = GREEN;
                break;
            default:
                break;
        }   // end switch
    }

    // return a number between 1 and maxValue
    private int getRandom(int maxValue) {
        return ((int) ((Math.random() * maxValue) + 1));
    }

    private void flashButton(Button button) {
        fb = button;
        Handler handler = new Handler();
        Runnable r = new Runnable() {
            public void run() {
                fb.setVisibility(View.VISIBLE);
                fb.setPressed(true);
                fb.invalidate();
                fb.performClick();
                Handler handler1 = new Handler();
                Runnable r1 = new Runnable() {
                    public void run() {
                        fb.setPressed(false);
                        fb.invalidate();
                        fb.setVisibility(View.INVISIBLE);
                    }
                };
                handler1.postDelayed(r1, 1000);

            } // end runnable
        };
        handler.postDelayed(r, 500);
    }
}