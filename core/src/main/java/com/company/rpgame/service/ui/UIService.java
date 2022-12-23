package com.company.rpgame.service.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.github.czyzby.autumn.annotation.Component;
import com.github.czyzby.autumn.annotation.Initiate;
import com.github.czyzby.autumn.annotation.Inject;
import com.github.czyzby.autumn.mvc.component.ui.SkinService;
import lombok.val;

import java.util.Objects;


@Component
public class UIService{
    @Inject
    private static SkinService skinService;

    @Initiate
    public static void initiate(final SkinService skinService){
        Skin skin = skinService.getSkin();
        loadRegions(skin, Gdx.files.internal("ui/atlas"));
        skin.add("noImage", new Pixmap(50, 50, Pixmap.Format.RGB888));
    }
    private static void loadRegions(Skin skin, FileHandle regionPath) {
        for (val file :
                regionPath.list()) {
            if(file.isDirectory() && !file.name().equals("main")){
                loadRegions(skin, file);
            }
            if(Objects.equals(file.extension(), "atlas")){
                skin.addRegions(new TextureAtlas(file));
            }
        }
    }
    public static Skin getSkin() {
        return skinService.getSkin();
    }

    public static void addSkin(String id, String skinPath) {
        skinService.addSkin(id, new Skin(Gdx.files.internal(skinPath)));
    }

}
