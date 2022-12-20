package com.company.rpgame.entity.items.basic;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.company.rpgame.entity.items.Equipable;

public abstract class Item implements Equipable {
    private Image image;
    private String description;
    private String savedCellId;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
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
