package com.game.engine;

import android.graphics.Bitmap;
import android.os.SystemClock;
import android.util.Log;

/**
 * Created by dabba on 12/10/17.
 */

public class Engine {
    private static final String TAG = "GameEngine";
    private final int height, width;
    private static final long target_millisPerFrame = 33; // ~30 FPS
    private Bitmap bitmapExportable;
    public boolean HasNewExportableBitmap;
    public boolean isPaused = false;
    public boolean isGameOver = false;

    public Engine(int h, int w){
        height = h;
        width = w;
        run();
    }

    private static int startX = 10, startY = 10;
    //Implement this method
    private static Bitmap reCreateBitmap(){
        return Bitmap.createBitmap(startX++,startY++, Bitmap.Config.ARGB_8888);
    }

    private void flushBitmap(){
        bitmapExportable = reCreateBitmap();
        HasNewExportableBitmap = true;
    }

    public synchronized void run(){
        Thread mainEngineThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.v(TAG, "Running engine");
                while(!isPaused && !isGameOver){
                    long startTime = SystemClock.uptimeMillis();

                    Log.v(TAG, "Running flushBitmap");
                    // Bitmap Drawing
                    flushBitmap();
                    Log.v(TAG, "Finished flushBitmap");

                    long stopTime = SystemClock.uptimeMillis();

                    // How much time do *we* require to do what we do (including drawing to surface, etc)?
                    long timeSpentInMethodCall = stopTime - startTime;

                    // So, say we took 10 millis
                    // ... that leaves (33 - 10 =) 23 millis of "time to spare"
                    // ... so that's how long we'll sleep

                    long timeToWait = target_millisPerFrame - timeSpentInMethodCall;

                    // But apply a minimum wait time so we don't starve the rest of the system
                    if ( timeToWait < 2 )
                        timeToWait = 2;

                    // Lullaby time
                    SystemClock.sleep(timeToWait);
                }
            }
            }
        );

        mainEngineThread.start();
    }

    public Bitmap getBitmap(){
        HasNewExportableBitmap = false;
        return bitmapExportable.copy(bitmapExportable.getConfig(), bitmapExportable.isMutable());
    }
}
