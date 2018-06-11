package com.game.actors;

import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;

import com.game.controls.GameScoreController;

import java.util.Random;

/**
 * Created by dabba on 17/10/17.
 */

public class MouseActor implements Actor {
    private static final String TAG = "SnakeActor";
    Point screen_size;
    Point location;

    public MouseActor(int size_x, int size_y) {
        this.screen_size = new Point(size_x, size_y);
        this.location = new Point(5, 5);
    }

    public Point getPoint() {
        return location;
    }

    @Override
    public void draw(Canvas canvas) {
        Painter.getActorPainter().drawCell(getPoint(), canvas);
    }

    @Override
    public void tick() {
        location = new Point(1 + (new Random()).nextInt(screen_size.x - 2), 1 + (new Random()).nextInt(screen_size.y - 2));
        Log.v(TAG, "New mouse created: " + location + " with screen size: " + screen_size);

        // Bonus for eating mouse
        GameScoreController.incrementScores(20);
    }
}
