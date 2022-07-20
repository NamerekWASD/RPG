package com.company.rpgame.configuration.preferences;

import com.company.rpgame.service.controls.ControlType;

public class ControlsData {
    /** Up movement shortcut. */
    public int up;
    /** Down movement shortcut. */
    public int down;
    /** Left movement shortcut. */
    public int left;
    /** Right movement shortcut. */
    public int right;
    /** Jump shortcut. */
    public int jump;
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
