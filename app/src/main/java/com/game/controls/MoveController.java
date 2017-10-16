package com.game.controls;

import android.util.Log;

/**
 * Created by dabba on 12/10/17.
 */

public final class MoveController {
    private static final String TAG = "MoveController";
    private static final long CONTROL_RETENTION_IN_MILLIS = 1100L;
    private static MoveController singletonMoveController;
    private MOVE moveAction;
    private long epoch_time_in_millis;

    private MoveController(){}

    private static MoveController getController(){
        if(singletonMoveController == null){
            singletonMoveController = new MoveController();
        }

        return singletonMoveController;
    }

    public static void setControlAction(MOVE action){
        getController().epoch_time_in_millis = System.currentTimeMillis();
        getController().moveAction = action;
        Log.v(TAG, "Setting control to : " + action.toString());
    }

    public static void setControlAction(String action){
        MOVE parsed_action;
        switch (action){
            case "UP":
                parsed_action = (MOVE.UP);
                break;
            case "DOWN":
                parsed_action = (MOVE.DOWN);
                break;
            case "LEFT":
                parsed_action = (MOVE.LEFT);
                break;
            case "RIGHT":
                parsed_action = (MOVE.RIGHT);
                break;
            default:
                parsed_action = (MOVE.NOOP);
        }

        setControlAction(parsed_action);
    }

    public static MOVE getControlAction(){
        if((System.currentTimeMillis() >= getController().epoch_time_in_millis)&&
                (System.currentTimeMillis() <= getController().epoch_time_in_millis + CONTROL_RETENTION_IN_MILLIS)){
            return getController().moveAction;
        }
        else{
            return MOVE.NOOP;
        }
    }
}
