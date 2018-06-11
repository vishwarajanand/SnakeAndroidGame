package com.game.mySnakeGame;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.game.controls.GameScoreController;
import com.game.controls.GameSpeedController;
import com.game.controls.MoveController;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class GameScreen extends Activity {
    private static final String TAG = "GameScreen";

    private MediaPlayer mediaPlayer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.snake_echo);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        View decorView = getWindow().getDecorView();
        if (hasFocus) {
            mediaPlayer.start();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }else{
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onDestroy() {
        mediaPlayer.stop();
        GameScoreController.updateScores();
        super.onDestroy();
    }

    public void onMoveControlButtonsClick(View v) {
        String controlPressed = v.getTag().toString();
        MoveController.setControlAction(controlPressed);
//        toastShort("Control: " + MoveController.getControlAction().toString());
    }

    public void onSpeedControlButtonsClick(View v) {
        String controlPressed = v.getTag().toString();
        switch (controlPressed) {
            case "UP":
                GameSpeedController.increaseSpeed();
                break;
            case "DOWN":
                GameSpeedController.reduceSpeed();
                break;
            default:
                throw new IllegalArgumentException("Arguments for speed controller are not valid");
        }
        TextView gameContent = (TextView) findViewById(R.id.txt_currentSpeed);
        gameContent.setText(getString(R.string.speed_prefix) + " " + GameSpeedController.getSpeedLevel().toString());

        toastShort("Speed level: " + GameSpeedController.getSpeedLevel().toString());
    }

    private void toastShort(String message) {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
