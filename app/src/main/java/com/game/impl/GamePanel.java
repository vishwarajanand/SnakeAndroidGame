package com.game.impl;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import com.game.controls.GAMESTATUS;
import com.game.controls.GameScoreController;
import com.game.controls.GameStatusController;
import com.game.engine.GameEngine;

/**
 * Created by dabba on 14/10/17.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = "GamePanel";

    private GameEngine gameEngine;
    private GameBoard gameBoard;

    public GamePanel(Context context) {
        super(context);

        getHolder().addCallback(this);
        gameEngine = new GameEngine(getHolder(), this);

        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        resetGame();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while (retry) {
            try {
                gameEngine.terminate();
                gameEngine.join();
            } catch (Exception ex) {
                Log.e(TAG, "Game Engine could not be destroyed", ex);
            }
            retry = false;
        }
        GameScoreController.reset();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_DOWN) {
            return false;//not registered
        }

        Log.v(TAG, "Started Mouse event with game status : " + GameStatusController.getControlAction());
        switch (GameStatusController.getControlAction()) {
            case GAME_OVER:
                GameStatusController.setControlAction(GAMESTATUS.NEW);
                resetGame();
                break;
            case NEW:
            case PAUSED:
                GameStatusController.setControlAction(GAMESTATUS.RUNNING);
                break;
            case RUNNING:
                GameStatusController.setControlAction(GAMESTATUS.PAUSED);
                break;
            default:
                return false; // could not process onTouchEvent
        }

        Log.v(TAG, "Finished Mouse event with game status: " + GameStatusController.getControlAction());
        return true; //processes
    }

    private void resetGame() {
        // terminate old game engine threads
        if (gameEngine != null) {
            gameEngine.terminate();
        }

        GameStatusController.setControlAction(GAMESTATUS.NEW);
        gameBoard = new GameBoard();
        gameEngine = new GameEngine(getHolder(), this);
        gameEngine.start();
    }

    public void update() {
        gameBoard.tick();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        gameBoard.draw(canvas);
        Log.v(TAG, "Canvas drawn");
    }
}
