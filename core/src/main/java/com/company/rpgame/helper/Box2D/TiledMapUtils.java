package com.company.rpgame.helper.Box2D;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;

public final class TiledMapUtils {
    public static RectangleMapObject fetchRectangle(TiledMap map,
                                        String layerName,
                                        String objectName){
        MapLayer utilObjects = map.getLayers().get(layerName);
        MapObject Object = utilObjects.getObjects().get(objectName);
        if(Object instanceof RectangleMapObject){
            return (RectangleMapObject) Object;
        }
        return null;
    }
}
