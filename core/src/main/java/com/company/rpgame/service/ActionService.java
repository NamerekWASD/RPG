package com.company.rpgame.service;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.company.rpgame.action.ScheduleAction;
import com.company.rpgame.action.base.SequenceSchedule;
import com.github.czyzby.autumn.annotation.Component;
import lombok.val;

@Component
public class ActionService implements Disposable {


    private final Array<ScheduleAction> scheduleActions = new Array<>();

    public void scheduleAction(ScheduleAction scheduleAction) {
        scheduleActions.add(scheduleAction);
    }

    public void render(){
        for (val scheduleAction :
                new Array.ArrayIterator<>(scheduleActions)) {

            if(scheduleAction.isActing()){
                continue;
            }
            if(scheduleAction.isComplete()){
                System.out.println(scheduleActions.size);
                scheduleActions.removeValue(scheduleAction, false);
            }
            scheduleAction.begin();
        }
    }
    private float getLastActorActionTime(){
        return scheduleActions.size == 0 ? 0.1f : scheduleActions.peek().getActionLastTime();
    }
    public void appearFromRight(Actor actor) {
        Vector2 stagePositionOfParent = actor.localToStageCoordinates(
                new Vector2(actor.getParent().getX(), actor.getParent().getY()));

        scheduleAction(new SequenceSchedule
                (actor, Actions.sequence(Actions.hide(),
                        Actions.delay(getLastActorActionTime()/2),
                        Actions.moveBy( actor.getWidth(), 0),
                        Actions.show(),
                        Actions.moveTo(stagePositionOfParent.x, stagePositionOfParent.y, 0.5f, Interpolation.fastSlow))));
    }

    @Override
    public void dispose(){
        if (scheduleActions.size != 0) {
            scheduleActions.clear();
        }
    }

}
