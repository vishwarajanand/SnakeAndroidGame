package com.game.mySnakeGame;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.game.controls.GameScoreController;

public class Statistics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
    }

    @Override
    protected void onStart()
    {
        super.onStart();

//        Toast toast = Toast.makeText(getApplicationContext(), "Super started!", Toast.LENGTH_LONG);
//        toast.show();

        //check scoring data
        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        int total_score = sharedPref.getInt(getString(R.string.total_score), GameScoreController.getTotal());
        int highest_score = sharedPref.getInt(getString(R.string.highest_score), GameScoreController.getHighest());

        if(total_score > GameScoreController.getTotal()){
            GameScoreController.set(total_score, highest_score);
        }

        //add data to display
        TextView txt_win_score = (TextView)findViewById(R.id.txt_total_score);
        txt_win_score.setText(Integer.toString(total_score));

        TextView txt_lost_score = (TextView)findViewById(R.id.txt_highest_score);
        txt_lost_score.setText(Integer.toString(highest_score));
    }
}
