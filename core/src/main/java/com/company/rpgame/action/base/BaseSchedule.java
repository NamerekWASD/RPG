package com.company.rpgame.action.base;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.utils.Disposable;
import com.company.rpgame.action.ScheduleAction;

public class BaseSchedule implements ScheduleAction, Disposable {
    private final Actor targetActor;
    protected final Action action;
    private boolean isInstant;
    private boolean complete = false;

    public BaseSchedule(Actor targetActor, Action action) {
        this.targetActor = targetActor;
        this.action = action;
        checkTemporal(action);
    }
    private void checkTemporal(Action action) {
        if(!(action instanceof TemporalAction)){
            return;
        }
        if(((TemporalAction) action).getDuration() == 0){
            isInstant = true;
        }
    }

    @Override
    public Action begin(float delta) {
        targetActor.addAction(action);
        if(isInstant){
            targetActor.act(delta);
            end();
        }
        return action;
    }

    @Override
    public Action end() {
        targetActor.removeAction(action);
        return action;
    }

    @Override
    public boolean isActing() {
        complete = !targetActor.getActions().contains(action, false);
        return !complete;
    }

    @Override
    public Action getAction() {
        return action;
    }

    @Override
    public void dispose() {
        Actions.removeAction(action);
    }

    @Override
    public boolean isComplete() {
        return complete;
    }
}
