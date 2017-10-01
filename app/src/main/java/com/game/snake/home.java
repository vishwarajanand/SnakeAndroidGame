package com.game.snake;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

public class home extends AppCompatActivity {

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
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        Display defaultDisplay = windowManager.getDefaultDisplay();
        defaultDisplay.getRealMetrics(metrics);
        return String.valueOf("Original screen size: " + metrics.widthPixels) + "X" + String.valueOf(metrics.heightPixels);
    }
}
