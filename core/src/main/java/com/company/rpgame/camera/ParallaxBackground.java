package com.company.rpgame.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import lombok.val;

public class ParallaxBackground implements Disposable {
    private final static int LAYER_SPEED_DIFFERENCE = 2;
    private final static int MODERATOR = 100;

    private final Batch batch;
    private final Array<Texture> layers;

    public ParallaxBackground(String parallaxPath){
        layers = loadTextures(parallaxPath);
        batch = new SpriteBatch();
        for(int i = 0; i < layers.size;i++){
            layers.get(i).setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.Repeat);
        }
    }
    private Array<Texture> loadTextures(String parallaxPath){
        Array<Texture> textures = new Array<>();
        for (val child :
                Gdx.files.internal(parallaxPath).list()) {
            textures.add(new Texture(child));
        }
        return textures;
    }

    public void draw(Vector2 playerCoordinates) {
        float scrollX = playerCoordinates.x / MODERATOR/2;
        float scrollY = playerCoordinates.y / MODERATOR;
        batch.begin();
        for(int i = 0; i < layers.size; i++) {
            int srcX = (int) (scrollX + i * LAYER_SPEED_DIFFERENCE * scrollX);
            int srcY = (int) -(scrollY + i * LAYER_SPEED_DIFFERENCE + scrollY);
            batch.draw(layers.get(i),
                    0, 0,
                    srcX, srcY,
                    layers.get(i).getWidth(), layers.get(i).getHeight());
        }
        batch.end();
    }

    @Override
    public void dispose() {
        for (val layer :
                new Array.ArrayIterator<>(layers)) {
            layer.dispose();
        }
        layers.clear();
    }
}
