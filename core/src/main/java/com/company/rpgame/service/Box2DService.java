package com.company.rpgame.service;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.company.rpgame.exception.NoSpawnPointException;
import com.company.rpgame.helper.Box2D.TiledMapUtils;
import com.company.rpgame.service.listeners.WorldListener;
import com.github.czyzby.autumn.annotation.Component;
import com.github.czyzby.autumn.annotation.Inject;
import net.dermetfan.gdx.physics.box2d.Box2DMapObjectParser;

import java.util.Objects;

import static com.company.rpgame.helper.Constants.*;


@Component
public class Box2DService extends Game {
    private final Box2DMapObjectParser parser = new Box2DMapObjectParser(1/PPM);
    private final Batch batch = new SpriteBatch();
    @Inject
    private CameraService cameraService;
    private Box2DDebugRenderer bdr;

    private World world;
    private TiledMap map;
    private OrthogonalTiledMapRenderer tmr;

    private Status status;


    enum Status{
        RUN,
        PAUSE
    }

    public void create() {
        dispose();

        bdr = new Box2DDebugRenderer();
        status = Status.RUN;
        world = new World(GRAVITY, true);

        world.setContactListener(new WorldListener());

        map = new TmxMapLoader().load("maps/test/newMap.tmx");
        parser.load(world, map);
        parser.getFixtures().forEach((fixture) -> fixture.value.setUserData(this));

        tmr = new OrthogonalTiledMapRenderer(map, batch);
    }


    public void render(float delta) {
        if(status == Status.RUN){
            Gdx.gl.glClearColor(.25f,.5f,.8f,.9f);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            update(delta);
        }else{
            Gdx.gl.glClearColor(1f, 1f, 1f, 0.5f);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        }
        tmr.render();

        bdr.render(world, cameraService.getProjectionMatrix().scl(PPM));
        if(Gdx.input.isKeyPressed(Input.Keys.C)){
            bdr.setDrawBodies(false);
            bdr.setDrawInactiveBodies(false);
        }else{
            bdr.setDrawInactiveBodies(true);
            bdr.setDrawBodies(true);
        }
    }

    @Override
    public void resize(int width, int height) {
        cameraService.resize(width, height);
    }

    @Override
    public void pause() {
        status = Status.PAUSE;
    }

    @Override
    public void resume() {
        status = Status.RUN;
    }

    @Override
    public void dispose() {
        if (bdr != null) {
            bdr.dispose();
            bdr = null;
        }
        if (world != null) {
            world.dispose();
            world = null;
        }
        if (tmr != null) {
            tmr.dispose();
            tmr = null;
        }
        if (map != null) {
            map.dispose();
            map = null;
        }
    }

    private void update(final float ignoredDelta) {
        world.step(DEFAULT_STEP, 8, 3);
        tmr.setView((OrthographicCamera) cameraService.getCamera());
    }
    public boolean isRunning() {
        return status == Status.RUN;
    }
    public World getWorld() {
        return world;
    }
    public Vector2 getWorldSpawnPoint() throws NoSpawnPointException {
        Vector2 spawnPoint = new Vector2();

        RectangleMapObject spawnPointRectangle = TiledMapUtils.fetchRectangle(map, "Utils", "spawnPoint");
        if(Objects.isNull(spawnPointRectangle)){
            throw new NoSpawnPointException("Map must have spawn point!");
        }
        spawnPoint.x = spawnPointRectangle.getRectangle().x;
        spawnPoint.y = spawnPointRectangle.getRectangle().y;

        return spawnPoint;
    }
}
