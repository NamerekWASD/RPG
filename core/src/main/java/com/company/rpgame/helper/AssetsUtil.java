package com.company.rpgame.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
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

    public static Texture getTexture(String directoryPath, String drawableName){
        return getTexture(directoryPath, drawableName, ".png");
    }

    public static Texture getTexture(String directoryPath, String drawableName, String extension) {
        FileHandle file = findFile(directoryPath, drawableName + extension);
        if(file == null){
            throw new NoSuchElementException("No such picture in asset manager!" +
                    "\nDrawable name: " + drawableName+extension);
        }
        return assetService.get(file.path(), Texture.class);
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
