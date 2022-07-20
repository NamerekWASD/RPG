package com.company.rpgame.service.controls;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.company.rpgame.configuration.preferences.ControlsData;

public interface PlayerControl {
    /** @param inputMultiplexer can be used to attach an input processor. */
    void attachInputListener(InputMultiplexer inputMultiplexer);

    /**
     * @param gameX x position of controlled entity in game units.
     * @param gameY y position of controlled entity in game units.    */
    void update(Viewport viewport, float gameX, float gameY);

    /** @return current movement direction. Values should add up to [-1, 1]. */
    Vector2 getMovementDirection();

    /** @param listener should receive game events. */
    void setControlListener(ControlListener listener);

    /** @return serialized controls values that can be saved and read from. */
    ControlsData toData();

    /** @param data saved controls values that should be read. */
    void copy(ControlsData data);

    /** @return type of controls. */
    ControlType getType();

    /** Clears state variables. */
    void reset();
}
