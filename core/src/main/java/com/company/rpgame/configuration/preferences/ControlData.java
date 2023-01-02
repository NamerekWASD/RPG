package com.company.rpgame.configuration.preferences;

import com.badlogic.gdx.utils.IntSet;
import com.badlogic.gdx.utils.ObjectMap;
import com.company.rpgame.service.controls.controlAbstract.ControlType;
import com.company.rpgame.service.controls.controlAbstract.controlType.ControlKey;

public class ControlData {
    public ObjectMap<ControlKey, IntSet> playerKeys;
    public ObjectMap<ControlKey, IntSet> screenKeys;
    private final ControlType type;
    /** Additional data. Might be used for device ID. */
    public int index;

    public ControlData(final ControlType type) {
        this.type = type;
    }

    public ControlType getType() {
        return type;
    }
}
