package com.company.rpgame.helper.Box2D;

import com.badlogic.gdx.math.Vector2;
import com.company.rpgame.helper.Box2D.components.Size;

public final class PositionFixer {
    public static Vector2 findCenterOnYAxis(Size bodySize, Size textureSize) {
        Vector2 newBoxCenter = new Vector2();
        Vector2 boxCenter = new Vector2(bodySize.getWidth() / 2,  bodySize.getHeight() / 2);
        Vector2 textureCenter = new Vector2(textureSize.getWidth() / 2, textureSize.getHeight() / 2);
        if (bodySize.getHeight() == textureSize.getHeight()) {
            return boxCenter;
        }
        if(bodySize.getHeight() < textureSize.getHeight()){
            newBoxCenter.y = textureCenter.y - boxCenter.y;
        }
        return newBoxCenter;
    }
}
