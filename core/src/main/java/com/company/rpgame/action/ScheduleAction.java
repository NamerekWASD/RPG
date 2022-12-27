package com.company.rpgame.action;


import com.badlogic.gdx.scenes.scene2d.Action;

public interface ScheduleAction {
    Action begin(final float delta);
    Action end();
    Action getAction();
    boolean isActing();

    boolean isComplete();
}
