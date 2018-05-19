package com.game.controls;

import android.util.Log;

/**
 * Created by dabba on 12/10/17.
 */

public final class GameSpeedController {
    private static final String TAG = "GameSpeedController";
    private static GameSpeedController singletonGameSpeedController;
    private GAMESPEED gameSpeed;

    private GameSpeedController() {
        gameSpeed = GAMESPEED.BEGINNER;
    }

    private static GameSpeedController getController() {
        if (singletonGameSpeedController == null) {
            singletonGameSpeedController = new GameSpeedController();
        }

        return singletonGameSpeedController;
    }

    public static void reduceSpeed() {
        getController().gameSpeed = getController().gameSpeed.reduceGameSpeed();
    }


    public static void increaseSpeed() {
        getController().gameSpeed = getController().gameSpeed.increaseGameSpeed();
    }

    public static GAMESPEED getSpeedLevel() {
        return getController().gameSpeed;
    }
}
