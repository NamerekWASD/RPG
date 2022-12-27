package com.company.rpgame.service.controls.controlAbstract;

import com.company.rpgame.configuration.preferences.ControlData;
import com.company.rpgame.service.controls.Control;
import com.company.rpgame.service.controls.PlayerControl;
import com.company.rpgame.service.controls.controlAbstract.controlType.KeyboardControl;
import com.company.rpgame.service.controls.controlAbstract.controlType.TouchControl;

public enum ControlType {
    TOUCH {
        @Override
        public Control create(final ControlData data) {
            final PlayerControl playerControl = new TouchControl();
            playerControl.copy(data);
            return playerControl;
        }
    },
    KEYBOARD {
        @Override
        public Control create(final ControlData data) {
            final PlayerControl playerControl = new KeyboardControl();
            playerControl.copy(data);
            return playerControl;
        }
    };
    /** @param data serialized controls.
     * @return deserialized controller. */
    public abstract Control create(ControlData data);
}
