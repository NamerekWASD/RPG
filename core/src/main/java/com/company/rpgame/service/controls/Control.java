package com.company.rpgame.service.controls;

import com.company.rpgame.configuration.preferences.ControlData;
import com.company.rpgame.service.controls.controlAbstract.ControlType;

public interface Control {

    ControlType getType();
    ControlData toData();
    void copy(ControlData data);
    void reset();

}
