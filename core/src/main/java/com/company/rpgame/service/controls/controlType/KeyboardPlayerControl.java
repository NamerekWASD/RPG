package com.company.rpgame.service.controls.controlType;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.company.rpgame.service.controls.AbstractButtonPlayerControl;
import com.company.rpgame.service.controls.ControlType;

import static com.company.rpgame.service.controls.controlType.PlayerControlKeys.*;

public class KeyboardPlayerControl extends AbstractButtonPlayerControl {
    public KeyboardPlayerControl() {
    // Initial settings:
    keys.put(UP, Input.Keys.W);
    keys.put(DOWN, Input.Keys.S);
    keys.put(LEFT, Input.Keys.A);
    keys.put(RIGHT, Input.Keys.D);
    keys.put(JUMP, Input.Keys.SPACE);
}

    @Override
    public void attachInputListener(final InputMultiplexer inputMultiplexer) {

        inputMultiplexer.addProcessor(new InputAdapter() {

            @Override
            public boolean keyDown(final int keycode) {
                if (keys.containsValue(keycode, true)) {
                    System.out.println("Pressed: " + keycode);
                    pressedButtons.add(keycode);
                    updateMovement();
                    return true;
                }
                return false;
            }

            @Override
            public boolean keyUp(final int keycode) {
                if (pressedButtons.contains(keycode)) {
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
