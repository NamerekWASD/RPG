package com.company.rpgame.service.controls.controlType;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.company.rpgame.service.controls.AbstractButtonPlayerControl;
import com.company.rpgame.service.controls.ControlType;

public class KeyboardPlayerControl extends AbstractButtonPlayerControl {
    public KeyboardPlayerControl() {
    // Initial settings:
    up = Input.Keys.W;
    down = Input.Keys.S;
    left = Input.Keys.A;
    right = Input.Keys.D;
    jump = Input.Keys.SPACE;
}

    @Override
    public void attachInputListener(final InputMultiplexer inputMultiplexer) {
        inputMultiplexer.addProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(final int keycode) {
                if (keycode == up || keycode == down || keycode == left || keycode == right) {
                    pressedButtons.add(keycode);
                    updateMovement();
                    return true;
                } else if (keycode == jump) {
                    getListener().jump();
                    return true;
                }
                return false;
            }

            @Override
            public boolean keyUp(final int keycode) {
                if (keycode == up || keycode == down || keycode == left || keycode == right) {
                    pressedButtons.remove(keycode);
                    updateMovement();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public ControlType getType() {
        return ControlType.KEYBOARD;
    }
}
