package com.company.rpgame.action;


import com.badlogic.gdx.scenes.scene2d.Action;

public interface ScheduleAction {
    Action begin();
    Action end();
    Action getAction();
    boolean isActing();

    boolean isComplete();

    float getActionLastTime();
}
