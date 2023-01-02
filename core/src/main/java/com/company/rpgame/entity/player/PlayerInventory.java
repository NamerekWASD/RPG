package com.company.rpgame.entity.player;

import com.badlogic.gdx.utils.Array;
import com.company.rpgame.entity.items.armor.Chest;
import com.company.rpgame.entity.items.armor.Head;
import com.company.rpgame.entity.items.basic.Armor;
import com.company.rpgame.entity.items.basic.Item;
import com.company.rpgame.entity.items.weapon.melee.Sword;


public class PlayerInventory {
    private Array<Item> inventoryItems;
    private Array<Item> equippedItems;
    private static final int capacity = 16;
    public PlayerInventory(){
        inventoryItems = new Array<>(capacity);
        equippedItems = new Array<>();
        Sword sword = new Sword(10.0, 50.0, 10.0);
        sword.setTexture("knife");
        sword.setDescription("some sword");

        Sword newSword = new Sword(10.0, 50.0, 10.0);
        newSword.setTexture("knife");
        newSword.setDescription("another one");

        Armor chest = new Chest();
        chest.setTexture("ironArmor");
        chest.setDescription("someDesc");

        Armor head = new Head();
        head.setTexture("plasticHelm");
        head.setDescription("someDesc");

        inventoryItems.add(sword, newSword, chest, head);
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
