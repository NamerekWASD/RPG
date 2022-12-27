package com.company.rpgame.service.controls;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.company.rpgame.service.listeners.PlayerControlListener;

public interface PlayerControl extends Control {
    void attachInputListener(InputMultiplexer inputMultiplexer);
    void update(Viewport viewport, float gameX, float gameY);

    Vector2 getMovementDirection();

    void setControlListener(PlayerControlListener listener);
}
