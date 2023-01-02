package com.company.rpgame.controller;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
    @Inject private GameScreenService gameScreenService;
    @Inject private ViewService viewService;
    @Inject private InventoryViewControllerControl inventoryDialogController;
    @Inject private SettingsDialogController settingsDialogController;
    @ViewStage
    private Stage stage;

    @LmlAction("gotoSettings")
    public void showSettings(){
        world.pause();
        interfaceService.showDialog(SettingsDialogController.class);
        //TODO: create action presets for dialog in action service

    }
    @LmlAction("gotoInventory")
    public void showInventoryDialog(){
        world.pause();
        interfaceService.showDialog(InventoryViewControllerControl.class);
        inventoryService.fillInventory();
        //TODO: create action presets for dialog in action service
    }

    @Override
    public void show(final Stage stage, final Action action){
        world.create();
        try {
            player.initiate();
        } catch (NoSpawnPointException e) {
            throw new RuntimeException(e);
        }
        gameScreenService.initiate();
        inventoryService.setStage(stage);

        viewService.initiate();
        viewService.addListeners(this, inventoryDialogController, settingsDialogController);
        viewService.setCurrentListener(this);
        viewService.attachListener(stage);
        super.show(stage, action);
    }

    @Override
    public void resize(final Stage stage, final int width, final int height) {
        world.resize(width, height);
        player.resize(width, height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render(final Stage stage, final float delta) {
        world.render(delta);
        player.render(delta);
        gameScreenService.render(delta);
        stage.act(delta);
        stage.draw();
        if(world.isRunning()){
            viewService.setCurrentListener(this);
        }
    }

    @LmlAction("toggleWorldState")
    public void toggle(ImageButton button){
        gameScreenService.toggleGameState(button);
    }
    @Override
    public void dispose(){
        stage.dispose();
        world.dispose();
        player.dispose();
        gameScreenService.dispose();
        viewService.dispose();
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
