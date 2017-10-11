package com.game.snake;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

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
        int total_score = sharedPref.getInt(getString(R.string.total_score), 0);
        int highest_score = sharedPref.getInt(getString(R.string.highest_score), 0);

        //add data to display
        TextView txt_win_score = (TextView)findViewById(R.id.txt_total_score);
        txt_win_score.setText(Integer.toString(total_score));

        TextView txt_lost_score = (TextView)findViewById(R.id.txt_highest_score);
        txt_lost_score.setText(Integer.toString(highest_score));
    }
}
