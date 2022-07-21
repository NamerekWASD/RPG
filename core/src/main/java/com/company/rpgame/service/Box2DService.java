package com.company.rpgame.service;

import static com.company.rpgame.helpers.Constants.DEFAULT_STEP;
import static com.company.rpgame.helpers.Constants.PPM;
import static com.company.rpgame.helpers.Constants.EARTH_GRAVITY;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.company.rpgame.Application;
import com.company.rpgame.entities.Player;
import com.company.rpgame.entities.base.ParticleEmitterTest;
import com.company.rpgame.service.listeners.WorldListener;
import com.github.czyzby.autumn.annotation.Component;
import com.github.czyzby.autumn.annotation.Inject;

import com.github.czyzby.lml.annotation.LmlAction;
import com.github.czyzby.lml.parser.action.ActionContainer;
import net.dermetfan.gdx.physics.box2d.Box2DMapObjectParser;


@Component
public class Box2DService extends Game implements Screen {
    private Box2DDebugRenderer bdr;

    private Player player;
    private World world;
    private TiledMap map;

    private Viewport viewport = new FitViewport(Application.WIDTH, Application.HEIGHT);

    private OrthogonalTiledMapRenderer tmr;

    private SpriteBatch batch;

    @Inject private ControlsService controlsService;

    private Status status;

    private ParticleEmitterTest particleEmitter;

    public float getViewportHeight() {
        return viewport.getScreenHeight();
    }

    public int getPlayerMaxHealthPoints() {
        return player.getMaxHealthPoints();
    }

    enum Status{
        RUN,
        PAUSE
    }


    @Override
    public void create() {
        dispose();

        particleEmitter = new ParticleEmitterTest();

        particleEmitter.create();

        bdr = new Box2DDebugRenderer();

        status = Status.RUN;

        batch = new SpriteBatch();

        world = new World(EARTH_GRAVITY, true);

        player = new Player(controlsService.getControl(), world, viewport);

        world.setContactListener(new WorldListener());

        map = new TmxMapLoader().load("maps/test/testmap.tmx");
        Box2DMapObjectParser parser = new Box2DMapObjectParser(1f/PPM);
        parser.load(world, map);
        for (ObjectMap.Entry<String, Fixture> fixture:
             parser.getFixtures()) {
            fixture.value.setUserData(this);
        }

        tmr = new OrthogonalTiledMapRenderer(map, parser.getUnitScale()*PPM);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(status == Status.RUN){
            Gdx.gl.glClearColor(.25f,.5f,.8f,.8f);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            particleEmitter.render(batch, delta);
            update(delta);
        }else{
            Gdx.gl.glClear(GL20.GL_DEPTH_BUFFER_BIT);
        }
        player.draw(batch, delta);
        bdr.render(world, viewport.getCamera().combined.scl(PPM));
        tmr.render();
        viewport.apply();
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
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
    public void hide() {

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
        if(player !=null){
            player.dispose();
            player = null;
        }

        if(particleEmitter != null){
            particleEmitter.dispose();
            particleEmitter = null;
        }
    }

    public void update(final float delta) {
        world.step(DEFAULT_STEP, 8, 3);
        player.update(delta);
        tmr.setView((OrthographicCamera) viewport.getCamera());
        cameraUpdate(delta);
        batch.setProjectionMatrix(viewport.getCamera().combined);
    }

    private void cameraUpdate(final float delta){
        Vector3 position = viewport.getCamera().position;
        position.x = player.getBodyPosition().x * PPM;
        position.y = player.getBodyPosition().y * PPM;

        viewport.getCamera().position.set(position);

        viewport.getCamera().update();
    }

    public void initiateControls(final InputMultiplexer inputMultiplexer) {
        player.getControl().attachInputListener(inputMultiplexer);
        particleEmitter.attachInputListener(inputMultiplexer);
    }

    public int getPlayerHealthPoints (){
        return player.getHealthPoints();
    }

    public boolean isRunning() {
        return status == Status.RUN;
    }
}
