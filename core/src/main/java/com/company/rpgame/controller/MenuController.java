package com.company.rpgame.controller;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.company.rpgame.action.base.SequenceSchedule;
import com.company.rpgame.actor.AnimationActor;
import com.company.rpgame.actor.components.AnimationTexture;
import com.company.rpgame.controller.dialog.SureDialogController;
import com.company.rpgame.service.ActionService;
import com.company.rpgame.service.ViewService;
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
	private ViewService viewService;
	@Inject
	private ActionService actionService;
	@LmlActor
	private TextButton start;
	@LmlActor
	private TextButton exit;
	@LmlActor
	private TextButton settings;
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
		stage.act(delta);
		stage.draw();
		if(!initialized){
			stage.setDebugAll(true);
			initialize();
			initialized = true;
			initMainBackground();
		}
		actionService.render();
	}

	private void initMainBackground() {
		AnimationActor background = new AnimationActor(new AnimationTexture("fire", true));
		stage.getRoot().addActor(background);
		background.clearActions();
		background.toBack();
		actionService.scheduleAction(new SequenceSchedule(background,
				Actions.sequence(Actions.sizeTo(stage.getWidth(), stage.getHeight()),
						Actions.fadeIn(1f, Interpolation.circleIn))));
	}

	private void initialize() {
		Array<Button> buttons = new Array<>();
		buttons.add(start, settings, exit);
		start.clip(false);
		for (val button :
				new Array.ArrayIterator<>(buttons)) {
			actionService.appearFromRight(button);
		}

		viewService.initiate();
		viewService.attachListener(stage);
		viewService.addListeners(this, sureDialog);
		viewService.setCurrentListener(this);
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

	@LmlAction
	public void showSureDialog() {
		interfaceService.showDialog(sureDialog.getClass());
		viewService.setCurrentListener(sureDialog);
	}
}