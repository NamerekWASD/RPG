package com.company.rpgame.actor.components;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.company.rpgame.entity.components.BaseAnimator;
import com.company.rpgame.entity.components.base.Animator;
import com.company.rpgame.helper.Box2D.components.Size;

public class AnimationTexture extends BaseDrawable
{
    private final Animation<TextureRegion> animation;
    private final Size textureSize;
    private boolean looping;
    private float stateTime = 0;

    public AnimationTexture(String animationName, boolean looping) {
        this.looping = looping;
        Animator animator = new BaseAnimator(animationName);
        this.animation = animator.getAnimation();
        Size animationSize = new Size(animation.getKeyFrame(0).getRegionWidth(), animation.getKeyFrame(0).getRegionHeight());
        this.textureSize = animationSize;
        setMinWidth(animationSize.getWidth());
        setMinHeight(animationSize.getHeight());
    }
    public void act(float delta)
    {
        stateTime += delta;
    }

    public void reset()
    {
        stateTime = 0;
    }

    public void draw(Batch batch, float x, float y){
        draw(batch, x, y, textureSize.getWidth(), textureSize.getHeight());
    }
    @Override
    public void draw(Batch batch, float x, float y, float width, float height) {
        batch.draw(animation.getKeyFrame(stateTime, looping), x, y, width, height);
    }

    public void setLooping(boolean looping) {
        this.looping = looping;
    }

    public Size getTextureSize() {
        return textureSize;
    }
}