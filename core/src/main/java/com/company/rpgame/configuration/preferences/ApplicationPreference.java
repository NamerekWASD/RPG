package com.company.rpgame.configuration.preferences;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.github.czyzby.autumn.mvc.component.preferences.dto.AbstractPreference;
import com.github.czyzby.autumn.mvc.stereotype.preference.Property;

@Property
public class ApplicationPreference extends AbstractPreference<ApplicationData> {
    private final Json json = new Json();
    @Override
    public ApplicationData getDefault() {
        return new ApplicationData();
    }

    @Override
    public ApplicationData extractFromActor(final Actor actor) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected ApplicationData convert(final String rawPreference) {
        return json.fromJson(ApplicationData.class, Base64Coder.decodeString(rawPreference));
    }

    @Override
    protected String serialize(final ApplicationData preference) {
        return Base64Coder.encodeString(json.toJson(preference, Array.class, ApplicationData.class));
    }
}
