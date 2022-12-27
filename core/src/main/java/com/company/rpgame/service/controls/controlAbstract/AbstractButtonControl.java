package com.company.rpgame.service.controls.controlAbstract;

import com.badlogic.gdx.utils.IntSet;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.company.rpgame.configuration.preferences.ControlData;
import com.company.rpgame.controller.GameController;
import com.company.rpgame.controller.dialog.InventoryViewControllerControl;
import com.company.rpgame.service.controls.controlAbstract.controlType.PlayerControlKey;
import com.company.rpgame.service.controls.controlAbstract.controlType.ScreenControlKey;
import com.company.rpgame.service.listeners.ViewControlListener;

import static com.company.rpgame.service.controls.controlAbstract.controlType.PlayerControlKey.*;
import static com.company.rpgame.service.controls.controlAbstract.controlType.ScreenControlKey.Back;
import static com.company.rpgame.service.controls.controlAbstract.controlType.ScreenControlKey.InvokeInventory;

public abstract class AbstractButtonControl extends AbstractControl {

    protected IntSet pressedButtons = new IntSet();

    protected ObjectMap<PlayerControlKey, Integer> playerKeys = new ObjectMap<>();
    protected ObjectMap<ScreenControlKey, Integer> screenKeys = new ObjectMap<>();

    /** Updates current movement according to button states. */
    protected void updateMovement() {
        stop();
        if (isPressed(LEFT) && isPressed(RIGHT)) {
            stop();
        } else if (isPressed(LEFT)) {
            movement.set(-1f, 0f);
        } else if (isPressed(RIGHT)) {
            movement.set(1f, 0f);
        }
        if (isPressed(JUMP)) {
            getListener().jump();
        }
    }

    protected void updateListeners(){
        ViewControlListener currentView = getCurrentView();

        if(isPressed(InvokeInventory)){
            ViewControlListener inventoryView = getListener(InventoryViewControllerControl.class);
            if (currentView.equals(inventoryView)) {
                inventoryView.backAction();
            } else if(currentView.equals(getListener(GameController.class))) {
                inventoryView.invoke();
            }
            return;
        }
        if(isPressed(Back)){
            currentView.backAction();
        }
    }

    @Override
    public void update(final Viewport gameViewport, final float gameX, final float gameY) {
        // Button controls usually do not need relative position of controlled entity.
    }

    /** @param key button code.
     * @return true if button is currently pressed. */
    protected boolean isPressed(final PlayerControlKey key) {
        return pressedButtons.contains(getPlayerKey(key));
    }
    protected boolean isPressed(final ScreenControlKey key) {
        return pressedButtons.contains(getScreenKey(key));
    }
    @Override
    public ControlData toData() {
        final ControlData data = new ControlData(getType());
        data.playerKeys = playerKeys;
        data.screenKeys = screenKeys;
        return data;
    }

    @Override
    public void copy(final ControlData data) {
        this.playerKeys = data.playerKeys;
        this.screenKeys = data.screenKeys;
    }

    @Override
    public void reset() {
        super.reset();
        pressedButtons.clear();
    }
    public void setPlayerKey(PlayerControlKey key, int value){
        if(playerKeys.containsKey(key)){
            playerKeys.remove(key);
        }
        playerKeys.put(key, value);
    }
    public void setScreenKeys(ScreenControlKey key, int value){
        if(screenKeys.containsKey(key)){
            screenKeys.remove(key);
        }
        screenKeys.put(key, value);
    }
    public int getPlayerKey(PlayerControlKey key){
        return playerKeys.get(key);
    }
    public int getScreenKey(ScreenControlKey key){
        return screenKeys.get(key);
    }
    public PlayerControlKey getPlayerKey(int value){
        return playerKeys.findKey(value, true);
    }
    public ScreenControlKey getScreenKey(int value){
        return screenKeys.findKey(value, true);
    }
}
