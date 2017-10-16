package com.game.actors;

import android.graphics.Canvas;
import android.graphics.Point;

import com.game.controls.GAMESTATUS;
import com.game.controls.GameStatusController;
import com.game.controls.MOVE;
import com.game.controls.MoveController;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by dabba on 17/10/17.
 */

public class SnakeActor implements Actor {
    Point screen_size;
    ArrayList<Point> current;
    MouseActor mouse;
    MOVE velocity;

    public SnakeActor(int size_x, int size_y) {
        this.screen_size = new Point(size_x, size_y);
        this.current = new ArrayList<Point>();
        this.current.add(new Point(10, 10));
        this.mouse = new MouseActor(size_x,size_y);
        velocity = MOVE.RIGHT;
    }

    public Point getHead() {
        return new Point(current.get(0));
    }

    @Override
    public void draw(Canvas canvas) {
        for (Point bodyPoint: current) {
            Painter.getActorPainter().drawCell(bodyPoint, canvas);
        }
        mouse.draw(canvas);
    }

    @Override
    public void tick() {
        //get Move from controller
        MOVE currentVelocity = MoveController.getControlAction();
        if((currentVelocity == MOVE.DOWN && velocity != MOVE.UP)
                ||(currentVelocity == MOVE.UP && velocity != MOVE.DOWN)
                ||(currentVelocity == MOVE.LEFT && velocity != MOVE.RIGHT)
                ||(currentVelocity == MOVE.RIGHT && velocity != MOVE.LEFT)){
            velocity = currentVelocity;
        }

        //calculate new head
        Point newHead = getHead();
        Point oldHead = getHead();
        switch (velocity){
            case DOWN:newHead=new Point(oldHead.x,oldHead.y+1);break;
            case UP:newHead=new Point(oldHead.x,oldHead.y-1);break;
            case LEFT:newHead=new Point(oldHead.x-1,oldHead.y);break;
            case RIGHT:newHead=new Point(oldHead.x+1,oldHead.y);break;
            default:newHead = getHead();
        }

        //end game if snake touches borders
        if(newHead.x == screen_size.x
                || newHead.y == screen_size.y
                || newHead.x == 0
                || newHead.y == 0){
            GameStatusController.setControlAction(GAMESTATUS.GAME_OVER);
            return;
        }

        //add length if new Head is mouse
        if(areSamePoints(newHead, mouse.current)){
            current.add(0,newHead);

            //end game if all places are filled
            if(current.size() >= screen_size.x * screen_size.y){
                GameStatusController.setControlAction(GAMESTATUS.GAME_OVER);
                return;
            }

            //add a new mouse
            while(true){
                mouse.tick();
                boolean retry = false;
                for (Point snakeBody: current) {
                    retry = retry || (areSamePoints(snakeBody,mouse.current));
                }
                if(!retry) break;
            }
        }
        else {
            current.add(0,newHead);
            current.remove(current.size()-1);
        }
    }

    private boolean areSamePoints(Point a, Point b){
        return a.x == b.x && a.y == b.y;
    }
}
