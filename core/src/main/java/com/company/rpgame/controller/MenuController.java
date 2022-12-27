package com.company.rpgame.controller;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.company.rpgame.action.base.BaseSchedule;
import com.company.rpgame.action.base.SequenceSchedule;
import com.company.rpgame.actor.AnimationActor;
import com.company.rpgame.actor.components.AnimationTexture;
import com.company.rpgame.service.ViewActionsService;
import com.github.czyzby.autumn.annotation.Inject;
import com.github.czyzby.autumn.mvc.component.ui.InterfaceService;
import com.github.czyzby.autumn.mvc.stereotype.View;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.annotation.LmlActor;
import lombok.val;


/** Thanks to View annotation, this class will be automatically found and initiated.
	 * This is application's main view, displaying a menu with several options. */
	@View(id = "menu", value = "lml/menu.lml", themes = "music/theme.ogg")
	public class MenuController implements DefaultViewController {
	@Inject
	private InterfaceService interfaceService;
	@Inject
	private ViewActionsService rendererService;
	@LmlActor
	private Table buttonTable;
	private boolean initialized;


	@LmlAction
	public void startGame() {
		interfaceService.show(GameController.class);
	}

	@Override
	public void render(final Stage stage, final float delta) {
		if(!initialized){
			initialize();
			stage.setDebugAll(true);
			initialized = true;
			initMainBackground(stage);
		}
		rendererService.render(delta);
		stage.act(delta);
		stage.draw();
	}

	private void initMainBackground(Stage stage) {
		AnimationActor background = new AnimationActor(new AnimationTexture("fire", true));
		Vector2 stageCenter = new Vector2(stage.getWidth()/2, stage.getHeight()/2);
		background.setPosition(stageCenter.x - background.getWidth()/2, stageCenter.y - background.getHeight()/2);
		stage.getRoot().addActor(background);
		background.clearActions();
		rendererService.scheduleAction(new SequenceSchedule(background, Actions.sequence(Actions.alpha(0f), Actions.fadeIn(0.2f, Interpolation.slowFast))));
	}

	int amount = 200;
	private void initialize() {

		int duration = 0;
		Actor buttonTableParent = buttonTable.getParent();


		rendererService.scheduleAction(new BaseSchedule(buttonTableParent, Actions.moveBy(0, -amount)));
		for (val button :
				new Array.ArrayIterator<>(buttonTable.getChildren())) {
			rendererService.scheduleAction(new SequenceSchedule
					(button, Actions.sequence(Actions.delay((float)duration++/5),
					Actions.moveBy(0, amount, 1, Interpolation.fastSlow))));
		}

		for (val button :
				new Array.ArrayIterator<>(buttonTable.getChildren())) {
			rendererService.scheduleAction(new SequenceSchedule(button, Actions.sequence(Actions.delay(duration),Actions.moveBy(0, -amount))));
		}

		rendererService.scheduleAction(new SequenceSchedule(buttonTableParent,
				Actions.sequence(Actions.delay(duration), Actions.moveBy(0, amount))));

	}

	@Override
	public Class<?> getViewClass() {
		return this.getClass();
	}

	@Override
	public void invoke() {
		interfaceService.show(this.getClass());
	}

	@Override
	public void backAction() {

	}
}