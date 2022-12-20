package com.company.rpgame.service.ui;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.company.rpgame.Application;
import com.company.rpgame.helpers.Constants;
import com.github.czyzby.autumn.annotation.Component;

@Component
public class CameraService {
    private static final Viewport viewport =
            new FillViewport(Application.WIDTH/ Constants.APPLICATION_SCALE, Application.HEIGHT/Constants.APPLICATION_SCALE);
    public Viewport getViewport() {
        return viewport;
    }

    public void setPosition(Vector3 position) {
        viewport.getCamera().position.x = position.x;
        viewport.getCamera().position.y = position.y;
        resize();
    }
    private void resize(){
        viewport.getCamera().update();
        viewport.apply();
    }
    public void resize(int width, int height){
        viewport.update(width, height);
    }

    public Matrix4 getProjectionMatrix() {
        return getViewport().getCamera().combined;
    }

    public Camera getCamera() {
        return viewport.getCamera();
    }
}
