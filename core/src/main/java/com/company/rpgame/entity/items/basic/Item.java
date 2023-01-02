package com.company.rpgame.entity.items.basic;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.company.rpgame.entity.items.Equipable;
import com.company.rpgame.helper.AssetsUtil;

import static com.company.rpgame.helper.Constants.IMAGES_DIRECTORY;

public abstract class Item implements Equipable {
    private String textureName;
    private Image image;
    private String description;
    private String savedCellId;

    public Image getImage() {
        return image;
    }

    public void setTexture(String textureName) {
        this.textureName = textureName;
        this.image = new Image(AssetsUtil.getTexture(IMAGES_DIRECTORY, this.textureName));
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getSavedCellId() {
        return savedCellId;
    }

    public void setSavedCellId(String savedCellId) {
        this.savedCellId = savedCellId;
    }
}
