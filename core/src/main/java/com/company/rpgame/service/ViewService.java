package com.company.rpgame.service;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.company.rpgame.service.controls.ScreenControl;
import com.company.rpgame.service.listeners.ViewControlListener;
import com.github.czyzby.autumn.annotation.Component;
import com.github.czyzby.autumn.annotation.Inject;

@Component
public class ViewService implements Disposable {
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
    @Override
    public void dispose() {
        this.screenControl.clear();
    }

}
