package com.company.rpgame.service.controls;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.company.rpgame.service.listeners.ViewControlListener;

public interface ScreenControl extends Control {
    void attachInputListener(final Stage stage);

    void setCurrentView(ViewControlListener viewControlListeners);

    void addListeners(ViewControlListener... viewControlListeners);

    void clear();
}
