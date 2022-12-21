package com.company.rpgame.controller.setting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.github.czyzby.autumn.mvc.component.ui.controller.ViewRenderer;
import com.github.czyzby.autumn.mvc.stereotype.View;
import com.github.czyzby.autumn.mvc.stereotype.preference.sfx.MusicEnabled;
import com.github.czyzby.autumn.mvc.stereotype.preference.sfx.MusicVolume;
import com.github.czyzby.autumn.mvc.stereotype.preference.sfx.SoundEnabled;
import com.github.czyzby.autumn.mvc.stereotype.preference.sfx.SoundVolume;

import static com.company.rpgame.configuration.Configuration.PREFERENCES;

@View(id="volume", value="lml/settings/volume.lml")
public class VolumeController implements ViewRenderer {
    /** These sound-related fields allow MusicService to store settings in preferences file. Sound preferences will be
     * automatically saved when the application closes and restored the next time it's turned on. Sound-related methods
     * methods will be automatically added to LML templates - see settings.lml template. */
    @SoundVolume(preferences = PREFERENCES) private final String soundVolume = "soundVolume";
    @SoundEnabled(preferences = PREFERENCES) private final String soundEnabled = "soundOn";
    @MusicVolume(preferences = PREFERENCES) private final String musicVolume = "musicVolume";
    @MusicEnabled(preferences = PREFERENCES) private final String musicEnabledPreference = "musicOn";

    @Override
    public void render(Stage stage, float delta) {
        stage.act(delta);
        Gdx.gl.glClearColor(.5f,.5f,.5f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }
}
