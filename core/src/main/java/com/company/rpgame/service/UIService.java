package com.company.rpgame.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.github.czyzby.autumn.annotation.Component;

@Component
public class UIService {
    private Skin skin;
    private static final String DEFAULT_SKIN_PATH = "ui/uiskin.json";

    public UIService(){
        try {
            skin = new Skin(Gdx.files.internal(DEFAULT_SKIN_PATH));
        }catch (Exception e){
            System.console().printf(e.getMessage());
        }
    }

    public Skin getSkin() {
        return skin;
    }

    public void setSkin(String skinPath) {
        skin = new Skin(Gdx.files.internal(skinPath));
    }
}
