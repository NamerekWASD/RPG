package com.company.rpgame.service.controls;

import com.company.rpgame.service.listeners.ViewControlListener;

public interface ScreenControl extends Control {

    void setCurrentView(ViewControlListener viewControlListeners);

    void addListeners(ViewControlListener... viewControlListeners);

    void clear();
}
