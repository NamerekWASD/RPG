package com.company.rpgame.service.controls;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.company.rpgame.configuration.preferences.ControlData;
import com.company.rpgame.service.controls.controlAbstract.ControlType;

public interface Control {
    void attachInputListener(final Stage stage);
    ControlType getType();
    ControlData toData();
    void copy(ControlData data);
    void reset();

}
