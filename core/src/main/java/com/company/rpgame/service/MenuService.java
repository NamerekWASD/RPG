package com.company.rpgame.service;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.company.rpgame.Application;
import com.github.czyzby.autumn.annotation.Component;

@Component
public class MenuService {
    public StretchViewport viewport;
    public OrthographicCamera camera;
    public MenuService(){
        camera = new OrthographicCamera();
        viewport = new StretchViewport(Application.WIDTH, Application.HEIGHT, camera);
    }
}
