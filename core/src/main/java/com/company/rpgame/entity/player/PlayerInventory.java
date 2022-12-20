package com.company.rpgame.entity.player;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.company.rpgame.entity.items.weapon.melee.Sword;
import com.company.rpgame.entity.items.basic.Item;
import com.company.rpgame.service.ui.UIService;


public class PlayerInventory {
    private Array<Item> inventoryItems;
    private Array<Item> equippedItems;
    private static final int capacity = 16;
    public PlayerInventory(){
        inventoryItems = new Array<>(capacity);
        equippedItems = new Array<>();
        Image image = new Image(UIService.getSkin(), "knife");
        Sword sword = new Sword(10.0, 50.0, 10.0);
        sword.setImage(image);
        sword.setDescription("some sword");
        Image newImage = new Image(UIService.getSkin(), "set");
        Sword newSword = new Sword(10.0, 50.0, 10.0);
        newSword.setImage(newImage);
        newSword.setDescription("another one");
        inventoryItems.add(sword, newSword);
    }

    public Array<Item> getInventoryItems() {
        return inventoryItems;
    }
    public Array<Item> getAllItems(){
        Array<Item> items = new Array<>(getInventoryItems());
        items.addAll(equippedItems);
        return items;
    }

    public void setItems(Array<Item> items) {
        this.inventoryItems = items;
    }
    public int getMaxItemCount(){
        return capacity;
    }

    public void setEquippedItems(Array<Item> equipments) {
        this.equippedItems = equipments;
    }

    public Array<Item> getEquippedItems() {
        return equippedItems;
    }
}
