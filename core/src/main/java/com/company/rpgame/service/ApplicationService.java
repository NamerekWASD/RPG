package com.company.rpgame.service;

import com.badlogic.gdx.Gdx;
import com.company.rpgame.configuration.preferences.ApplicationData;
import com.company.rpgame.configuration.preferences.ApplicationPreference;
import com.github.czyzby.autumn.annotation.Component;
import com.github.czyzby.autumn.annotation.Initiate;
import com.github.czyzby.autumn.annotation.Inject;

@Component
public class ApplicationService {
    @Inject
    private ApplicationPreference applicationPreference;

    @Initiate
    private void initiateApplicationConfigurations() {
        ApplicationData applicationData = applicationPreference.getDefault();
        if (applicationData.isFullScreen){
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        }
    }
}
