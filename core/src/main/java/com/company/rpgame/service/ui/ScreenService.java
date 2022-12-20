package com.company.rpgame.service.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.Null;
import com.company.rpgame.entity.player.Player;
import com.company.rpgame.service.Box2DService;
import com.company.rpgame.service.ui.elements.game.HealthBar;
import com.github.czyzby.autumn.mvc.component.ui.InterfaceService;
import com.github.czyzby.autumn.mvc.stereotype.View;
import com.github.czyzby.autumn.mvc.stereotype.ViewDialog;

import java.lang.annotation.Annotation;
import java.util.Objects;

public class ScreenService {
    private Player player;
    private final SpriteBatch batch;
    private HealthBar playerHealthBar;
    public ScreenService(){
        batch = new SpriteBatch();
    }

    public void initiateGameUI(){
        playerHealthBar = new HealthBar(player, UIService.getSkin().getDrawable("bar"),1);
    }

    public void render(final float delta){
        update(delta);

        playerHealthBar.render(delta, batch);
    }

    private void update(final float delta){
        playerHealthBar.update();
    }

    public void onToggle(ImageButton button, Box2DService world, InterfaceService interfaceService){
        if(world.isRunning()){
            world.pause();
            button.getStyle().imageChecked = UIService.getSkin().getDrawable("pause-btn");
        }else {
            onResume(null, world, interfaceService);
            button.getStyle().imageChecked = UIService.getSkin().getDrawable("play-btn");
        }
    }

    public <T> void onResume(@Null Class<T> tClass, Box2DService world, InterfaceService interfaceService) {
        world.resume();
        if(tClass == null){
            return;
        }
        for (Annotation annotation:
                tClass.getAnnotations()) {
            if (annotation == null) {
                continue;
            }
            if (Objects.equals(annotation.annotationType(), ViewDialog.class)){
                interfaceService.destroyDialog(tClass);
            }else if (Objects.equals(annotation.annotationType(), View.class)){
                interfaceService.destroy(tClass);
            }
        }
    }
    public void dispose(){
        playerHealthBar.dispose();
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
