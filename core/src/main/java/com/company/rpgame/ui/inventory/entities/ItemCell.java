package com.company.rpgame.ui.inventory.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.company.rpgame.entity.items.basic.Item;
import com.company.rpgame.helper.Box2D.components.Size;
import com.company.rpgame.helper.scene.ActorUtil;

public class ItemCell {
    private Image cell;
    protected Item item;
    private Image lastHoveredCell;

    private boolean isResized;

    public  Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
        if(item != null){
            apply();
        }
    }

    public Image getCell() {
        return cell;
    }

    public void setCell(Image cell) {
        this.cell = cell;
    }

    public Image getLastHoveredCell() {
        return lastHoveredCell;
    }

    public void setLastHoveredCell(Image lastHoveredCell) {
        this.lastHoveredCell = lastHoveredCell;
    }

    public void apply(){
        updatePosition();
        updateSize();
    }

    private void updateSize() {
        Image itemImage = item.getImage();
        if (itemImage == null) return;
        if(itemImage.getWidth() != cell.getWidth() ||
                itemImage.getHeight() != cell.getHeight()){
            Size cellSize = new Size(cell.getWidth(), cell.getHeight());
            resize(itemImage, cellSize);
        }
    }

    private void updatePosition() {
        Actor cellParent = cell.getParent();
        Actor itemParent = item.getImage().getParent();
        if(itemParent == null || cellParent == null){
            return;
        }
        Image itemImage = item.getImage();
        Vector2 newPosition = ActorUtil.convertCoordinates(cellParent, itemParent, cell);
        itemImage.setPosition(newPosition.x, newPosition.y);
        item.setSavedCellId(cell.getName());
    }

    private void resize(Image item, Size cellSize) {
        item.setSize(cellSize.getWidth(), cellSize.getHeight());
        isResized = true;
    }

    public boolean isResized() {
        return this.isResized;
    }
    public void setResized(boolean resized) {
        isResized = resized;
    }


}
