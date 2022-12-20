package com.company.rpgame.service.ui.elements.inventory;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.company.rpgame.entity.items.basic.Item;
import com.company.rpgame.helpers.Box2D.components.Size;
import com.company.rpgame.helpers.scene.ActorUtil;

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
        System.out.println("Cell pos: " +
                cell.getX() +
                " | "  +
                cell.getY());
        System.out.println("Item pos: " +
                item.getImage().getX() +
                " | "  +
                item.getImage().getY());
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
