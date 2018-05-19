package com.game.controls;

import android.util.Log;

/**
 * Created by dabba on 12/10/17.
 */

public final class GameScoreController {
    private static final String TAG = "GameScoreController";
    private static GameScoreController singletonGameScoreController;
    private int totalScore, highestScore, currentScore;

    private GameScoreController() {
        totalScore = 0;
        highestScore = 0;
        currentScore = 0;
    }

    private static GameScoreController getController() {
        if (singletonGameScoreController == null) {
            singletonGameScoreController = new GameScoreController();
        }

        return singletonGameScoreController;
    }

    public static void set(int totalScore, int highestScore) {
        Log.v(TAG, "Setting scores- highest: " + getController().highestScore + " and total:" + getController().totalScore);
        getController().totalScore = totalScore;
        getController().highestScore = highestScore;
        getController().currentScore = 0;
    }

    public static void settle() {
        Log.v(TAG, "Settling scores- highest: " + getController().highestScore + " and total:" + getController().totalScore);
        if (getController().highestScore < getController().currentScore) {
            getController().highestScore = getController().currentScore;
        }
        getController().totalScore += getController().currentScore;
        getController().currentScore = 0;
    }

    public static void increaseCurrent(final int incrementFactor) {
        Log.v(TAG, "Increasing scores by " + incrementFactor);
        getController().currentScore += incrementFactor;
    }

    public static int getTotal() {
        return getController().totalScore;
    }

    public static int getHighest() {
        return getController().highestScore;
    }
}
