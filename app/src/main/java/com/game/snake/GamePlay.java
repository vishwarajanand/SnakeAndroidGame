package com.game.snake;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.game.engine.Engine;

import java.util.Timer;
import java.util.TimerTask;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class GamePlay extends Activity {
    private static final String TAG = "GamePlayUIThread";
    private Engine gameEngine;
    private Bitmap bitmapFromGameEngine;
    private ImageView on;

    private void initializeEngine(){
        on = (ImageView) findViewById(R.id.fullscreen_content);
        gameEngine = new Engine(on.getWidth(), on.getHeight());
        gameEngine.run();
        toastShort("Game Engine started");
        bitmapFromGameEngine = Bitmap.createBitmap(50,50, Bitmap.Config.ARGB_8888);
        Timer timerObj = new Timer();
        TimerTask timerTaskObj = new TimerTask() {
            public void run() {
                //perform your action here
                runOnUiThread(updater);
            }
        };
        timerObj.schedule(timerTaskObj, 0, 500);
//        runOnUiThread(updater);
    }

    private Runnable updater = new Runnable() {
        @Override
        public void run() {
                looper();
        }
    };

    private  void looper(){
        Log.v(TAG, "Seeking new Bitmaps");
        if (gameEngine.HasNewExportableBitmap) {
            bitmapFromGameEngine = gameEngine.getBitmap();
            on.setImageBitmap(bitmapFromGameEngine);
            on.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        Log.v(TAG, "Loop on UI");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_play);
        initializeEngine();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        View decorView = getWindow().getDecorView();
        if (hasFocus) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    public void onControlButtonsClick(View v) {
        String controlPressed = v.getTag().toString();
        Controller.setControlAction(controlPressed);
//        toastControl();
    }

    private void toastControl() {
        toastShort("Control: " + Controller.getControlAction().toString());
    }

    private void toastShort(String message) {
//        TextView gameContent = (TextView) findViewById(R.id.fullscreen_content);
//        gameContent.setText(message);
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
