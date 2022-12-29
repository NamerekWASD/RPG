package com.company.rpgame.helper;

import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.IntSet;

public class ArrayUtils {

    public static int[] getItems(IntSet set) {
        return new IntSet.IntSetIterator(set).toArray().items;
    }
    public static IntArray getArray(IntSet set){
        return IntArray.with(getItems(set));
    }

}
