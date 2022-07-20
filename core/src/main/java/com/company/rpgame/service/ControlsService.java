package com.company.rpgame.service;

import com.company.rpgame.configuration.preferences.ControlsData;
import com.company.rpgame.configuration.preferences.ControlsPreference;
import com.company.rpgame.service.controls.PlayerControl;
import com.github.czyzby.autumn.annotation.Component;
import com.github.czyzby.autumn.annotation.Destroy;
import com.github.czyzby.autumn.annotation.Initiate;
import com.github.czyzby.autumn.annotation.Inject;
import com.github.czyzby.autumn.mvc.config.AutumnActionPriority;

@Component
public class ControlsService {
    @Inject private ControlsPreference preference;
    private PlayerControl playerControl;

    @Initiate
    public void readControlsFromPreferences() {
        playerControl = preference.getDefault().type.create(preference.getDefault());
    }

    @Destroy(priority = AutumnActionPriority.TOP_PRIORITY)
    public void saveControlsInPreferences() {
        ControlsData data =  playerControl.toData();
        preference.set(data);
    }

    /** @param playerControl will be assigned to the player. */
    public void setControl(final PlayerControl playerControl) {
        this.playerControl = playerControl;
    }

    /** @return controllers assigned to player. */
    public PlayerControl getControl() {
        return playerControl;
    }
}