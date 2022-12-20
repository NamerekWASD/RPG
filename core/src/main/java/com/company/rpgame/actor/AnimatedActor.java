package com.company.rpgame.actor;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.company.rpgame.actor.components.AnimationTexture;

public class AnimatedActor extends Image {
    private final AnimationTexture texture;

    public AnimatedActor(AnimationTexture texture)
    {
        super(texture);
        this.texture = texture;
    }

    @Override
    public void act(float delta)
    {
        texture.act(delta);
        super.act(delta);
    }

}
