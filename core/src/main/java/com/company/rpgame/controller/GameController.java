package com.company.rpgame.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Null;
import com.company.rpgame.controller.game.InGameSettingsController;
import com.company.rpgame.service.Box2DService;
import com.company.rpgame.service.UIService;
import com.github.czyzby.autumn.annotation.Inject;
import com.github.czyzby.autumn.mvc.component.ui.InterfaceService;
import com.github.czyzby.autumn.mvc.component.ui.controller.ViewRenderer;
import com.github.czyzby.autumn.mvc.component.ui.controller.ViewResizer;
import com.github.czyzby.autumn.mvc.component.ui.controller.impl.StandardViewShower;
import com.github.czyzby.autumn.mvc.stereotype.View;
import com.github.czyzby.autumn.mvc.stereotype.ViewDialog;
import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.parser.action.ActionContainer;

import java.lang.annotation.Annotation;

@View(id="game", value = "ui/templates/game.lml")
public class GameController extends StandardViewShower implements ViewResizer, ViewRenderer,ActionContainer {
    @Inject private InterfaceService interfaceService;
    @Inject private Box2DService world;
    @Inject private UIService uiService;
    private final Box2DDebugRenderer renderer = new Box2DDebugRenderer();

    @LmlAction("settings")
    public void settings(){
        world.pause();
        interfaceService.showDialog(InGameSettingsController.class);
    }

    @Override
    public void show(final Stage stage, final Action action){
        world.create();
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
    }
    @LmlAction("toggleWorldState")
    public void toggle(ImageButton button){
        if(world.isRunning()){
            world.pause();
            button.getStyle().imageChecked = uiService.getSkin().getDrawable("pause-btn");
        }else {
            resume(null);
            button.getStyle().imageChecked = uiService.getSkin().getDrawable("play-btn");
        }
    }

    public <T> void resume(@Null Class<T> tClass) {
        world.resume();
        if(tClass == null){
            return;
        }
        for (Annotation annotation:
             tClass.getAnnotations()) {
            if (annotation == null) {
                continue;
            }
            if (annotation.annotationType().getAnnotation(ViewDialog.class) != null){
                interfaceService.destroyDialog(tClass);
            }else if (annotation.annotationType().getAnnotation(View.class) != null){
                interfaceService.destroy(tClass);
            }
        }
    }
}
