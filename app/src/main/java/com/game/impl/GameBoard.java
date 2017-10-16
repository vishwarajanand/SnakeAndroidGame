package com.game.impl;

import android.graphics.Canvas;

import com.game.actors.Painter;
import com.game.actors.SnakeActor;
import com.game.controls.GameStatusController;

/**
 * Created by dabba on 15/10/17.
 */

public class GameBoard {
    private static final String TAG = "GameBoard";
    SnakeActor snake;

    public GameBoard() {
    }

    public void tick() {
        if(snake != null){
            snake.tick();
        }
    }

    public Canvas draw(Canvas canvas) {
        // clear canvas
        Painter.getScreenPainter().drawBackground(canvas);
        // canvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR);

        switch (GameStatusController.getControlAction()) {
            case NEW:
                Painter.getScreenPainter().drawText("Tap to Start",canvas);
                snake = new SnakeActor(Painter.getScreenPainter().getScreenSize().x,Painter.getScreenPainter().getScreenSize().x);
                break;
            case PAUSED:
                Painter.getScreenPainter().drawText("Game paused, tap to Play",canvas);
                break;
            case GAME_OVER:
                Painter.getScreenPainter().drawText("Game Over! Tap to Retry",canvas);
                break;
            case RUNNING:
                Painter.getScreenPainter().drawScreen(canvas);
                snake.draw(canvas);
                break;
            default:
                throw new RuntimeException("Unknown GameStatus!!");
        }

        return canvas;
    }
}
