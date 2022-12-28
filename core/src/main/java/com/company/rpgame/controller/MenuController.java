package com.company.rpgame.controller;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.company.rpgame.action.base.BaseSchedule;
import com.company.rpgame.action.base.SequenceSchedule;
import com.company.rpgame.actor.AnimationActor;
import com.company.rpgame.actor.components.AnimationTexture;
import com.company.rpgame.controller.dialog.SureDialogController;
import com.company.rpgame.service.ViewActionsService;
import com.github.czyzby.autumn.annotation.Inject;
import com.github.czyzby.autumn.mvc.component.ui.InterfaceService;
import com.github.czyzby.autumn.mvc.stereotype.View;
import com.github.czyzby.autumn.mvc.stereotype.ViewStage;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.annotation.LmlActor;
import lombok.val;


/** Thanks to View annotation, this class will be automatically found and initiated.
	 * This is application's main view, displaying a menu with several options. */
	@View(id = "menu", value = "lml/menu.lml")
	public class MenuController implements DefaultViewController {
	@Inject
	private InterfaceService interfaceService;
	@Inject
	private ViewActionsService viewActionsService;
	@LmlActor
	private Table buttonTable;
	@Inject
	private SureDialogController sureDialog;
	@ViewStage
	private Stage stage;
	private boolean initialized;


	@LmlAction
	public void startGame() {
		interfaceService.show(GameController.class);
	}

	@Override
	public void render(final Stage stage, final float delta) {
		if(!initialized){
			initialize();
			initialized = true;
			initMainBackground();
		}
		viewActionsService.render(delta);
		stage.act(delta);
		stage.draw();
	}

	private void initMainBackground() {
		AnimationActor background = new AnimationActor(new AnimationTexture("fire", true));
		stage.getRoot().addActor(background);
		background.clearActions();
		background.toBack();
		viewActionsService.scheduleAction(new SequenceSchedule(background,
				Actions.sequence(Actions.alpha(0f),
						Actions.sizeTo(stage.getWidth(), stage.getHeight()),
						Actions.fadeIn(0.2f, Interpolation.slowFast))));
	}

	private void initialize() {

		int amount = 200;
		int duration = 0;
		Actor buttonTableParent = buttonTable.getParent();

		viewActionsService.scheduleAction(new BaseSchedule(buttonTableParent, Actions.moveBy(0, -amount)));
		for (val button :
				new Array.ArrayIterator<>(buttonTable.getChildren())) {
			viewActionsService.scheduleAction(new SequenceSchedule
					(button, Actions.sequence(Actions.delay((float)duration++/5),
					Actions.moveBy(0, amount, 1, Interpolation.fastSlow))));
		}

		for (val button :
				new Array.ArrayIterator<>(buttonTable.getChildren())) {
			viewActionsService.scheduleAction(new SequenceSchedule(button, Actions.sequence(Actions.delay(duration),Actions.moveBy(0, -amount))));
		}

		viewActionsService.scheduleAction(new SequenceSchedule(buttonTableParent,
				Actions.sequence(Actions.delay(duration), Actions.moveBy(0, amount))));

		viewActionsService.initiate();
		viewActionsService.attachListener(stage);
		viewActionsService.addListeners(this, sureDialog);
		viewActionsService.setCurrentListener(this);
	}

	@Override
	public Class<?> getViewClass() {
		return this.getClass();
	}

	@Override
	public void invoke() {
		interfaceService.show(this.getClass());
		viewActionsService.setCurrentListener(this);
	}

	@Override
	public void backAction() {
		showSureDialog();
	}

	@LmlAction
	public void showSureDialog() {
		interfaceService.showDialog(sureDialog.getClass());
		viewActionsService.setCurrentListener(sureDialog);
	}
}