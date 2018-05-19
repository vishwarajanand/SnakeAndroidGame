package com.game.actors;

import android.graphics.Canvas;

/**
 * Created by dabba on 17/10/17.
 */

public interface Actor {
    void draw(Canvas canvas);
    void tick();
}
