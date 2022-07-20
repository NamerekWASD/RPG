package com.company.rpgame.configuration.preferences;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.company.rpgame.service.controls.controlType.KeyboardPlayerControl;
import com.company.rpgame.service.controls.controlType.TouchPlayerControl;
import com.github.czyzby.autumn.mvc.component.preferences.dto.AbstractPreference;
import com.github.czyzby.autumn.mvc.stereotype.preference.Property;
import com.github.czyzby.kiwi.util.gdx.GdxUtilities;


/** Allows to save controls in preferences. */
@Property("Controls")
public class ControlsPreference extends AbstractPreference<ControlsData> {
    private final Json json = new Json();

    @Override
    public ControlsData getDefault() {
        // First player defaults to touch (on mobile) or keyboard (on desktop) controls.
        return GdxUtilities.isMobile() ? new TouchPlayerControl().toData() : new KeyboardPlayerControl().toData();
    }

    @Override
    public ControlsData extractFromActor(final Actor actor) {
        throw new UnsupportedOperationException();
    }

    @Override
    @SuppressWarnings("unchecked")
    protected ControlsData convert(final String rawPreference) {
        return json.fromJson(ControlsData.class, Base64Coder.decodeString(rawPreference));
    }

    @Override
    protected String serialize(final ControlsData preference) {
        return Base64Coder.encodeString(json.toJson(preference, Array.class, ControlsData.class));
    }
}
