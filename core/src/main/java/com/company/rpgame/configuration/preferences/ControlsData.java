package com.company.rpgame.configuration.preferences;

import com.badlogic.gdx.utils.ObjectMap;
import com.company.rpgame.service.controls.ControlType;

public class ControlsData {
    public ObjectMap<String, Integer> keys;
    /** Type of controls */
    public ControlType type;
    /** Additional data. Might be used for device ID. */
    public int index;

    public ControlsData() {
    }

    public ControlsData(final ControlType type) {
        this.type = type;
    }
}
