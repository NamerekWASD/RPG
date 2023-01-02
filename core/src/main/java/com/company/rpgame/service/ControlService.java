package com.company.rpgame.service;

import com.company.rpgame.configuration.preferences.ControlPreference;
import com.company.rpgame.service.controls.Control;
import com.company.rpgame.service.controls.PlayerControl;
import com.company.rpgame.service.controls.ScreenControl;
import com.github.czyzby.autumn.annotation.Component;
import com.github.czyzby.autumn.annotation.Destroy;
import com.github.czyzby.autumn.annotation.Initiate;
import com.github.czyzby.autumn.annotation.Inject;
import com.github.czyzby.autumn.mvc.config.AutumnActionPriority;

@Component
public class ControlService {
    @Inject private ControlPreference controlPreference;
    private Control generalControl;

    @Initiate
    public void readControlsFromPreferences() {
        generalControl = controlPreference.getDefault().getType().create(controlPreference.getDefault());
    }

    @Destroy(priority = AutumnActionPriority.TOP_PRIORITY)
    public void saveControlsInPreferences() {
        controlPreference.set(generalControl.toData());
    }

    public void setControl(final Control control) {
        this.generalControl = control;
    }

    public PlayerControl getPlayerControl() {
        return (PlayerControl) generalControl;
    }

    public ScreenControl getScreenControl() {
        return (ScreenControl) generalControl;
    }

    public Control getGeneralControl() {
        return generalControl;
    }
}