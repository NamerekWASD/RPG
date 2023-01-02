package com.company.rpgame.service.controls.controlAbstract.controlType;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.IntSet;
import com.company.rpgame.service.controls.controlAbstract.AbstractButtonControl;
import com.company.rpgame.service.controls.controlAbstract.ControlType;

import static com.company.rpgame.service.controls.controlAbstract.controlType.ControlKey.*;

public class KeyboardControl extends AbstractButtonControl {
    private static final int initialCapacity = 2;

    public KeyboardControl() {
        // Initial settings:
        playerKeys.put(UP, initiateKey(Input.Keys.W));
        playerKeys.put(DOWN, initiateKey(Input.Keys.S));
        playerKeys.put(LEFT, initiateKey(Input.Keys.A));
        playerKeys.put(RIGHT, initiateKey(Input.Keys.D));
        playerKeys.put(JUMP, initiateKey(Input.Keys.SPACE));
        playerKeys.put(ATTACK, initiateKey(Input.Keys.J));

        screenKeys.put(InvokeInventory, initiateKey(Input.Keys.TAB, Input.Keys.E));
        screenKeys.put(Back, initiateKey(Input.Keys.ESCAPE));
}

    private IntSet initiateKey(int... keys) {
        IntSet set = new IntSet(initialCapacity);
        for (int key :
                keys) {
            set.add(key);
        }
        return set;
    }

    @Override
    public void attachInputListener(final Stage stage) {
        stage.addListener(new InputListener() {

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if(!pressedButtons.add(keycode)){
                    return false;
                }

                if(getListener() != null){
                    updateMovement();
                }

                if (getListeners().size != 0 && updateViews()) {
                    resetScreenKeys();
                    return true;
                }

                return false;
            }
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if(!pressedButtons.remove(keycode)){
                    return false;
                }

                if(getListener() != null){
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
