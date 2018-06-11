package com.game.mySnakeGame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.game.controls.GameScoreController;

public class home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        GameScoreController.setController(getApplicationContext());
    }

    public void onClick_NewGame(View v) {
        MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.snake_bite_yikes);
        mediaPlayer.start();
        Intent k = new Intent(this, GameScreen.class);
        startActivity(k);
    }

    public void onClick_Statistics(View v) {
        Intent k = new Intent(this, Statistics.class);
        startActivity(k);
    }

    @Override
    protected void onDestroy() {
        GameScoreController.updateScores();
        super.onDestroy();
    }
}
