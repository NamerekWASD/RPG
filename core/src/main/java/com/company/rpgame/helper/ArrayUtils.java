package com.company.rpgame.helper;

import com.badlogic.gdx.utils.IntSet;

public class ArrayUtils {

    public static int[] getItems(IntSet set) {
        return new IntSet.IntSetIterator(set).toArray().items;
    }

    public static IntSet.IntSetIterator getIterator(IntSet set){
        return new IntSet.IntSetIterator(set);
    }
}
