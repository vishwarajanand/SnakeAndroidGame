package com.game.controls;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;

import com.game.mySnakeGame.R;

/**
 * Created by dabba on 12/10/17.
 */

public final class GameScoreController {
    private static final String TAG = "GameScoreController";
    private static GameScoreController singletonGameScoreController;
    private static Context CONTEXT;
    private int totalScore, highestScore, currentScore;

    private GameScoreController(Context ctx) {
        currentScore = 0;
        this.CONTEXT = ctx;
        SharedPreferences sharedPref = ctx.getSharedPreferences(Resources.getSystem().getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        totalScore = sharedPref.getInt(Resources.getSystem().getString(R.string.total_score), GameScoreController.getTotal());
        highestScore = sharedPref.getInt(Resources.getSystem().getString(R.string.highest_score), GameScoreController.getHighest());
    }

    private static GameScoreController getController() {
        return singletonGameScoreController;
    }

    public static void setController(Context ctx) {
        if (ctx != null) {
            singletonGameScoreController = new GameScoreController(ctx);
        }
    }

    public static void reset() {
        Log.v(TAG, "Settling scores- highest: " + getController().highestScore + " and total:" + getController().totalScore);
        if (getController().highestScore < getController().currentScore) {
            getController().highestScore = getController().currentScore;
        }
//        getController().totalScore += getController().currentScore;
        getController().currentScore = 0;
        updateScores();
    }

    public static void incrementScores(final int incrementFactor) {
        Log.v(TAG, "Increasing scores by " + incrementFactor);

        // update current score
        getController().currentScore += incrementFactor;

        // update total score
        getController().totalScore += incrementFactor;

        // update highest score
        if(getController().highestScore < getController().currentScore){
            getController().highestScore = getController().currentScore;
        }

    }

    public static int getTotal() {
        return getController().totalScore;
    }

    public static int getHighest() {
        return getController().highestScore;
    }

    public static void updateScores() {
        SharedPreferences sharedPref = CONTEXT.getSharedPreferences(Resources.getSystem().getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPref.edit();
        prefEditor.putInt(Resources.getSystem().getString(R.string.total_score), GameScoreController.getTotal())
                .putInt(Resources.getSystem().getString(R.string.highest_score), GameScoreController.getHighest()).commit();
    }
}
