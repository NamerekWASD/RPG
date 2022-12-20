package com.company.rpgame.controller.setting;

	import com.badlogic.gdx.Gdx;
	import com.badlogic.gdx.Graphics;
    import com.badlogic.gdx.graphics.GL20;
    import com.badlogic.gdx.scenes.scene2d.Actor;
	import com.badlogic.gdx.scenes.scene2d.Stage;
	import com.badlogic.gdx.utils.Array;
	import com.badlogic.gdx.utils.ObjectSet;
	import com.company.rpgame.service.DisplayService;
    import com.github.czyzby.autumn.annotation.Inject;
	import com.github.czyzby.autumn.mvc.component.ui.controller.ViewRenderer;
	import com.github.czyzby.autumn.mvc.component.ui.controller.impl.StandardViewShower;
	import com.github.czyzby.autumn.mvc.stereotype.View;
	import com.github.czyzby.kiwi.util.gdx.collection.GdxArrays;
	import com.github.czyzby.kiwi.util.gdx.collection.GdxSets;
	import com.github.czyzby.lml.annotation.LmlAction;
    import com.github.czyzby.lml.parser.action.ActionContainer;
	import com.github.czyzby.lml.util.LmlUtilities;

/** This is a settings dialog, which can be shown in any view by using "show:settings" LML action or - in Java code -
	 * through InterfaceService.showDialog(Class) method. Thanks to the fact that it implements ActionContainer, its methods
	 * will be available in the LML template. */
@View(id = "settings", value = "ui/templates/settings/settings.lml")
public class SettingsController extends StandardViewShower implements ViewRenderer, ActionContainer {
	@Inject
	private DisplayService displayService;

	/** @return array of serialized display modes' names. */
	@LmlAction("displayModes")
	public Array<String> getDisplayModes() {
		final ObjectSet<String> alreadyAdded = GdxSets.newSet(); // Removes duplicates.
		final Array<String> displayModes = GdxArrays.newArray(); // Keeps display modes sorted.
		for (final Graphics.DisplayMode mode : displayService.getDisplayModes()) {
			final String modeName = displayService.serialize(mode);
			if (alreadyAdded.contains(modeName)) {
				continue; // Same size already added.
			}
			displayModes.add(modeName);
			alreadyAdded.add(modeName);
		}
		return displayModes;
	}

	/** @param actor its ID must match name of a display mode. */
	@LmlAction("setFullscreen")
	public void setFullscreenMode(final Actor actor) {
		final String modeName = LmlUtilities.getActorId(actor);
		final Graphics.DisplayMode mode = displayService.deserialize(modeName);
		displayService.setFullscreen(mode);
	}

	/** Attempts to return to the original window size. */
	@LmlAction("resetFullscreen")
	public void setWindowedMode() {
		displayService.resetFullscreen();
	}

	@Override
	public void render(Stage stage, float delta) {
		stage.act(delta);
		Gdx.gl.glClearColor(.5f,.5f,.5f,1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
	}
}