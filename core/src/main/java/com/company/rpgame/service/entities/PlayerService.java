package com.company.rpgame.service.entities;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.company.rpgame.entity.items.basic.Item;
import com.company.rpgame.entity.player.Player;
import com.company.rpgame.entity.player.PlayerInventory;
import com.company.rpgame.service.ControlsService;
import com.company.rpgame.service.ui.CameraService;
import com.github.czyzby.autumn.annotation.Component;
import com.github.czyzby.autumn.annotation.Inject;

import static com.company.rpgame.helpers.Constants.PPM;

@Component
public class PlayerService implements Disposable {
    private final SpriteBatch batch = new SpriteBatch();
    @Inject
    private CameraService cameraService;
    @Inject
    private ControlsService controlsService;
    private World world;
    private Player player;

    public void initiateControls(final InputMultiplexer inputMultiplexer) {
        player.getControl().attachInputListener(inputMultiplexer);
    }
    public void create(Vector2 spawnPoint){
        player = new Player(batch, controlsService.getControl(),
                world, spawnPoint, cameraService.getViewport());
    }

    public void render(float delta) {
        update(delta);
        player.render(delta);
    }

    private void update(float delta) {
        batch.setProjectionMatrix(cameraService.getProjectionMatrix().scl(1/PPM));
        updateCamera(delta);
    }

    private void updateCamera(float delta){
        Vector3 position = cameraService.getViewport().getCamera().position;
        position.x = player.getBodyPosition().x * PPM;
        position.y = player.getBodyPosition().y * PPM;
        cameraService.setPosition(position);
    }

    public void resize(int width, int height) {
        cameraService.resize(width, height);
    }
    public Player getPlayer() {
        return player;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    @Override
    public void dispose() {
        if(player !=null){
            player.dispose();
            player = null;
        }
        if(world != null){
            world.dispose();
            world = null;
        }
    }
    public int getMaxItemCount() {
        return player.getInventory().getMaxItemCount();
    }

    public void updateInventory(PlayerInventory collectedData) {
        player.updateInventory(collectedData.getInventoryItems());
        player.updateEquipped(collectedData.getEquippedItems());
    }


    public Array<Item> getAllItems() {
        return player.getInventory().getAllItems();
    }
}
