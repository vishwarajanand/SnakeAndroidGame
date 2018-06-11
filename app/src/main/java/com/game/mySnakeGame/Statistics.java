package com.game.mySnakeGame;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.game.controls.GameScoreController;

public class Statistics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Toast toast = Toast.makeText(getApplicationContext(), "Super started!", Toast.LENGTH_LONG);
        // toast.show();

        //add data to display
        TextView txt_win_score = (TextView) findViewById(R.id.txt_total_score);
        txt_win_score.setText(Integer.toString(GameScoreController.getTotal()));

        TextView txt_lost_score = (TextView) findViewById(R.id.txt_highest_score);
        txt_lost_score.setText(Integer.toString(GameScoreController.getHighest()));


        MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.snake_bite_yikes);
        mediaPlayer.start();
    }
}
