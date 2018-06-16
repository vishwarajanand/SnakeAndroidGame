package com.game.controls;

import android.content.Context;
import android.media.MediaPlayer;

import com.game.mySnakeGame.R;

/**
 * Created by dabba on 16/06/16.
 */

public final class GameAudioController {
    private static final String TAG = "GameAudioController";
    private static GameAudioController singletonGameAudioController;
    private Context gameContext;
    private MediaPlayer mediaPlayer;

    private GameAudioController(Context ctx) {
        this.gameContext = ctx;
        this.mediaPlayer = null;
    }

    private static GameAudioController getController() {
        return singletonGameAudioController;
    }

    public static void setController(Context ctx) {
        if (ctx != null) {
            singletonGameAudioController = new GameAudioController(ctx);
        }
    }

    public static void playYikes() {
        reset();
        getController().mediaPlayer = MediaPlayer.create(getController().gameContext, R.raw.snake_bite_yikes);
        getController().mediaPlayer.start();
    }

    public static void loopSnakeEcho() {
        reset();
        getController().mediaPlayer = MediaPlayer.create(getController().gameContext, R.raw.snake_echo);
        getController().mediaPlayer.setLooping(true);
        getController().mediaPlayer.start();
    }

    public static void reset() {
        if (getController().mediaPlayer != null) {
            getController().mediaPlayer.stop();
        }
    }
}
