package com.company.rpgame.action.base;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.company.rpgame.action.ScheduleAction;

public class SequenceSchedule extends BaseSchedule implements ScheduleAction {

    public SequenceSchedule(Actor targetActor, SequenceAction action) {
        super(targetActor, action);
    }
    private boolean checkSequence() {
        return ((SequenceAction) action).getActions().size == 0;
    }

    @Override
    public Action begin(float delta) {
        return super.begin(delta);
    }

    @Override
    public Action end() {
        return super.end();
    }

    @Override
    public boolean isActing() {
        checkSequence();
        return super.isActing();
    }

    @Override
    public Action getAction() {
        return super.getAction();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public boolean isComplete() {
        return checkSequence();
    }
}
