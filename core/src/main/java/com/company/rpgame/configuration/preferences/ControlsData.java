package com.company.rpgame.configuration.preferences;

import com.badlogic.gdx.utils.ObjectMap;
import com.company.rpgame.service.controls.ControlType;
import com.company.rpgame.service.controls.controlType.PlayerControlKeys;

public class ControlsData {
    public ObjectMap<PlayerControlKeys, Integer> keys;
    /** Type of controls */
    private ControlType type;
    /** Additional data. Might be used for device ID. */
    public int index;

    public ControlsData() {
    }

    public ControlsData(final ControlType type) {
        this.type = type;
    }

    public ControlType getType() {
        return type;
    }
}
