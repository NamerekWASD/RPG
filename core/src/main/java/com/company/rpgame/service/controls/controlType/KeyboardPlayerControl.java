package com.company.rpgame.service.controls.controlType;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.company.rpgame.configuration.preferences.ControlsData;
import com.company.rpgame.service.controls.AbstractButtonPlayerControl;
import com.company.rpgame.service.controls.ControlType;

public class KeyboardPlayerControl extends AbstractButtonPlayerControl {
    public KeyboardPlayerControl() {
    // Initial settings:
    keys.put("up", Input.Keys.W);
    keys.put("down", Input.Keys.S);
    keys.put("left", Input.Keys.A);
    keys.put("right", Input.Keys.D);
    keys.put("jump", Input.Keys.SPACE);
    keys.put("hurt", Input.Keys.ENTER);
}

    @Override
    public void attachInputListener(final InputMultiplexer inputMultiplexer) {
        inputMultiplexer.addProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(final int keycode) {
                if (keycode == getKey("up") || keycode == getKey("down") ||
                        keycode == getKey("left") || keycode == getKey("right")) {
                    pressedButtons.add(keycode);
                    updateMovement();
                    return true;
                } else if (keycode == getKey("jump")) {
                    getListener().jump();
                    return true;
                } else if (keycode == getKey("hurt")) {
                    getListener().hurt();
                    return true;
                }
                return false;
            }

            @Override
            public boolean keyUp(final int keycode) {
                if (keycode == getKey("up") || keycode == getKey("down") ||
                        keycode == getKey("left") || keycode == getKey("right")) {
                    pressedButtons.remove(keycode);
                    updateMovement();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void copy(ControlsData data) {

    }

    @Override
    public ControlType getType() {
        return ControlType.KEYBOARD;
    }
}
