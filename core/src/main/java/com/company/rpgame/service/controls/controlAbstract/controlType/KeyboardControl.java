package com.company.rpgame.service.controls.controlAbstract.controlType;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.company.rpgame.service.controls.controlAbstract.AbstractButtonControl;
import com.company.rpgame.service.controls.controlAbstract.ControlType;

import static com.company.rpgame.service.controls.controlAbstract.controlType.PlayerControlKey.*;
import static com.company.rpgame.service.controls.controlAbstract.controlType.ScreenControlKey.Back;
import static com.company.rpgame.service.controls.controlAbstract.controlType.ScreenControlKey.InvokeInventory;

public class KeyboardControl extends AbstractButtonControl {
    public KeyboardControl() {
        // Initial settings:
        setPlayerKey(UP, Input.Keys.W);
        setPlayerKey(DOWN, Input.Keys.S);
        setPlayerKey(LEFT, Input.Keys.A);
        setPlayerKey(RIGHT, Input.Keys.D);
        setPlayerKey(JUMP, Input.Keys.SPACE);

        setScreenKeys(InvokeInventory, Input.Keys.TAB);
        setScreenKeys(Back, Input.Keys.ESCAPE);
}
    @Override
    public void attachInputListener(final Stage stage) {
        stage.addListener(new InputListener() {
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if (screenKeys.containsValue(keycode, true)) {
                    pressedButtons.add(keycode);
                    updateListeners();
                    pressedButtons.remove(keycode);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void attachInputListener(final InputMultiplexer inputMultiplexer) {

        inputMultiplexer.addProcessor(new InputAdapter() {

            @Override
            public boolean keyDown(final int keycode) {
                if (playerKeys.containsValue(keycode, true)) {
                    pressedButtons.add(keycode);
                    updateMovement();
                    return true;
                }
                return false;
            }

            @Override
            public boolean keyUp(final int keycode) {
                if (pressedButtons.contains(keycode)) {
                    System.out.println(pressedButtons.remove(keycode));
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
