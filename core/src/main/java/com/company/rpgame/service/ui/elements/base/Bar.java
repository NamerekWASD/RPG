package com.company.rpgame.service.ui.elements.base;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class Bar {
    private final boolean isAnimatedBar;
    private final Vector2 position;
    private final int maxWidth;
    private final int height;
    private final Drawable externalTexture;
    private int previousWidth ,currentWidth;
    private int fadingOutWidth;
    private Texture foregroundTexture, backgroundTexture, animatedTexture;

    private float animationDuration;


    public Bar(int maxWidth, int height, Vector2 position, Drawable externalTexture, Texture foregroundTexture,
               Texture backgroundTexture, float animationDuration) {
        this.maxWidth = maxWidth;
        this.height = height;
        this.position = position;
        this.externalTexture = externalTexture;
        this.foregroundTexture = foregroundTexture;
        this.backgroundTexture = backgroundTexture;
        this.animationDuration = animationDuration;
        isAnimatedBar = true;
    }

    public Bar(int maxWidth, int height,
               Color backgroundColor, Color foregroundColor,
               Vector2 position, Drawable externalTexture, float animationDuration) {
        this.maxWidth = maxWidth;
        this.height = height;
        this.position = position;
        this.externalTexture = externalTexture;
        this.animationDuration = animationDuration;
        isAnimatedBar = true;
        initiateTextures(backgroundColor, foregroundColor);
    }

    public Bar(int maxWidth, int height,
               Color backgroundColor, Color foregroundColor,
               Vector2 position, Drawable externalTexture){
        this.maxWidth = maxWidth;
        this.height = height;
        this.position = position;
        this.externalTexture = externalTexture;
        isAnimatedBar = false;
        initiateTextures(backgroundColor, foregroundColor);
    }
    private void initiateTextures(Color backgroundColor,
                                  Color foregroundColor) {

        Pixmap pixmap = initiatePixmap(maxWidth, height, backgroundColor);

        backgroundTexture = new Texture(pixmap);

        pixmap = initiatePixmap(maxWidth, height, foregroundColor);

        foregroundTexture = new Texture(pixmap);

        if(animationDuration != 0f){
            pixmap = initiatePixmap(0, height, Color.BLUE);

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

    public void render(final float delta, final SpriteBatch batch, final int currentWidth){

        update(delta, currentWidth);

        if(!isAnimatedBar){
            defaultRender(delta, batch);
        }else{
            animatedRender(delta, batch);
        }
    }
    private void defaultRender(final float delta, final SpriteBatch batch){
        batch.begin();
        externalTexture.draw(batch, position.x, position.y, maxWidth, height);
        batch.draw(backgroundTexture, position.x, position.y, maxWidth, height);
        batch.draw(foregroundTexture, position.x, position.y, currentWidth, height);
        batch.end();
    }
    private void animatedRender(final float delta, final SpriteBatch batch){
        batch.begin();
        externalTexture.draw(batch, position.x-20, position.y-(height*3f)/2f+height/2f, maxWidth+40, height*3f);
        batch.draw(backgroundTexture, position.x, position.y, maxWidth, height);

        drawAdditionalStripe(delta, batch);

        batch.draw(foregroundTexture, position.x, position.y, currentWidth, height);
        batch.end();
    }

    private void drawAdditionalStripe(float delta, final SpriteBatch batch) {

        batch.draw(animatedTexture, position.x + currentWidth,
                position.y, fadingOutWidth, height);

    }

    private float suspenseTime;
    private void update(final float delta, final int currentWidth){
        setCurrentWidth(currentWidth);

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

    public void setCurrentWidth(int currentWidth) {
        if(currentWidth < 0){
            this.currentWidth = 0;
        }else this.currentWidth = Math.min(currentWidth, maxWidth);
    }

    public void dispose(){
        backgroundTexture.dispose();
        foregroundTexture.dispose();
        animatedTexture.dispose();
    }
}
