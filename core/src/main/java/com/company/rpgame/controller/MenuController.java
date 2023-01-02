package com.company.rpgame.controller;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.company.rpgame.action.base.SequenceSchedule;
import com.company.rpgame.actor.AnimationActor;
import com.company.rpgame.actor.components.AnimationTexture;
import com.company.rpgame.controller.dialog.SureDialogController;
import com.company.rpgame.service.ActionService;
import com.company.rpgame.service.ViewService;
import com.github.czyzby.autumn.annotation.Inject;
import com.github.czyzby.autumn.mvc.component.ui.InterfaceService;
import com.github.czyzby.autumn.mvc.component.ui.controller.impl.StandardViewShower;
import com.github.czyzby.autumn.mvc.stereotype.View;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.annotation.LmlActor;
import lombok.val;


/** Thanks to View annotation, this class will be automatically found and initiated.
	 * This is application's main view, displaying a menu with several options. */
	@View(id = "menu", value = "lml/menu.lml")
	public class MenuController  extends StandardViewShower implements DefaultViewController, Disposable {
	@Inject
	private InterfaceService interfaceService;
	@Inject
	private ViewService viewService;
	@Inject
	private ActionService actionService;
	@Inject
	private SureDialogController sureDialog;
	@LmlActor("start;settings;exit")
	private Array<TextButton> textButtons;
	private AnimationActor background;
	private Stage stage;

	@LmlAction
	public void startGame() {
		interfaceService.show(GameController.class);
	}
	@LmlAction
	public void showSureDialog() {
		interfaceService.showDialog(sureDialog.getClass());
		viewService.setCurrentListener(sureDialog);
	}

	@Override
	public void render(final Stage stage, final float delta) {
		stage.act(delta);
		stage.draw();
		actionService.render();
	}



	@Override
	public Class<?> getViewClass() {
		return this.getClass();
	}

	@Override
	public void invoke() {
		interfaceService.show(this.getClass());
		viewService.setCurrentListener(this);
	}

	@Override
	public void backAction() {
		showSureDialog();
	}

	@Override
	public void dispose() {
		stage.dispose();
		viewService.dispose();
		interfaceService.destroy(this.getClass());
	}

	@Override
	public void show(Stage stage, Action action) {
		super.show(stage, action);
		stage.setDebugAll(true);
		stage.draw();
		stage.act();
		this.stage = stage;
		initialize();
		initMainBackground();
	}

	private void initialize() {
		for (val button :
				new Array.ArrayIterator<>(textButtons)) {
			actionService.appearFromRight(button);
		}

		viewService.initiate();
		viewService.attachListener(stage);
		viewService.addListeners(this, sureDialog);
		viewService.setCurrentListener(this);
	}

	private void initMainBackground() {
		if(background == null){
			background = new AnimationActor(new AnimationTexture("fire", true));
			stage.addActor(background);
			background.toBack();
			actionService.scheduleAction(new SequenceSchedule(background,
					Actions.sequence(Actions.sizeTo(stage.getWidth(), stage.getHeight()),
							Actions.fadeIn(1f, Interpolation.circleIn))));
		}
	}

	@Override
	public void hide(Stage stage, Action action) {
		super.hide(stage, action);
	}
}