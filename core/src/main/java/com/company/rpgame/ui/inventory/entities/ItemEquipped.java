package com.company.rpgame.ui.inventory.entities;

import com.badlogic.gdx.utils.Array;
import com.company.rpgame.entity.items.basic.Item;
import com.company.rpgame.helper.JsonUtil;
import lombok.val;


public class ItemEquipped extends ItemCell{
    private static Array<Class<?>> acceptableClasses;
    private Class<?> acceptableClassItem;

    private void initiate(){
        acceptableClasses = new Array<>();
        Array<String> stringClasses = JsonUtil.readRules("inventory", "permissibleEquipmentClass");
        for (val stringClass :
                new Array.ArrayIterator<>(stringClasses)) {
            try {
                acceptableClasses.add(Class.forName(stringClass));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setAcceptableClassItem(String classItem) {
        if(acceptableClasses == null){
            initiate();
        }

        for (Class<?> acceptableClass :
                new Array.ArrayIterator<>(acceptableClasses)) {
            classItem = classItem.replace("equipment", "");
            if(acceptableClass.getSimpleName().toLowerCase().contains(classItem.toLowerCase())){
                acceptableClassItem = acceptableClass;
            }
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
