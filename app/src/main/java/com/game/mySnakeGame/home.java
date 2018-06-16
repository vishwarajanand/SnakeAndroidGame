package com.game.mySnakeGame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.game.controls.GameAudioController;
import com.game.controls.GameScoreController;
import android.support.v7.app.AppCompatActivity;

public class home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initializeControllers();
    }

    private void initializeControllers() {
        GameScoreController.setController(getApplicationContext());
        GameAudioController.setController(getApplicationContext());
    }

    public void onClick_NewGame(View v) {
        GameAudioController.playYikes();
        Intent k = new Intent(this, GameScreen.class);
        startActivity(k);
    }

    public void onClick_Statistics(View v) {
        GameAudioController.playYikes();
        Intent k = new Intent(this, Statistics.class);
        startActivity(k);
    }

    @Override
    protected void onDestroy() {
        GameScoreController.updateScores();
        super.onDestroy();
    }
}
