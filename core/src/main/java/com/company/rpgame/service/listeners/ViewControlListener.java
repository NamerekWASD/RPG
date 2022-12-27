package com.company.rpgame.service.listeners;

public interface ViewControlListener {
    Class<?> getViewClass();
    void invoke();
    void backAction();
}
