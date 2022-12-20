package com.company.rpgame.entity.components.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.JsonValue;
import com.company.rpgame.helpers.Box2D.components.Size;
import com.company.rpgame.helpers.JsonUtil;

public abstract class Animator implements Disposable {
    private final static float FrameRateMultiplier = 2f;
    protected Size frameSize;
    protected Animation<TextureRegion> animation;
    protected Texture sheet;
    private int frameColumns;
    private int frameRows;
    protected float stateTime;

    public Animator(String texturePath, String sheetDataPath){
        sheet = new Texture(Gdx.files.internal(texturePath));
        readProperties(sheetDataPath);
        calculateColumnsAndRows();
        spreadTextures();
    }

    private void readProperties(String sheetDataPath) {
        JsonValue root = JsonUtil.readFromFile(sheetDataPath);
        JsonValue size = JsonUtil.getProperty("Size", root);
        int width = JsonUtil.getAsInt("width", size);
        int height = JsonUtil.getAsInt("height", size);
        frameSize = new Size(width, height);
    }

    private void calculateColumnsAndRows(){
        frameColumns = (int) (sheet.getWidth() / frameSize.getWidth());
        frameRows = (int) (sheet.getHeight() / frameSize.getHeight());
    }
    private void spreadTextures(){
        TextureRegion[][] tmp = TextureRegion.split(sheet,
                sheet.getWidth() / frameColumns,
                sheet.getHeight() / frameRows);

        TextureRegion[] animationFrames = new TextureRegion[frameColumns * frameRows];
        int index = 0;
        for (int rowIndex = 0; rowIndex < frameRows; rowIndex++) {
            for (int columnIndex = 0; columnIndex < frameColumns; columnIndex++) {
                animationFrames[index++] = tmp[rowIndex][columnIndex];
            }
        }
        animation = new Animation<>(1f/animationFrames.length/FrameRateMultiplier, animationFrames);
        stateTime = 0f;
    }

    public abstract void act(final float delta);
    public abstract void draw();

    public void dispose() {
        sheet.dispose();
    }

    public Size getTextureSize() {
        return frameSize;
    }
    public Animation<TextureRegion> getAnimation(){
        return animation;
    }
}
