package com.game.actors;

import android.graphics.Canvas;
import android.graphics.Point;

import java.util.Random;

/**
 * Created by dabba on 17/10/17.
 */

public class MouseActor implements Actor {
    Point screen_size;
    Point current;

    public MouseActor(int size_x, int size_y) {
        this.screen_size = new Point(size_x, size_y);
        this.current = new Point(5, 5);
    }

    public Point getPoint() {
        return current;
    }

    @Override
    public void draw(Canvas canvas) {
        Painter.getActorPainter().drawCell(getPoint(), canvas);
    }

    @Override
    public void tick() {
        current = new Point((new Random()).nextInt(screen_size.x + 1), (new Random()).nextInt(screen_size.y + 1));
    }
}
