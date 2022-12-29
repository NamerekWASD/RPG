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
import lombok.val;

import static com.company.rpgame.helper.ArrayUtils.getArray;
import static com.company.rpgame.helper.ArrayUtils.getItems;
import static com.company.rpgame.service.controls.controlAbstract.controlType.PlayerControlKey.*;
import static com.company.rpgame.service.controls.controlAbstract.controlType.ScreenControlKey.Back;
import static com.company.rpgame.service.controls.controlAbstract.controlType.ScreenControlKey.InvokeInventory;

public abstract class AbstractButtonControl extends AbstractControl {

    protected IntSet pressedButtons = new IntSet();

    protected ObjectMap<PlayerControlKey, IntSet> playerKeys = new ObjectMap<>();
    protected ObjectMap<ScreenControlKey, IntSet> screenKeys = new ObjectMap<>();

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

    protected boolean updateViews(){
        ViewControlListener currentView = getCurrentView();

        if(isPressed(InvokeInventory)){
            ViewControlListener inventoryView = getListener(InventoryViewControllerControl.class);
            if(inventoryView == null){
                return false;
            }
            if (currentView.equals(inventoryView)) {
                inventoryView.backAction();
            } else if(currentView.equals(getListener(GameController.class))) {
                inventoryView.invoke();
            }
            return true;
        }
        if(isPressed(Back)){
            currentView.backAction();
            return true;
        }
        return false;
    }

    @Override
    public void update(final Viewport gameViewport, final float gameX, final float gameY) {
        // Button controls usually do not need relative position of controlled entity.
    }

    /** @param key button code.
     * @return true if button is currently pressed. */
    protected boolean isPressed(final PlayerControlKey key) {
        for (val comboValue:
                getItems(playerKeys.get(key))) {
            if(!getArray(pressedButtons).contains(comboValue)){
                return false;
            }
        }
        return true;
    }

    private void printPressedButtons() {
        for (val pressedButton :
                getItems(pressedButtons)) {
            System.out.print(pressedButton + " | ");
        }
        System.out.println();
    }

    protected boolean isPressed(final ScreenControlKey key) {
        int[] keys = getItems(screenKeys.get(key));
        System.out.println(keys.length);
        for (val comboValue:
                 keys) {
            if(!getArray(pressedButtons).contains(comboValue)){
                return false;
            }
        }
        return true;
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
    public void resetScreenKeys(){
        for (int key : getItems(pressedButtons)) {
            if(screenKeys.containsValue(key, true)){
                pressedButtons.remove(key);
            }
        }
    }

    public void setPlayerKey(PlayerControlKey key, IntSet value){
        if(playerKeys.containsKey(key)){
            playerKeys.remove(key);
        }
        playerKeys.put(key, value);
    }
    public void setScreenKeys(ScreenControlKey key, IntSet value){
        if(screenKeys.containsKey(key)){
            screenKeys.remove(key);
        }
        screenKeys.put(key, value);
    }
    public IntSet getPlayerKey(PlayerControlKey key){
        return playerKeys.get(key);
    }
    public IntSet getScreenKey(ScreenControlKey key){
        return screenKeys.get(key);
    }
    public PlayerControlKey getPlayerKey(IntSet value){
        return playerKeys.findKey(value, true);
    }
    public ScreenControlKey getScreenKey(IntSet value){
        return screenKeys.findKey(value, true);
    }
}
