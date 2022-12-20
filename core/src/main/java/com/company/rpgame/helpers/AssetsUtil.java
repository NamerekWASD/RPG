package com.company.rpgame.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Null;
import com.github.czyzby.autumn.annotation.Component;
import com.github.czyzby.autumn.annotation.Inject;
import com.github.czyzby.autumn.mvc.component.asset.AssetService;
import lombok.val;

import java.util.NoSuchElementException;
import java.util.Objects;
@Component
public class AssetsUtil {
    @Inject
    private static AssetService assetService;

    public static Drawable getDrawable(String directoryPath, String drawableName, @Null String extension){
        if(extension == null){
            extension  = ".png";
        }
        FileHandle file = scanDirectory(directoryPath, drawableName + extension);
        if(file == null){
            throw new NoSuchElementException("No such drawable in asset manager!" +
                    "\nDrawable name: " + drawableName);
        }
        return new TextureRegionDrawable(assetService.get(file.path(), Texture.class));
    }
    public static FileHandle scanDirectory(String directoryPath, String fileToFind){
        FileHandle fileHandle = Gdx.files.internal(directoryPath);

        if(!Objects.equals(fileHandle.file().getName(), fileToFind)){
            for (val child:
                    fileHandle.list()) {
                if(Objects.equals(child.name(), fileToFind)){
                    return child;
                }
                if(!child.isDirectory()){
                    continue;
                }
                return scanDirectory(child.path() ,fileToFind);
            }
        }
        return null;
    }
}
