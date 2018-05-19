package com.game.actors;

import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;

import com.game.controls.GAMESTATUS;
import com.game.controls.GameScoreController;
import com.game.controls.GameStatusController;
import com.game.controls.MOVE;
import com.game.controls.MoveController;

import java.util.ArrayList;

/**
 * Created by dabba on 17/10/17.
 */

public class SnakeActor implements Actor {
    private static final String TAG = "SnakeActor";
    final Point screen_size;
    ArrayList<Point> body;
    final MouseActor mouse;
    MOVE velocity;

    public SnakeActor(int size_x, int size_y) {
        this.screen_size = new Point(size_x, size_y);
        this.body = new ArrayList<Point>();
        this.body.add(new Point(10, 10));
        this.mouse = new MouseActor(size_x, size_y);
        velocity = MOVE.RIGHT;
        GameScoreController.settle();
    }

    public Point getHead() {
        return new Point(body.get(0));
    }

    @Override
    public void draw(Canvas canvas) {
        for (Point bodyPoint : body) {
            Painter.getActorPainter().drawCell(bodyPoint, canvas);
        }
        mouse.draw(canvas);
    }

    @Override
    public void tick() {
        //get Move from controller
        MOVE currentVelocity = MoveController.getControlAction();
        if ((currentVelocity == MOVE.DOWN && velocity != MOVE.UP)
                || (currentVelocity == MOVE.UP && velocity != MOVE.DOWN)
                || (currentVelocity == MOVE.LEFT && velocity != MOVE.RIGHT)
                || (currentVelocity == MOVE.RIGHT && velocity != MOVE.LEFT)) {
            velocity = currentVelocity;
        }

        //calculate new head
        Point newHead;
        Point oldHead = getHead();
        switch (velocity) {
            case DOWN:
                newHead = new Point(oldHead.x, oldHead.y + 1);
                break;
            case UP:
                newHead = new Point(oldHead.x, oldHead.y - 1);
                break;
            case LEFT:
                newHead = new Point(oldHead.x - 1, oldHead.y);
                break;
            case RIGHT:
                newHead = new Point(oldHead.x + 1, oldHead.y);
                break;
            default:
                newHead = getHead();
        }

        //end game if snake touches borders
        if (newHead.x > screen_size.x
                || newHead.y > screen_size.y
                || newHead.x == 0
                || newHead.y == 0) {
            Log.v(TAG, "Game over because snake crossed screen- Screen size:" + screen_size + " and snake head: " + newHead);
            GameStatusController.setControlAction(GAMESTATUS.GAME_OVER);
            GameScoreController.settle();
            return;
        }

        //end game if snake touches itself
        for (Point bodyPoint : body) {
            if (areSamePoints(newHead, bodyPoint)) {
                Log.v(TAG, "Game over because snake crossed its own body- snake body: " + bodyPoint + " and snake head: " + newHead);
                GameStatusController.setControlAction(GAMESTATUS.GAME_OVER);
                GameScoreController.settle();
                return;
            }
        }

        // Increment score for moving
        GameScoreController.increaseCurrent(1);

        //add length if new Head is mouse
        if (areSamePoints(newHead, mouse.location)) {
            body.add(0, newHead);

            //end game if all places are filled
            if (body.size() >= screen_size.x * screen_size.y) {
                Log.v(TAG, "Game over because snake has covered all of screen- " + screen_size+ " and snake size: " + body.size());
                GameStatusController.setControlAction(GAMESTATUS.GAME_OVER);
                GameScoreController.settle();
                return;
            }

            //add a new mouse
            while (true) {
                mouse.tick();
                boolean retry = false;
                for (Point snakeBody : body) {
                    retry = retry || (areSamePoints(snakeBody, mouse.location));
                }
                if (!retry) break;
            }
        } else {
            body.add(0, newHead);
            body.remove(body.size() - 1);
        }
    }

    private boolean areSamePoints(Point a, Point b) {
        return a.x == b.x && a.y == b.y;
    }
}
