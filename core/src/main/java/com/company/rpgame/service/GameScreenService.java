package com.company.rpgame.service;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Null;
import com.company.rpgame.ui.game.HealthBar;
import com.github.czyzby.autumn.annotation.Component;
import com.github.czyzby.autumn.annotation.Inject;
import com.github.czyzby.autumn.mvc.component.ui.InterfaceService;
import com.github.czyzby.autumn.mvc.stereotype.View;
import com.github.czyzby.autumn.mvc.stereotype.ViewDialog;

import java.util.Objects;

@Component
public class GameScreenService implements Disposable {
    @Inject
    private PlayerService player;
    @Inject private InterfaceService interfaceService;
    @Inject private Box2DService world;

    private Batch batch;
    private HealthBar playerHealthBar;

    public void initiate(){
        playerHealthBar = new HealthBar(player.getPlayer(), UIService.getSkin().getDrawable("bar"),1);
        batch = new SpriteBatch();

    }


    public void render(final float delta){
        if(world.isRunning())
            update();
        playerHealthBar.render(delta, batch);
    }

    private void update(){
        playerHealthBar.update();
    }

    public void toggleGameState(ImageButton button){
        if(world.isRunning()){
            world.pause();
            button.getStyle().imageChecked = UIService.getSkin().getDrawable("pause-btn");
        }else {
            resumeGame(null);
            button.getStyle().imageChecked = UIService.getSkin().getDrawable("play-btn");
        }
    }

    public void resumeGame(@Null Class<?> tClass) {
        world.resume();
        if(tClass == null){
            return;
        }
        new Array<>(tClass.getAnnotations()).forEach(annotation -> {
            if (Objects.equals(annotation.annotationType(), ViewDialog.class)){
                interfaceService.destroyDialog(tClass);
            }else if (Objects.equals(annotation.annotationType(), View.class)){
                interfaceService.destroy(tClass);
            }
        });
    }



    @Override
    public void dispose() {
        playerHealthBar.dispose();
    }
}
