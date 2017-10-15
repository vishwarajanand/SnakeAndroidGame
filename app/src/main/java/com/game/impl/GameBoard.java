package com.game.impl;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import com.game.controls.GAMESTATUS;
import com.game.controls.GameStatusController;
import com.game.engine.GameEngine;

/**
 * Created by dabba on 14/10/17.
 */

public class GameBoard extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = "GameBoard";

    private GameEngine gameRun;
    public GameBoard(Context context){
        super(context);

        getHolder().addCallback(this);
        gameRun = new GameEngine(getHolder(), this);

        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        reset_game();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while(retry){
            try {
                gameRun.terminate();
                gameRun.join();
            }catch (Exception ex){
                Log.e(TAG, "Game Engine could not be destroyed",ex);
            }
            retry = false;
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction() != MotionEvent.ACTION_DOWN){
            return false;//not registered
        }

        Log.v(TAG, "Started Mouse event with game status : " + GameStatusController.getControlAction());
        switch (GameStatusController.getControlAction()){
            case GAME_OVER:
                GameStatusController.setControlAction(GAMESTATUS.NEW);
                reset_game();
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
        return  true; //processes
    }

    private void reset_game(){
        // terminate old game engine threads
        if(gameRun != null){
//            gameRun.setRunning(false);
            gameRun.terminate();
        }

        GameStatusController.setControlAction(GAMESTATUS.NEW);
        gameRun = new GameEngine(getHolder(), this);
        gameRun.start();
        //TODO: reset game actors
    }

    public void update(){

    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
    }
}
