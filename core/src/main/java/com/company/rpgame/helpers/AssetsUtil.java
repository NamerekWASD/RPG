package com.company.rpgame.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.github.czyzby.autumn.annotation.Component;
import com.github.czyzby.autumn.annotation.Inject;
import com.github.czyzby.autumn.mvc.component.asset.AssetService;
import lombok.val;

import java.util.NoSuchElementException;
import java.util.Objects;

@Component
public class AssetsUtil {
    public enum Extension {
        PNG
    }
    public static String extensionValue(Extension value){
        return "." + value.name().toLowerCase();
    }
    @Inject
    private static AssetService assetService;

    public static Texture getTexture(String directoryPath, String drawableName){
        return getTexture(directoryPath, drawableName, ".png");
    }

    public static Texture getTexture(String directoryPath, String drawableName, String extension) {
        FileHandle file = findFile(directoryPath, drawableName + extension);
        if(file == null){
            throw new NoSuchElementException("No such drawable in asset manager!" +
                    "\nDrawable name: " + drawableName);
        }
        return assetService.get(file.path(), Texture.class);
    }

    public static Drawable getDrawable(String directoryPath, String drawableName){
        return getDrawable(directoryPath, drawableName, Extension.PNG);
    }
    public static Drawable getDrawable(String directoryPath, String drawableName, Extension extension){
        FileHandle file = findFile(directoryPath, drawableName + extensionValue(extension));
        if(file == null){
            throw new NoSuchElementException("No such drawable in asset manager!" +
                    "\nDrawable name: " + drawableName);
        }
        return new TextureRegionDrawable(assetService.get(file.path(), Texture.class));
    }
    public static FileHandle findFile(String directoryPath, String fileToFind){
        FileHandle parent = Gdx.files.internal(directoryPath);
        for (val child:
                parent.list()) {
            if(Objects.equals(child.name().toLowerCase(),
                    fileToFind.toLowerCase())) {
                return child;
            }
            if(child.isDirectory()){
                FileHandle found = findFile(child.path(), fileToFind);
                if(found != null){
                    return found;
                }
            }
        }
        return null;
    }
}
