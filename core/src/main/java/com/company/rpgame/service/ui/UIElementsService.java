package com.company.rpgame.service.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.Null;
import com.company.rpgame.entities.Player;
import com.company.rpgame.service.Box2DService;
import com.company.rpgame.service.ui.UIService;
import com.company.rpgame.service.ui.elements.HealthBar;
import com.github.czyzby.autumn.annotation.Component;
import com.github.czyzby.autumn.annotation.Inject;
import com.github.czyzby.autumn.mvc.component.ui.InterfaceService;
import com.github.czyzby.autumn.mvc.stereotype.View;
import com.github.czyzby.autumn.mvc.stereotype.ViewDialog;

import java.lang.annotation.Annotation;

public class UIElementsService {
    private final UIService uiService;
    private final Player player;
    private HealthBar playerHealthBar;

    public UIElementsService(UIService uiService, Player player){
        this.uiService = uiService;
        this.player = player;
    }

    public void initiateHealthPointBar(final Vector2 position){
        playerHealthBar = new HealthBar(300, 20,
                position, uiService.getSkin().getDrawable("bar"), 1);
    }

    public void render(final float delta, final SpriteBatch batch){
        if(playerHealthBar != null){
            playerHealthBar.render(delta, batch, player.getHealthPoints());
        }
    }


    public void onToggle(ImageButton button, Box2DService world, InterfaceService interfaceService){
        if(world.isRunning()){
            world.pause();
            button.getStyle().imageChecked = uiService.getSkin().getDrawable("pause-btn");
        }else {
            onResume(null, world, interfaceService);
            button.getStyle().imageChecked = uiService.getSkin().getDrawable("play-btn");
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
            if (annotation.annotationType() == ViewDialog.class){
                interfaceService.destroyDialog(tClass);
            }else if (annotation.annotationType() == View.class){
                interfaceService.destroy(tClass);
            }
        }
    }
}
