package com.game.engine;

import android.graphics.Canvas;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;

import com.game.controls.GAMESTATUS;
import com.game.controls.GameStatusController;
import com.game.impl.GamePanel;

/**
 * Created by dabba on 12/10/17.
 */

public class GameEngine extends Thread {
    private static final String TAG = "GameEngine";

    private final SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean terminalSignal;
    private static Canvas canvas;
    private static final long target_millisPerFrame = 500; // ~2 FPS

    public GameEngine(SurfaceHolder holder, GamePanel panel) {
        super();
        this.surfaceHolder = holder;
        this.gamePanel = panel;
        terminalSignal = false;
    }

    public void terminate() {
        this.terminalSignal = true;
    }

    @Override
    public void run() {
        Log.v(TAG, "State: Running");
        while (!terminalSignal) {

            long startTime = SystemClock.uptimeMillis();
            canvas = null;

            drawFrame();

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
            try {
                this.sleep(timeToWait);
            } catch (Exception ex) {
                Log.e(TAG, "GameRunning: Failure while putting thread to sleep", ex);
            }
        }
        Log.v(TAG, "State: Terminated");
    }

    public void drawFrame() {
        try {
            Log.v(TAG, "Starting frame update.");
            canvas = this.surfaceHolder.lockCanvas();
            synchronized (surfaceHolder) {

                int w = canvas.getWidth();
                int h = canvas.getHeight();
                Log.v(TAG, "WxH = " + w + "x" + h);

                this.gamePanel.update();
                Log.v(TAG, "GamePanel updated");
                this.gamePanel.draw(canvas);
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
    }
}
