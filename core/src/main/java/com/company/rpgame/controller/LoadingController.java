package com.company.rpgame.controller;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.github.czyzby.autumn.annotation.Inject;
import com.github.czyzby.autumn.mvc.component.asset.AssetService;
import com.github.czyzby.autumn.mvc.component.ui.controller.ViewRenderer;
import com.github.czyzby.autumn.mvc.stereotype.View;
import com.github.czyzby.lml.annotation.LmlActor;

	/** Thanks to View annotation, this class will be automatically found and initiated.
	 * This is the first application's view, shown right after the application starts. It will hide after all assests are
	 * loaded. */
	@View(value = "lml/loading.lml", first = true)
	public class LoadingController implements ViewRenderer {
		/** Will be injected automatically. Manages assets. Used to display loading progress. */
		@Inject private AssetService assetService;
		/** This is a widget injected from the loading.lml template. "loadingBar" is its ID. */
		@LmlActor("loadingBar") private ProgressBar loadingBar;

		// Since this class implements ViewRenderer, it can modify the way its view is drawn. Additionally to drawing the
		// stage, this view also updates assets manager and reads its progress.
		@Override
		public void render(final Stage stage, final float delta) {
			assetService.update();
			loadingBar.setValue(assetService.getLoadingProgress());
			stage.act(delta);
			stage.draw();
		}
	}