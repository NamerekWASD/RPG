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
    private PlayerControl playerControl;
    private ScreenControl screenControl;

    @Initiate
    public void readControlsFromPreferences() {
        Control control = controlPreference.getDefault().getType().create(controlPreference.getDefault());
        playerControl = (PlayerControl) control;
        screenControl = (ScreenControl) control;
    }

    @Destroy(priority = AutumnActionPriority.TOP_PRIORITY)
    public void saveControlsInPreferences() {
        controlPreference.set(playerControl.toData());
        controlPreference.set(screenControl.toData());
    }

    public void setPlayerControl(final PlayerControl playerControl) {
        this.playerControl = playerControl;
    }

    public void setScreenControl(final ScreenControl screenControl) {
        this.screenControl = screenControl;
    }
    public PlayerControl getPlayerControl() {
        return playerControl;
    }

    public ScreenControl getScreenControl() {
        return screenControl;
    }
}