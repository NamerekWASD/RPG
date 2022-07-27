package com.company.rpgame.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.Null;
import com.company.rpgame.controller.game.InGameSettingsController;
import com.company.rpgame.service.ui.UIElementsService;
import com.company.rpgame.service.Box2DService;
import com.company.rpgame.service.ui.UIService;
import com.github.czyzby.autumn.annotation.Inject;
import com.github.czyzby.autumn.mvc.component.ui.InterfaceService;
import com.github.czyzby.autumn.mvc.component.ui.controller.ViewRenderer;
import com.github.czyzby.autumn.mvc.component.ui.controller.ViewResizer;
import com.github.czyzby.autumn.mvc.component.ui.controller.impl.StandardViewShower;
import com.github.czyzby.autumn.mvc.stereotype.View;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.parser.action.ActionContainer;

@View(id="game", value = "ui/templates/game.lml")
public class GameController extends StandardViewShower implements ViewResizer, ViewRenderer,ActionContainer {

    private static final float SCALE = 2f;

    private final SpriteBatch batch = new SpriteBatch();
    @Inject private InterfaceService interfaceService;
    @Inject private Box2DService world;
    private UIElementsService uiElementsService;

    @LmlAction("settings")
    public void settings(){
        world.pause();
        interfaceService.showDialog(InGameSettingsController.class);
    }

    @Override
    public void show(final Stage stage, final Action action){
        world.create();
        uiElementsService = new UIElementsService(new UIService(), world.getPlayer());
        uiElementsService.initiateHealthPointBar(new Vector2(50, Gdx.graphics.getHeight() - 75));
        super.show(stage, Actions.sequence(action, Actions.run(() -> { // Listening to user input events:
            final InputMultiplexer inputMultiplexer = new InputMultiplexer(stage);
            world.initiateControls(inputMultiplexer);
            Gdx.input.setInputProcessor(inputMultiplexer);
        })));
    }

    @Override
    public void resize(final Stage stage, final int width, final int height) {
        world.resize(width, height);
        stage.getViewport().update(width, height, true);
    }
    @Override
    public void render(final Stage stage, final float delta) {
        stage.act(delta);
        world.render(delta);
        stage.draw();
        uiElementsService.render(delta, batch);
    }

    @LmlAction("toggleWorldState")
    public void toggle(ImageButton button){
        uiElementsService.onToggle(button, world, interfaceService);
    }

    public <T> void resume(@Null Class<T> tClass) {
        uiElementsService.onResume(tClass, world, interfaceService);
    }
}
