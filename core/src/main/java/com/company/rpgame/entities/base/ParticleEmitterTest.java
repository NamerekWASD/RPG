package com.company.rpgame.entities.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;


public class ParticleEmitterTest {
    ParticleEffect effect;
    int emitterIndex;
    Array<ParticleEmitter> emitters;
    int particleCount = 10;
    float fpsCounter;

    public void attachInputListener(InputMultiplexer inputMultiplexer){
        inputMultiplexer.addProcessor(new InputAdapter() {

            @Override
            public boolean touchDragged (int x, int y, int pointer) {
                effect.setPosition(x, Gdx.graphics.getHeight() - y);
                return false;
            }

            @Override
            public boolean touchDown (int x, int y, int pointer, int newParam) {
                // effect.setPosition(x, Gdx.graphics.getHeight() - y);
                ParticleEmitter emitter = emitters.get(emitterIndex);
                particleCount += 100;
                System.out.println(particleCount);
                particleCount = Math.max(0, particleCount);
                if (particleCount > emitter.getMaxParticleCount()) emitter.setMaxParticleCount(particleCount * 2);
                emitter.getEmission().setHigh(particleCount / emitter.getLife().getHighMax() * 1000);
                effect.getEmitters().clear();
                effect.getEmitters().add(emitter);
                return false;
            }

            @Override
            public boolean keyDown (int keycode) {
                ParticleEmitter emitter = emitters.get(emitterIndex);
                if (keycode == Input.Keys.P)
                    particleCount += 5;
                else if (keycode == Input.Keys.L) {
                    emitter = new ParticleEmitter(emitter);
                } else if (keycode == Input.Keys.O)
                    particleCount -= 5;
                else if (keycode == Input.Keys.SPACE) {
                    emitterIndex = (emitterIndex + 1) % emitters.size;
                    emitter = emitters.get(emitterIndex);

                    // if we've previously stopped the emitter reset it
                    if (emitter.isComplete()) emitter.reset();
                    particleCount = (int)(emitter.getEmission().getHighMax() * emitter.getLife().getHighMax() / 1000f);
                } else if (keycode == Input.Keys.ENTER) {
                    emitter = emitters.get(emitterIndex);
                    if (emitter.isComplete())
                        emitter.reset();
                    else
                        emitter.allowCompletion();
                } else
                    return false;
                particleCount = Math.max(0, particleCount);
                if (particleCount > emitter.getMaxParticleCount()) emitter.setMaxParticleCount(particleCount * 2);
                emitter.getEmission().setHigh(particleCount / emitter.getLife().getHighMax() * 1000);
                effect.getEmitters().clear();
                effect.getEmitters().add(emitter);
                return false;
            }
        });
    }

    public void create () {

        effect = new ParticleEffect();
        effect.load(Gdx.files.internal("images/fire"), Gdx.files.internal("images"));
        effect.setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        // Of course, a ParticleEffect is normally just used, without messing around with its emitters.
        emitters = new Array(effect.getEmitters());
        effect.getEmitters().clear();
        effect.getEmitters().add(emitters.get(0));
    }

    public void dispose () {
        effect.dispose();
    }

    public void render (final SpriteBatch batch, final float delta) {
        batch.begin();
        effect.draw(batch, delta);
        batch.end();
        fpsCounter += delta;
        if (fpsCounter > 3) {
            fpsCounter = 0;
            int activeCount = emitters.get(emitterIndex).getActiveCount();
            Gdx.app.log("libGDX", activeCount + "/" + particleCount + " particles, FPS: " + Gdx.graphics.getFramesPerSecond());
        }
    }

    public boolean needsGL20 () {
        return false;
    }
}
