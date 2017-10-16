package com.game.controls;

import android.util.Log;

/**
 * Created by dabba on 12/10/17.
 */

public final class GameStatusController {
    private static final String TAG = "GameStatusController";
    private static GameStatusController singletonGameStateController;
    private GAMESTATUS gameStatus;

    private GameStatusController() {
    }

    private static GameStatusController getController() {
        if (singletonGameStateController == null) {
            singletonGameStateController = new GameStatusController();
        }

        return singletonGameStateController;
    }

    public static void setControlAction(GAMESTATUS newGameState) {
        getController().gameStatus = newGameState;
        Log.v(TAG, "Setting GameState to : " + newGameState.toString());
    }

    public static GAMESTATUS getControlAction() {
        return getController().gameStatus;
    }
}
