package com.company.rpgame.service.ui.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.company.rpgame.service.ui.elements.base.Bar;

public class HealthBar extends Bar {
    private static final Color RED = Color.RED ,GREEN = Color.GREEN;


    public HealthBar(int maxWidth, int height, Vector2 position, Drawable externalTexture,
                     Texture foregroundTexture, Texture backgroundTexture, float animationDuration) {

        super(maxWidth, height, position, externalTexture, foregroundTexture, backgroundTexture, animationDuration);

    }

    public HealthBar(int maxWidth, int height, Drawable externalTexture,
                     Color backgroundColor, Color foregroundColor,
                     Vector2 position, float animationDuration) {

        super(maxWidth, height, backgroundColor, foregroundColor, position, externalTexture, animationDuration);

    }

    public HealthBar(int maxWidth, int height, Vector2 position, Drawable externalTexture, float animationDuration) {

        super(maxWidth, height, RED, GREEN, position, externalTexture, animationDuration);

    }

    public HealthBar(int maxWidth, int height, Color backgroundColor, Color foregroundColor, Vector2 position, Drawable externalTexture) {

        super(maxWidth, height, backgroundColor, foregroundColor, position, externalTexture);

    }
}
