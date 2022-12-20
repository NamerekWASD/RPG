package com.company.rpgame.service.ui.elements.inventory;

import com.badlogic.gdx.utils.Array;
import com.company.rpgame.entity.items.basic.Item;



public class ItemEquipped extends ItemCell{
    private static final String packagePath = "com.company.rpgame.entity.items";
    private Class<?> acceptableClassItem;
    public void setAcceptableClassItem(String classItem) {
        classItem = classItem.replace("equipment", "");
        String exactPath = "";
        Array<String> armorPackage = new Array<>(new String[]{"Boots","Chest","Head","Legs"});
        Array<String> basicPackage = new Array<>(new String[]{"Hand"});

        if(armorPackage.contains(classItem, false)){
            exactPath = ".armor";
        } else if (basicPackage.contains(classItem, false) &&
        classItem.equals("Hand")) {
            classItem = "Equipable";
        }

        try {
            acceptableClassItem = Class.forName(packagePath + exactPath + "." + classItem);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setItem(Item item) {
        if(item != null && !acceptableClassItem.isAssignableFrom(item.getClass())){
            return;
        }
        this.item = item;
        if(this.item != null){
            apply();
        }
    }
    public boolean checkCompatibility(Item item) {
        return acceptableClassItem.isAssignableFrom(item.getClass());
    }
}
