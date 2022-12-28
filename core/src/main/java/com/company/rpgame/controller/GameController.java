package com.company.rpgame.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.Disposable;
import com.company.rpgame.controller.dialog.InventoryViewControllerControl;
import com.company.rpgame.controller.dialog.SettingsDialogController;
import com.company.rpgame.exception.NoSpawnPointException;
import com.company.rpgame.service.*;
import com.github.czyzby.autumn.annotation.Inject;
import com.github.czyzby.autumn.mvc.component.ui.InterfaceService;
import com.github.czyzby.autumn.mvc.component.ui.controller.ViewResizer;
import com.github.czyzby.autumn.mvc.component.ui.controller.impl.StandardViewShower;
import com.github.czyzby.autumn.mvc.stereotype.View;
import com.github.czyzby.autumn.mvc.stereotype.ViewStage;
import com.github.czyzby.lml.annotation.LmlAction;

@View(id="game", value = "lml/game.lml")
public class GameController extends StandardViewShower implements DefaultViewController, ViewResizer, Disposable {
    @Inject private InterfaceService interfaceService;
    @Inject private Box2DService world;
    @Inject private PlayerService player;
    @Inject private InventoryService inventoryService;
    @Inject private ScreenService screenService;
    @Inject private ViewService actionsService;
    @Inject private InventoryViewControllerControl inventoryDialogController;
    @Inject private SettingsDialogController settingsDialogController;
    @ViewStage
    private Stage stage;

    @LmlAction("gotoSettings")
    public void showSettings(){
        world.pause();
        interfaceService.showDialog(SettingsDialogController.class);
        SettingsDialogController.getDialog().clearActions();
        createAction(SettingsDialogController.getDialog());

    }
    @LmlAction("gotoInventory")
    public void showInventoryDialog(){
        world.pause();
        interfaceService.showDialog(InventoryViewControllerControl.class);
        InventoryViewControllerControl.getDialog().clearActions();
        createAction(InventoryViewControllerControl.getDialog());
        inventoryService.fillInventory();
    }
    private void createAction(Actor actor){
        if (actor == null) {
            return;
        }
        actor.addAction(Actions.sequence(Actions.rotateTo(90),
                Actions.rotateTo(0, 0.3f, Interpolation.fastSlow)));
    }

    @Override
    public void show(final Stage stage, final Action action){
        world.create();
        try {
            player.initiate();
        } catch (NoSpawnPointException e) {
            throw new RuntimeException(e);
        }
        screenService.initiate();
        inventoryService.setStage(stage);

        actionsService.initiate();
        actionsService.addListeners(this, inventoryDialogController, settingsDialogController);
        actionsService.setCurrentListener(this);
        actionsService.attachListener(stage);
        super.show(stage, Actions.sequence(action, Actions.run(() -> {
            final InputMultiplexer stageMultiplexer = new InputMultiplexer(stage);
            player.initiateControls(stageMultiplexer);
            Gdx.input.setInputProcessor(stageMultiplexer);
        })));
    }

    @Override
    public void resize(final Stage stage, final int width, final int height) {
        world.resize(width, height);
        player.resize(width, height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render(final Stage stage, final float delta) {
        stage.act(delta);
        world.render(delta);
        player.render(delta);
        screenService.render(delta);
        stage.draw();
        if(world.isRunning()){
            actionsService.setCurrentListener(this);
        }
    }

    @LmlAction("toggleWorldState")
    public void toggle(ImageButton button){
        screenService.toggleGameState(button);
    }
    @Override
    public void dispose(){
        world.dispose();
        player.dispose();
        stage.dispose();
        screenService.dispose();
        actionsService.dispose();
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
        showSettings();
    }
}
