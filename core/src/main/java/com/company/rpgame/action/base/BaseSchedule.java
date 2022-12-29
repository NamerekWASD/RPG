package com.company.rpgame.action.base;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.utils.Null;
import com.company.rpgame.action.ScheduleAction;

public class BaseSchedule implements ScheduleAction {
    private final Actor targetActor;
    protected final Action action;
    private boolean isInstant;

    public BaseSchedule(@Null Actor targetActor, Action action) {
        this.targetActor = targetActor;
        this.action = action;
        checkTemporal(action);
    }
    private void checkTemporal(Action action) {
        if(!(action instanceof TemporalAction)){
            return;
        }
        isInstant = ((TemporalAction) action).getDuration() == 0;
    }

    @Override
    public Action begin() {
        targetActor.addAction(action);
        if(isInstant){
            targetActor.act(0);
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
        return targetActor.getActions().contains(action, false);
    }

    @Override
    public Action getAction() {
        return action;
    }


    @Override
    public boolean isComplete() {
        return !isActing();
    }

    @Override
    public float getActionLastTime() {
        if(action instanceof TemporalAction){
            TemporalAction temporalAction = (TemporalAction) action;
            return temporalAction.getDuration() - temporalAction.getTime();
        }
        return 0;
    }
}
