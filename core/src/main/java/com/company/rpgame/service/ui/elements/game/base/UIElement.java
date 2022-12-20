package com.company.rpgame.service.ui.elements.game.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public interface UIElement extends Disposable {
    void render(final float delta, final SpriteBatch batch);
}
