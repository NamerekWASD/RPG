package com.company.rpgame.entity.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.company.rpgame.entity.components.base.Animator;
import com.company.rpgame.helpers.Box2D.PositionFixer;
import com.company.rpgame.helpers.Box2D.components.Size;

public class EntityAnimator extends Animator {

    private Vector2 bodyCenter;
    private boolean isReversed = false;
    protected SpriteBatch batch;
    private Vector2 currentPosition;

    public EntityAnimator(SpriteBatch batch,
                          String textureName,
                          String SheetDataName,
                          Size bodySize) {
        super(textureName, SheetDataName);
        this.batch = batch;
        findCenter(bodySize);
    }

    @Override
    public void act(final float delta) {
        stateTime += delta;
    }

    @Override
    public void draw(){
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);

        batch.begin();
        batch.draw(currentFrame, currentPosition.x + bodyCenter.x, currentPosition.y + bodyCenter.y);
        batch.end();
    }

    public void updateDirection(final boolean isDirectionLeft){
        if(isDirectionLeft && !isReversed){
            reverseRender();
            isReversed = true;
        }else if(isReversed && !isDirectionLeft){
            reverseRender();
            isReversed = false;
        }
    }

    private void reverseRender() {
        for (TextureRegion tr:
                animation.getKeyFrames()) {
            tr.flip(true, false);
        }
    }

    private void findCenter(Size bodySize) {
        bodyCenter = PositionFixer.findCenterOnYAxis(bodySize, frameSize);
    }

    public void setCurrentPosition(Vector2 currentPosition) {
        this.currentPosition = currentPosition;
    }
}
