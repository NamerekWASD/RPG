package com.company.rpgame.configuration.preferences;

import com.badlogic.gdx.utils.ObjectMap;
import com.company.rpgame.service.controls.controlAbstract.ControlType;
import com.company.rpgame.service.controls.controlAbstract.controlType.PlayerControlKey;
import com.company.rpgame.service.controls.controlAbstract.controlType.ScreenControlKey;

public class ControlData {
    public ObjectMap<PlayerControlKey, Integer> playerKeys;
    public ObjectMap<ScreenControlKey, Integer> screenKeys;
    private ControlType type;
    /** Additional data. Might be used for device ID. */
    public int index;

    public ControlData() {
    }

    public ControlData(final ControlType type) {
        this.type = type;
    }

    public ControlType getType() {
        return type;
    }
}
