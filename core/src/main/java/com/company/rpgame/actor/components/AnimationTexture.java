package com.company.rpgame.actor.components;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.company.rpgame.entity.components.DefaultAnimator;
import com.company.rpgame.entity.components.base.Animator;

public class AnimationTexture extends BaseDrawable
{
    private final Animation<TextureRegion> animation;
    private boolean looping;
    private float stateTime = 0;

    public AnimationTexture(String texturePath, String sheetDataPath, boolean looping) {
        this.looping = looping;
        Animator animator = new DefaultAnimator(texturePath, sheetDataPath);
        this.animation = animator.getAnimation();
        setMinWidth(animation.getKeyFrame(0).getRegionWidth());
        setMinHeight(animation.getKeyFrame(0).getRegionHeight());
    }
    public void act(float delta)
    {
        stateTime += delta;
    }

    public void reset()
    {
        stateTime = 0;
    }

    @Override
    public void draw(Batch batch, float x, float y, float width, float height) {
        batch.draw(animation.getKeyFrame(stateTime, looping), x, y, width, height);
    }

    public boolean isLooping() {
        return looping;
    }

    public void setLooping(boolean looping) {
        this.looping = looping;
    }
}