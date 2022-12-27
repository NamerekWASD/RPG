package com.company.rpgame.ui.game.base;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Disposable;

public interface UIElement extends Disposable {
    void render(final float delta, final Batch batch);
}
