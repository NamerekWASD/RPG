package com.company.rpgame.service.controls.controlAbstract;

import com.badlogic.gdx.utils.IntSet;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.company.rpgame.configuration.preferences.ControlData;
import com.company.rpgame.controller.dialog.InventoryViewControllerControl;
import com.company.rpgame.service.controls.controlAbstract.controlType.ControlKey;
import com.company.rpgame.service.listeners.ViewControlListener;
import lombok.val;

import static com.company.rpgame.helper.ArrayUtils.getItems;
import static com.company.rpgame.service.controls.controlAbstract.controlType.ControlKey.*;

public abstract class AbstractButtonControl extends AbstractControl {

    protected IntSet pressedButtons = new IntSet();

    protected ObjectMap<ControlKey, IntSet> playerKeys = new ObjectMap<>();
    protected ObjectMap<ControlKey, IntSet> screenKeys = new ObjectMap<>();

    /** Updates current movement according to button states. */
    protected void updateMovement() {
        stop();
        if (isPressed(playerKeys, LEFT) && isPressed(playerKeys, RIGHT)) {
            stop();
        } else if (isPressed(playerKeys, LEFT)) {
            movement.set(-1f, 0f);
        } else if (isPressed(playerKeys, RIGHT)) {
            movement.set(1f, 0f);
        }
        if (isPressed(playerKeys, JUMP)) {
            getListener().jump();
        }
    }

    protected boolean updateViews(){
        ViewControlListener currentView = getCurrentView();

        if(isPressed(screenKeys, InvokeInventory)){
            ViewControlListener inventoryView = getListener(InventoryViewControllerControl.class);
            if(inventoryView == null){
                return false;
            }
            inventoryView.invoke();
        }
        if(isPressed(screenKeys, Back)){
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

    private boolean isPressed(ObjectMap<ControlKey, IntSet> keyMap, ControlKey key) {
        for (val comboValue:
                getItems(keyMap.get(key))) {
            if(!pressedButtons.contains(comboValue)){
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

    public void setNewValue(ControlKey key, IntSet value){
        putKey(playerKeys, key, value);
        putKey(screenKeys, key, value);
    }

    private static void putKey(ObjectMap<ControlKey, IntSet> screenKeys, ControlKey key, IntSet value) {
        if (screenKeys.containsKey(key)) {
            screenKeys.put(key, value);
        }
    }

    public IntSet getKey(ControlKey key){
        if(playerKeys.containsKey(key)){
            return playerKeys.get(key);
        }
        if(screenKeys.containsKey(key)){
            return screenKeys.get(key);
        }
        return null;
    }
    public ControlKey getKey(IntSet value){
        if(playerKeys.containsValue(value, true)){
            return playerKeys.findKey(value, true);
        }
        if(screenKeys.containsValue(value, true)){
            return screenKeys.findKey(value, true);
        }
        return null;
    }
}
