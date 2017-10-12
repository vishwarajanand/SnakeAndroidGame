package com.game.snake;

/**
 * Created by dabba on 12/10/17.
 */

public final class Controller {
    private static final long CONTROL_RETENTION_IN_MILLIS = 100L;
    private static Controller singletonController;
    private MOVE moveAction;
    private long epoch_time_in_millis;

    private Controller(){}

    private static Controller getController(){
        if(singletonController == null){
            singletonController = new Controller();
        }

        return singletonController;
    }

    public static void setControlAction(MOVE action){
        getController().epoch_time_in_millis = System.currentTimeMillis();
        getController().moveAction = action;
    }

    public static void setControlAction(String action){
        switch (action){
            case "UP":
                Controller.setControlAction(MOVE.UP);
                break;
            case "DOWN":
                Controller.setControlAction(MOVE.DOWN);
                break;
            case "LEFT":
                Controller.setControlAction(MOVE.LEFT);
                break;
            case "RIGHT":
                Controller.setControlAction(MOVE.RIGHT);
                break;
            default:
                Controller.setControlAction(MOVE.NOOP);
        }
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
