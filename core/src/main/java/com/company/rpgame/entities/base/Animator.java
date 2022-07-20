package com.company.rpgame.entities.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public final class Animator {
    private final static float FrameRateMultiplier = 1f/2f;

    // Constant rows and columns of the sprite sheet
    private final int frameColumns, frameRows;

    // Objects used
    Animation<TextureRegion> animation; // Must declare frame type (TextureRegion)
    Texture sheet;

    private boolean isReversed;

    // A variable for tracking elapsed time for the animation
    float stateTime;

    public Animator(String texturePath,int frameColumns, int frameRows){

        this.frameColumns = frameColumns;
        this.frameRows = frameRows;
        create(texturePath);
    }

    public void create(String texturePath) {

        sheet = new Texture(Gdx.files.internal(texturePath));

        TextureRegion[][] tmp = TextureRegion.split(sheet,
                sheet.getWidth() / frameColumns,
                sheet.getHeight() / frameRows);

        TextureRegion[] animationFrames = new TextureRegion[frameColumns * frameRows];
        int index = 0;
        for (int i = 0; i < frameRows; i++) {
            for (int j = 0; j < frameColumns; j++) {
                animationFrames[index++] = tmp[i][j];
            }
        }

        animation = new Animation<>(1f/animationFrames.length*FrameRateMultiplier, animationFrames);

        stateTime = 0f;
    }

    public void render(final SpriteBatch batch, final Vector2 position,
                       final float delta, final boolean isDirectionLeft) {
        stateTime += delta;

        updateDirection(isDirectionLeft);

        draw(batch, position);
    }

    private void draw(final SpriteBatch batch, final Vector2 position){
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);

        batch.begin();
        batch.draw(currentFrame, position.x, position.y); // Draw current frame at (50, 50)
        batch.end();
    }
    private void updateDirection(final boolean isDirectionLeft){
        if(isDirectionLeft && !isReversed){
            reverseRender();
            isReversed = true;
        }else if(isReversed && !isDirectionLeft){
            reverseRender();
            isReversed = false;
        }
    }

    public void dispose() {
        sheet.dispose();
    }
    public void reverseRender() {
        for (TextureRegion tr:
             animation.getKeyFrames()) {
            tr.flip(true, false);
        }
    }
}
