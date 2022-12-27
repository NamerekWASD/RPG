package com.company.rpgame.ui.inventory;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;
import com.company.rpgame.entity.items.basic.Item;
import com.company.rpgame.helper.Box2D.components.Size;
import com.company.rpgame.ui.inventory.entities.ItemCell;
import com.company.rpgame.ui.inventory.entities.ItemEquipped;

import java.util.NoSuchElementException;
import java.util.Objects;

import static com.company.rpgame.helper.scene.ActorUtil.convertCoordinates;

public class InventoryDragAndDrop {
    private DragAndDrop dragAndDrop;
    private Array<ItemCell> itemCellArray;

    public void initiate() {
        dragAndDrop = new DragAndDrop();
        for (ItemCell itemCell :
                new Array.ArrayIterator<>(itemCellArray)) {
            setupTarget(itemCell.getCell());
            if(itemCell.getItem() == null){
                continue;
            }
            Image item = itemCell.getItem().getImage();
            setupSource(item);
            setupTarget(item);
            itemCell.apply();
        }
    }
    private void setupSource(Actor actor) {

        normalizeDragAndDropActorHover(actor);
        dragAndDrop.addSource(new DragAndDrop.Source(actor) {
            @Null
            public DragAndDrop.Payload dragStart (InputEvent event, float x, float y, int pointer) {
                DragAndDrop.Payload payload = new DragAndDrop.Payload();
                ItemCell itemCell = getCellThatContains(getActor());
                setLastHoveredItem(getActor(), itemCell.getCell());
                payload.setDragActor(getActor());
                return payload;
            }
            @Override
            public void drag (InputEvent event, float x, float y, int pointer) {
            }
            @Override
            public void dragStop (InputEvent event, float x, float y, int pointer, @Null DragAndDrop.Payload payload, @Null DragAndDrop.Target target) {
                if(Objects.nonNull(target)){
                    return;
                }
                Actor lastHoveredActor = getLastHoveredItem(getActor());

                if(sourceActorOverTarget(getActor(), lastHoveredActor)){
                    moveItem(getActor(), lastHoveredActor);
                }else {
                    returnItemToPreviousPosition(getActor());
                }
            }
        });
    }

    private void setupTarget(Actor actor) {
        dragAndDrop.addTarget(new DragAndDrop.Target(actor) {

            public boolean drag (DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                getActor().setColor(Color.GRAY);
                setLastHoveredItem(source.getActor(), getActor());
                return true;
            }

            public void reset (DragAndDrop.Source source, DragAndDrop.Payload payload) {
                getActor().setColor(Color.WHITE);

            }

            public void drop (DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                moveItem(source.getActor(), getActor());
            }
        });
    }

    private void returnItemToPreviousPosition(Actor actor) {
        ItemCell itemCell = getCellThatContains(actor);
        moveItem(itemCell.getItem().getImage(), itemCell.getCell());
    }
    private void setLastHoveredItem(Actor forActor, Actor lastHoveredActor) {
        ItemCell itemCell = getCellThatContains(forActor);
        itemCell.setLastHoveredCell((Image) lastHoveredActor);
    }

    private Actor getLastHoveredItem(Actor actor) {
        ItemCell itemCell = getCellThatContains(actor);
        return itemCell.getLastHoveredCell();
    }

    private void moveItem(Actor source, Actor target) {
        ItemCell sourceCell = getCellThatContains(source);
        ItemCell targetCell = getCellThatContains(target);
        boolean isCompatible;
        if(targetCell instanceof ItemEquipped){
            isCompatible = (((ItemEquipped) targetCell).checkCompatibility(sourceCell.getItem()));
            if(!isCompatible){
                returnItemToPreviousPosition(source);
                return;
            }
        }
        Item temp = sourceCell.getItem();

        boolean isTargetItemNull = Objects.isNull(targetCell.getItem());
        if(!isTargetItemNull){
            sourceCell.setItem(targetCell.getItem());
        }else {
            sourceCell.setLastHoveredCell(null);
            sourceCell.setItem(null);
        }
        targetCell.setItem(temp);
        if(sourceCell.isResized()){
            normalizeDragAndDropActorHover(source);
            sourceCell.setResized(false);
        }
    }

    private void normalizeDragAndDropActorHover(Actor actor) {
        Vector2 centered = new Vector2(actor.getWidth()/2, -(actor.getHeight()/2));
        dragAndDrop.setDragActorPosition(centered.x, centered.y);
    }


    private boolean sourceActorOverTarget(Actor source, Actor target) {
        Vector2 sourcePos = convertCoordinates(source.getParent(), target.getParent(), source);
        Vector2 targetPos = new Vector2(target.getX(), target.getY());
        Size sourceSize = new Size(source.getWidth(), source.getWidth());
        Size targetSize = new Size(target.getWidth(), target.getHeight());
        float a, b, c, d;
        a = targetPos.x;
        b = targetPos.x + targetSize.getWidth();
        c = targetPos.y;
        d = targetPos.y + targetSize.getHeight();
//        System.out.println("Source pos: " + (sourcePos.x + (sourceSize.getWidth()/2)) + " | " + (sourcePos.y + (sourceSize.getHeight()/2)));
//        System.out.println("target square: " + a + " | " + b + " | " + c + " | " + d);
        return a <= sourcePos.x + (sourceSize.getWidth()/2)
                && b >= sourcePos.x + (sourceSize.getWidth()/2)
                && c <= sourcePos.y + (sourceSize.getHeight()/2)
                && d >= sourcePos.y + (sourceSize.getHeight()/2);
    }

    private ItemCell getCellThatContains(Actor item){
        for (ItemCell itemCell :
                new Array.ArrayIterator<>(itemCellArray)) {
            if(itemCell.getCell().equals(item) ||
                    (itemCell.getItem() != null && itemCell.getItem().getImage().equals(item))){
                return itemCell;
            }
        }
        throw new NoSuchElementException("There is no such itemCell object!");
    }
    public void setItemCellArray(Array<ItemCell> itemCellArray) {
        this.itemCellArray = itemCellArray;
    }

    public void dispose() {
        if (dragAndDrop != null) {
            dragAndDrop.clear();
        }
    }

    public Array<ItemCell> getItemCells() {
        return itemCellArray;
    }
}
