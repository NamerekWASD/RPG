package com.company.rpgame.service;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.company.rpgame.action.ScheduleAction;
import com.company.rpgame.service.controls.ScreenControl;
import com.company.rpgame.service.listeners.ViewControlListener;
import com.github.czyzby.autumn.annotation.Component;
import com.github.czyzby.autumn.annotation.Inject;
import lombok.val;

@Component
public class ViewService implements Disposable {
    private final Array<ScheduleAction> scheduleActions = new Array<>();
    @Inject
    private ControlService controlService;
    private ScreenControl screenControl;
    public void initiate(){
        screenControl = controlService.getScreenControl();
    }

    public void setCurrentListener(ViewControlListener currentViewListener) {
        this.screenControl.setCurrentView(currentViewListener);
    }
    public void addListeners(ViewControlListener... viewControlListeners){
        this.screenControl.addListeners(viewControlListeners);
    }
    public void attachListener(Stage stage){
        this.screenControl.attachInputListener(stage);
    }
    public void scheduleAction(ScheduleAction scheduleAction){
        scheduleActions.add(scheduleAction);
    }

    public void render(final float delta){
        for (val scheduleAction :
                new Array.ArrayIterator<>(scheduleActions)) {
            if(scheduleAction.isActing()){
                continue;
            }
            scheduleAction.begin(delta);
            checkInstance(scheduleAction);
        }
    }

    private void checkInstance(ScheduleAction action) {
        if(action.isComplete()){
            scheduleActions.removeValue(action, false);
            System.out.println(scheduleActions.size);
        }
    }

    @Override
    public void dispose() {
        if(scheduleActions.size != 0){
            scheduleActions.clear();
        }
        this.screenControl.clear();
    }

}
