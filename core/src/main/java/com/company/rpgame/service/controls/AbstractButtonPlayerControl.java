package com.company.rpgame.service.controls;

import com.badlogic.gdx.utils.IntSet;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.company.rpgame.configuration.preferences.ControlsData;
import com.company.rpgame.service.controls.controlType.PlayerControlKeys;

import static com.company.rpgame.service.controls.controlType.PlayerControlKeys.*;

public abstract class AbstractButtonPlayerControl extends AbstractPlayerControl {

    protected IntSet pressedButtons = new IntSet();

    protected ObjectMap<PlayerControlKeys, Integer> keys = new ObjectMap<>();

    /** Updates current movement according to button states. */
    protected void updateMovement() {
        if (pressedButtons.size == 0) {
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

    @Override
    public void update(final Viewport gameViewport, final float gameX, final float gameY) {
        // Button controls usually do not need relative position of controlled entity.
    }

    /** @param key button code.
     * @return true if button is currently pressed. */
    protected boolean isPressed(final PlayerControlKeys key) {
        return pressedButtons.contains(getKey(key));
    }

    @Override
    public ControlsData toData() {
        final ControlsData data = new ControlsData(getType());
        data.keys = keys;
        return data;
    }

    @Override
    public void copy(final ControlsData data) {
        this.keys = data.keys;
    }

    @Override
    public void reset() {
        super.reset();
        pressedButtons.clear();
    }
    public void setKey(PlayerControlKeys key, int value){
        if(keys.containsKey(key)){
            keys.remove(key);
        }
        keys.put(key, value);
    }
    public int getKey(PlayerControlKeys key){
        return keys.get(key);
    }
    public PlayerControlKeys getKey(int value){
        return keys.findKey(value, true);
    }
}
