package com.company.rpgame.configuration.preferences;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.company.rpgame.service.controls.controlAbstract.controlType.KeyboardControl;
import com.company.rpgame.service.controls.controlAbstract.controlType.TouchControl;
import com.github.czyzby.autumn.mvc.component.preferences.dto.AbstractPreference;
import com.github.czyzby.autumn.mvc.stereotype.preference.Property;
import com.github.czyzby.kiwi.util.gdx.GdxUtilities;


/** Allows to save controls in preferences. */
@Property("PlayerControls")
public class ControlPreference extends AbstractPreference<ControlData> {
    private final Json json = new Json();

    @Override
    public ControlData getDefault() {
       return GdxUtilities.isMobile() ? new TouchControl().toData() : new KeyboardControl().toData();
    }

    @Override
    public ControlData extractFromActor(final Actor actor) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected ControlData convert(final String rawPreference) {
        return json.fromJson(ControlData.class, Base64Coder.decodeString(rawPreference));
    }

    @Override
    protected String serialize(final ControlData preference) {
        return Base64Coder.encodeString(json.toJson(preference, Array.class, ControlData.class));
    }
}
