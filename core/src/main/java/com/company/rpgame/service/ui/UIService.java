package com.company.rpgame.service.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.github.czyzby.autumn.annotation.Component;
import com.github.czyzby.autumn.annotation.Inject;
import com.github.czyzby.autumn.mvc.component.ui.SkinService;


@Component
public class UIService{
    @Inject
    private static SkinService skinService;

    public static Skin getSkin() {
        return skinService.getSkin();
    }

    public static void addSkin(String id, String skinPath) {
        skinService.addSkin(id, new Skin(Gdx.files.internal(skinPath)));
    }

}
