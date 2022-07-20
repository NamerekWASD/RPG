package com.company.rpgame.service.controls;

import com.company.rpgame.configuration.preferences.ControlsData;
import com.company.rpgame.service.controls.controlType.KeyboardPlayerControl;
import com.company.rpgame.service.controls.controlType.TouchPlayerControl;

public enum ControlType {
    TOUCH {
        @Override
        public PlayerControl create(final ControlsData data) {
            return new TouchPlayerControl();
        }
    },
    KEYBOARD {
        @Override
        public PlayerControl create(final ControlsData data) {
            final PlayerControl playerControl = new KeyboardPlayerControl();
            playerControl.copy(data);
            return playerControl;
        }
    };
    /** @param data serialized controls.
     * @return deserialized controller. */
    public abstract PlayerControl create(ControlsData data);
}
