package com.game.engine;

import android.graphics.Canvas;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;

import com.game.controls.GAMESTATUS;
import com.game.controls.GameStatusController;
import com.game.impl.GameBoard;

/**
 * Created by dabba on 12/10/17.
 */

public class GameEngine extends Thread {
    private static final String TAG = "GameEngine";

    private final SurfaceHolder surfaceHolder;
    private GameBoard gameBoard;
    private boolean terminalSignal;
    private static Canvas canvas;
    private static final long target_millisPerFrame = 33; // ~30 FPS

    public GameEngine(SurfaceHolder holder, GameBoard board) {
        super();
        this.surfaceHolder = holder;
        this.gameBoard = board;
        terminalSignal = false;
    }

    public void terminate(){this.terminalSignal = true;}

    @Override
    public void run() {
        Log.v(TAG, "State: Running");
        while (!terminalSignal) {

            while ((!terminalSignal) && (GameStatusController.getControlAction() == GAMESTATUS.RUNNING)) {

                long startTime = SystemClock.uptimeMillis();
                canvas = null;

                try {
                    Log.v(TAG, "Starting frame update.");
                    canvas = this.surfaceHolder.lockCanvas();
                    synchronized (surfaceHolder) {
                        this.gameBoard.update();
                        Log.v(TAG, "GameBoard updated");
                        this.gameBoard.draw(canvas);
                        Log.v(TAG, "Canvas updated");
                    }
                    Log.v(TAG, "Finished frame update");
                } catch (Exception ex) {
                    Log.e(TAG, "Exception while updating frame: ", ex);
                } finally {
                    if (canvas != null) {
                        try {
                            surfaceHolder.unlockCanvasAndPost(canvas);
                            Log.v(TAG, "Canvas unlocked and posted");
                        } catch (Exception ex) {
                            Log.e(TAG, "Canvas unlock also failed after frame update failed: ", ex);
                        }
                    }
                }

                long stopTime = SystemClock.uptimeMillis();

                // How much time do *we* require to do what we do (including drawing to surface, etc)?
                long timeSpentInMethodCall = stopTime - startTime;

                // So, say we took 10 millis
                // ... that leaves (33 - 10 =) 23 millis of "time to spare"
                // ... so that's how long we'll sleep

                long timeToWait = target_millisPerFrame - timeSpentInMethodCall;

                // But apply a minimum wait time so we don't starve the rest of the system
                if (timeToWait < 2)
                    timeToWait = 2;

                // Lullaby time
                try{
                    this.sleep(timeToWait);
                }catch (Exception ex){
                    Log.e(TAG, "GameRunning: Failure while putting thread to sleep", ex);
                }
            }
            Log.v(TAG, "STATE: Paused");
            try{
                this.sleep(500);
            }catch (Exception ex){
                Log.e(TAG, "GamePaused: Failure while putting thread to sleep", ex);
            }
        }
        Log.v(TAG, "State: Terminated");
    }
}
