package com.company.rpgame.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Null;
import com.company.rpgame.controller.game.InGameSettingsController;
import com.company.rpgame.controller.game.InventoryController;
import com.company.rpgame.exception.NoSpawnPointException;
import com.company.rpgame.service.entities.PlayerService;
import com.company.rpgame.service.ui.ScreenService;
import com.company.rpgame.service.Box2DService;
import com.company.rpgame.service.ui.InventoryService;
import com.github.czyzby.autumn.annotation.Inject;
import com.github.czyzby.autumn.mvc.component.ui.InterfaceService;
import com.github.czyzby.autumn.mvc.component.ui.controller.ViewRenderer;
import com.github.czyzby.autumn.mvc.component.ui.controller.ViewResizer;
import com.github.czyzby.autumn.mvc.component.ui.controller.impl.StandardViewShower;
import com.github.czyzby.autumn.mvc.stereotype.View;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.parser.action.ActionContainer;

@View(id="game", value = "lml/game.lml")
public class GameController extends StandardViewShower implements ViewResizer, ViewRenderer, ActionContainer, Disposable {
    @Inject private InterfaceService interfaceService;
    @Inject private Box2DService world;
    @Inject private PlayerService player;
    @Inject private InventoryService inventoryService;
    private ScreenService gameUiElementsService;
    private Stage stage;

    @LmlAction("settings")
    private void settings(){
        world.pause();
        interfaceService.showDialog(InGameSettingsController.class);
    }
    @LmlAction("Inventory")
    private void inventory(){
        world.pause();
        interfaceService.showDialog(InventoryController.class);
        inventoryService.fillInventory();
    }

    @Override
    public void show(final Stage stage, final Action action){
        this.stage = stage;
        stage.setDebugAll(true);
        world.create();
        try {
            player.setWorld(world.getWorld());
            player.create(world.getWorldSpawnPoint());
        } catch (NoSpawnPointException e) {
            e.printStackTrace();
        }
        gameUiElementsService = new ScreenService();
        gameUiElementsService.setPlayer(player.getPlayer());
        gameUiElementsService.initiateGameUI();

        inventoryService.setStage(stage);

        super.show(stage, Actions.sequence(action, Actions.run(() -> { // Listening to user input events:
            final InputMultiplexer inputMultiplexer = new InputMultiplexer(stage);
            player.initiateControls(inputMultiplexer);
            Gdx.input.setInputProcessor(inputMultiplexer);
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
        gameUiElementsService.render(delta);
        stage.draw();
    }

    @LmlAction("toggleWorldState")
    public void toggle(ImageButton button){
        gameUiElementsService.onToggle(button, world, interfaceService);
    }

    public <T> void resume(@Null Class<T> tClass) {
        gameUiElementsService.onResume(tClass, world, interfaceService);
    }

    @Override
    public void dispose(){
        world.dispose();
        player.dispose();
        stage.dispose();
    }
}
