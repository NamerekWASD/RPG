package com.company.rpgame.entity.components.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.utils.Disposable;
import com.company.rpgame.helper.AssetsUtil;

public class Particle implements Disposable {

    private final ParticleEffect effect;

    public Particle(String effectName){
        effect = new ParticleEffect();
        effect.load(AssetsUtil.findFile("particle/" + effectName, effectName),
                Gdx.files.internal("particle/" + effectName));
        effect.start();
    }

    public void setPosition(float x, float y){
        effect.setPosition(x, y);
    }


    @Override
    public void dispose () {
        effect.dispose();
    }

    public void draw(Batch batch, final float delta) {
        effect.draw(batch, delta);
    }
}
