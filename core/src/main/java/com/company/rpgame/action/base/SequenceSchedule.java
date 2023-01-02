package com.company.rpgame.action.base;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;
import lombok.val;

public class SequenceSchedule extends BaseSchedule {

    public SequenceSchedule(@Null Actor targetActor, SequenceAction action) {
        super(targetActor, action);
    }
    private boolean sequenceIsEmpty() {
        return ((SequenceAction) action).getActions().size == 0 || action.getActor() == null;
    }

    @Override
    public Action begin() {
        return super.begin();
    }

    @Override
    public Action end() {
        return super.end();
    }

    @Override
    public boolean isActing() {
        return !sequenceIsEmpty();
    }

    @Override
    public Action getAction() {
        return super.getAction();
    }

    @Override
    public boolean isComplete() {
        return sequenceIsEmpty();
    }

    @Override
    public float getActionRemainingTime() {
        return scanAllActionsAndCalculateTotalTime(action, 0);

    }
    private float scanAllActionsAndCalculateTotalTime(Action action, float time){
        float totalTime = time;
        for (val child :
                new Array.ArrayIterator<>(((SequenceAction) action).getActions())) {
            if(child instanceof TemporalAction){
                TemporalAction temporalAction = (TemporalAction) child;
                totalTime += temporalAction.getDuration() - temporalAction.getTime();
            }
            totalTime = defineActions(totalTime, child);
        }
        return totalTime;
    }

    private float getMaxTimeOfParallelAction(ParallelAction action, float time) {
        float totalTime = time;
        float maxTime = 0;
        for (val child :
                new Array.ArrayIterator<>(action.getActions())) {
            if(child instanceof TemporalAction){
                TemporalAction temporalAction = (TemporalAction) child;
                maxTime = temporalAction.getDuration() - temporalAction.getTime();
            }
            totalTime = defineActions(totalTime, child);
        }
        return totalTime + maxTime;
    }

    private float defineActions(float totalTime, Action child) {
        if(child instanceof SequenceAction){
            SequenceAction sequenceAction = (SequenceAction) child;
            totalTime += scanAllActionsAndCalculateTotalTime(sequenceAction, totalTime);
        }
        if(child instanceof ParallelAction){
            ParallelAction parallelAction = (ParallelAction) child;
            totalTime += getMaxTimeOfParallelAction(parallelAction, totalTime);
        }
        if(child instanceof DelayAction){
            DelayAction delayAction = (DelayAction) child;
            totalTime += delayAction.getDuration() - delayAction.getTime();
        }
        return totalTime;
    }
}
