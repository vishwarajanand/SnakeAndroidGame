package com.game.snake;

import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.TextView;

public class home extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        displayText(originalScreenSize());
    }

    private void displayText(String textData){
        TextView textViewScreenSize = (TextView) findViewById(R.id.textViewScreenSize);
        textViewScreenSize.setText(textData);
    }

    private String originalScreenSize() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);
        return String.valueOf("Original screen size: " + size.x) + "X" + String.valueOf(size.y);
    }

    public void onClick_NewGame(View v) {
        Intent k = new Intent(this, GameScreen.class);
        startActivity(k);
    }
    public void onClick_Statistics(View v) {
        Intent k = new Intent(this, Statistics.class);
        startActivity(k);
    }
}
