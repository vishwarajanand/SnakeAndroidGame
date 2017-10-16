package com.game.actors;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * Created by dabba on 17/10/17.
 */

public class Painter {
    private static final int CELLSIZE = 30;

    private Paint bgScreenPaint, cellCenterPaint, cellBoundaryPaint;
    private Point screen_size;

    private static Painter singletonScreenPainter;
    private static Painter singletonActorPainter;

    private enum PrinterType{
        ScreenPainter,
        ActorPainter
    };

    private Painter(PrinterType type){
        //set background paint
        bgScreenPaint = new Paint();
        bgScreenPaint.setColor(Color.WHITE);
        bgScreenPaint.setStyle(Paint.Style.FILL);

        cellCenterPaint = new Paint();
        cellCenterPaint.setStyle(Paint.Style.FILL);
        cellCenterPaint.setTextSize(100);

        cellBoundaryPaint = new Paint();
        cellBoundaryPaint.setStrokeWidth(0.25f);
        cellBoundaryPaint.setStyle(Paint.Style.STROKE);

        switch (type){
            case ScreenPainter:
                cellCenterPaint.setColor(Color.LTGRAY);
                cellBoundaryPaint.setColor(Color.LTGRAY);
                break;
            case ActorPainter:
                cellCenterPaint.setColor(Color.BLACK);
                cellBoundaryPaint.setColor(Color.BLACK);
                break;
            default: throw new RuntimeException("Printer type not found!");
        }
    }

    public static Painter getScreenPainter(){
        if(singletonScreenPainter == null){
            singletonScreenPainter = new Painter(PrinterType.ScreenPainter);
        }
        return singletonScreenPainter;
    }

    public static Painter getActorPainter(){
        if(singletonActorPainter == null){
            singletonActorPainter = new Painter(PrinterType.ActorPainter);
        }
        return singletonActorPainter;
    }

    public void drawBackground(Canvas canvas){
        updateDimentions(canvas);
        canvas.drawPaint(bgScreenPaint);
    }

    public void drawText(String text, Canvas canvas){
        canvas.drawText(text, 100, 100, cellCenterPaint);
    }

    public void drawCell(int cellX, int cellY, Canvas canvas){
        drawCell(new Point(cellX,cellY),canvas);
    }

    public void drawCell(Point cell, Canvas canvas){
        int i = cell.x * CELLSIZE;
        int j = cell.y * CELLSIZE;
        canvas.drawRect(i, j, i - CELLSIZE, j - CELLSIZE, getScreenPainter().cellBoundaryPaint);
        canvas.drawRect(i - 4, j - 4, i - CELLSIZE + 4, j - CELLSIZE + 4, cellCenterPaint);
    }

    public void drawScreen(Canvas canvas) {
        //create each cell
        for (int i = 0; i <= getScreenSize().x; i++) {
            for (int j = 0; j <= getScreenSize().y; j++) {
                drawCell(j, i, canvas);
            }
        }
    }

    private void updateDimentions(Canvas canvas){
        screen_size = new Point(canvas.getHeight() / CELLSIZE, canvas.getWidth() / CELLSIZE);
    }

    public Point getScreenSize(){
        return screen_size;
    }
}
