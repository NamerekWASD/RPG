package com.company.rpgame.service.ui.elements.game.base;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.company.rpgame.helpers.Percentage;

public class Bar implements UIElement{
    private final boolean isAnimatedBar;
    private final Vector2 position = new Vector2(50, 50);
    private final Drawable externalTexture;
    private final float animationDuration;
    private final int height = 20;
    private final int maxWidth = 300;
    private int previousWidth, currentWidth;
    private int fadingOutWidth;
    private float suspenseTime;

    private Texture foregroundTexture, backgroundTexture, animatedTexture;

    public Bar(Drawable externalTexture, float animationDuration) {
        this.externalTexture = externalTexture;
        this.animationDuration = animationDuration;
        isAnimatedBar = animationDuration != 0;
        initiateTextures();
    }

    private void initiateTextures() {

        Pixmap pixmap = initiatePixmap(maxWidth, height, Color.RED);
        backgroundTexture = new Texture(pixmap);

        pixmap = initiatePixmap(maxWidth, height, Color.GREEN);
        foregroundTexture = new Texture(pixmap);

        if (animationDuration != 0f) {
            pixmap = initiatePixmap(0, height, Color.WHITE);
            animatedTexture = new Texture(pixmap);
        }

        pixmap.dispose();
    }

    private Pixmap initiatePixmap(int width, int height, Color backgroundColor) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(backgroundColor);
        pixmap.fill();

        return pixmap;
    }

    @Override
    public void render(final float delta, final SpriteBatch batch){
        update(delta);

        if(isAnimatedBar){
            animatedRender(batch);
        }else{
            defaultRender(batch);
        }
    }
    private void update(final float delta){

        if(isAnimatedBar){
            if(previousWidth > this.currentWidth){
                suspenseTime = 0;
                fadingOutWidth += previousWidth - this.currentWidth;
            }

            suspenseTime += delta;
            if(suspenseTime > .5f){
                fadingOutWidth -= (fadingOutWidth / animationDuration) * delta;
            }

            if (fadingOutWidth <= 0){
                fadingOutWidth = 0;
            }
        }

        previousWidth = this.currentWidth;
    }

    private void defaultRender(final Batch batch){
        batch.begin();
        externalTexture.draw(batch, position.x, position.y, maxWidth, height);
        batch.draw(backgroundTexture, position.x, position.y, maxWidth, height);
        batch.draw(foregroundTexture, position.x, position.y, currentWidth, height);
        batch.end();
    }
    private void animatedRender(final Batch batch){
        batch.begin();
        Vector2 externalTexturePosition = new Vector2(position.x-20, position.y - (height*3f)/2f + height/2f);
        externalTexture.draw(batch, externalTexturePosition.x, externalTexturePosition.y,
                maxWidth+40, height*3f);
        batch.draw(backgroundTexture, position.x, position.y, maxWidth, height);

        drawAdditionalStripe(batch);

        batch.draw(foregroundTexture, position.x, position.y, currentWidth, height);
        batch.end();
    }

    private void drawAdditionalStripe(final Batch batch) {
        batch.draw(animatedTexture, position.x + currentWidth,
                position.y, fadingOutWidth, height);
    }
    public void setCurrentWidth(float percentage) {
        int calculatedWidth = (int)Percentage.getValuePercentageFromValue(percentage, maxWidth);

        if(calculatedWidth < 0)
            this.currentWidth = 0;
        else
            this.currentWidth = Math.min(calculatedWidth, maxWidth);

    }
    @Override
    public void dispose(){
        backgroundTexture.dispose();
        foregroundTexture.dispose();
        animatedTexture.dispose();
    }
}
