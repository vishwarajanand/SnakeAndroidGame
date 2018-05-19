package com.game.controls;

/**
 * Created by dabba on 12/10/17.
 */

public enum GAMESPEED {
    BEGINNER {
        @Override
        public GAMESPEED reduceGameSpeed() {
            return GAMESPEED.BEGINNER;
        }
    },
    MODERATE,
    AVERAGE,
    PRO,
    EXPERT {
        @Override
        public GAMESPEED increaseGameSpeed() {
            return GAMESPEED.EXPERT;
        }
    };

    public GAMESPEED reduceGameSpeed() {
        return values()[ordinal() - 1];
    }

    public GAMESPEED increaseGameSpeed() {
        return values()[ordinal() + 1];
    }
}