package com.game.impl;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.Log;

/**
 * Created by dabba on 15/10/17.
 */

public class GameBoard {
    private static final String TAG = "GameBoard";
    private int height, width;

    public GameBoard() {
    }

    public void tick() {

    }

    public Canvas draw(Canvas canvas) {
        //setting height and width
        width = canvas.getWidth();
        height = canvas.getHeight();
        Log.v(TAG, "Canvas WidthXHeight: " + width + "X" + height);

        //clear canvas
        Paint bgPaint = new Paint();
        bgPaint.setColor(Color.WHITE);
        bgPaint.setStyle(Paint.Style.FILL);
        canvas.drawPaint(bgPaint);
//        canvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR);

        //create boundary
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);

        canvas.drawRect(0, 0, width, height, paint);
        return canvas;
    }
}
