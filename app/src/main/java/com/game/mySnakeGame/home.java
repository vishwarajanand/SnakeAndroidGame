package com.game.mySnakeGame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.TextView;

import com.game.controls.GameScoreController;

public class home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        displayText(originalScreenSize());
        setScores();
    }

//    private void displayText(String textData){
//        TextView textViewScreenSize = (TextView) findViewById(R.id.textViewScreenSize);
//        textViewScreenSize.setText(textData);
//    }
//
//    private String originalScreenSize() {
//        Display display = getWindowManager().getDefaultDisplay();
//        Point size = new Point();
//        display.getRealSize(size);
//        return String.valueOf("Original screen size: " + size.x) + "X" + String.valueOf(size.y);
//    }

    public void onClick_NewGame(View v) {
        Intent k = new Intent(this, GameScreen.class);
        startActivity(k);
    }

    public void onClick_Statistics(View v) {
        Intent k = new Intent(this, Statistics.class);
        startActivity(k);
    }

    @Override
    protected void onDestroy() {
        updateScores();
        super.onDestroy();
    }

    private void setScores() {
        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        int total_score = sharedPref.getInt(getString(R.string.total_score), GameScoreController.getTotal());
        int highest_score = sharedPref.getInt(getString(R.string.highest_score), GameScoreController.getHighest());

        if (total_score > GameScoreController.getTotal()) {
            GameScoreController.set(total_score, highest_score);
        }
    }

    private void updateScores() {
        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPref.edit();
        prefEditor.putInt(getString(R.string.total_score), GameScoreController.getTotal()).commit();
        prefEditor.putInt(getString(R.string.highest_score), GameScoreController.getHighest()).commit();
    }
}
