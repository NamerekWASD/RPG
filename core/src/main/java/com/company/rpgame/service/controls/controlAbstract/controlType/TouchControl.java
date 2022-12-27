package com.company.rpgame.service.controls.controlAbstract.controlType;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.company.rpgame.configuration.preferences.ControlData;
import com.company.rpgame.service.controls.controlAbstract.AbstractControl;
import com.company.rpgame.service.controls.controlAbstract.ControlType;

public class TouchControl extends AbstractControl {
    private final Vector2 entityPosition = new Vector2();
    private boolean isMoving;
    private float x;
    private float y;
    @Override
    public void attachInputListener(Stage stage) {
        stage.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return false;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, final int pointer) {
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, final int pointer, final int button) {
            }
        });
    }
    @Override
    public void attachInputListener(final InputMultiplexer inputMultiplexer) {
        inputMultiplexer.addProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(final int screenX, final int screenY, final int pointer, final int button) {
                updateDirection(screenX, Gdx.graphics.getHeight() - screenY);
                isMoving = true;
                getListener().jump();
                return false;
            }

            @Override
            public boolean touchDragged(final int screenX, final int screenY, final int pointer) {
                updateDirection(screenX, Gdx.graphics.getHeight() - screenY);
                return false;
            }

            @Override
            public boolean touchUp(final int screenX, final int screenY, final int pointer, final int button) {
                stop();
                isMoving = false;
                return false;
            }
        });
    }

    private void updateDirection(final float x, final float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void update(final Viewport gameViewport, final float gameX, final float gameY) {
        gameViewport.project(entityPosition.set(gameX, gameY));
        if (isMoving) {
            updateMovementWithAngle(MathUtils.atan2(y - entityPosition.y, x - entityPosition.x));
        }
    }

    @Override
    public ControlData toData() {
        return new ControlData(getType()); // Touch controls require no shortcuts.
    }

    @Override
    public void copy(final ControlData data) {
        // Touch controls require no shortcuts.
    }

    @Override
    public ControlType getType() {
        return ControlType.TOUCH;
    }


}
