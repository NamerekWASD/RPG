package com.company.rpgame.service.controls.controlAbstract.controlType;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.IntSet;
import com.company.rpgame.service.controls.controlAbstract.AbstractButtonControl;
import com.company.rpgame.service.controls.controlAbstract.ControlType;

import static com.company.rpgame.service.controls.controlAbstract.controlType.PlayerControlKey.*;
import static com.company.rpgame.service.controls.controlAbstract.controlType.ScreenControlKey.Back;
import static com.company.rpgame.service.controls.controlAbstract.controlType.ScreenControlKey.InvokeInventory;

public class KeyboardControl extends AbstractButtonControl {
    private static final int initialCapacity = 2;

    public KeyboardControl() {
        // Initial settings:
        setPlayerKey(UP, initiateKey(Input.Keys.W));
        setPlayerKey(DOWN, initiateKey(Input.Keys.S));
        setPlayerKey(LEFT, initiateKey(Input.Keys.A));
        setPlayerKey(RIGHT, initiateKey(Input.Keys.D));
        setPlayerKey(JUMP, initiateKey(Input.Keys.SPACE));

        setScreenKeys(InvokeInventory, initiateKey(Input.Keys.TAB, Input.Keys.E));
        setScreenKeys(Back, initiateKey(Input.Keys.ESCAPE));
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
                if(getListeners().size != 0){
                    if(updateViews()){
                        resetScreenKeys();
                        return true;
                    }
                }
                if(getListener() != null){
                    updateMovement();
                    return true;
                }
                return false;
            }
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if(!pressedButtons.remove(keycode)){
                    return false;
                }
                System.out.println("out");
                updateMovement();
                return true;
            }
        });
    }


    @Override
    public ControlType getType() {
        return ControlType.KEYBOARD;
    }

}
