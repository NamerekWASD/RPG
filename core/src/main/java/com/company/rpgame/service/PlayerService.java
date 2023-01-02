package com.company.rpgame.service;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.company.rpgame.entity.items.basic.Item;
import com.company.rpgame.entity.player.Player;
import com.company.rpgame.entity.player.PlayerInventory;
import com.company.rpgame.exception.NoSpawnPointException;
import com.github.czyzby.autumn.annotation.Component;
import com.github.czyzby.autumn.annotation.Inject;

import static com.company.rpgame.helper.Constants.PPM;

@Component
public class PlayerService implements Disposable {
    private final SpriteBatch batch = new SpriteBatch();
    @Inject
    private CameraService cameraService;
    @Inject
    private ControlService controlService;
    @Inject
    private Box2DService world;
    private Player player;

    public void initiate() throws NoSpawnPointException {
        player = new Player(controlService.getPlayerControl(), world.getWorld(),
                world.getPlayerSpawnPoint(), cameraService.getViewport());
    }

    public void render(float delta) {
        if(world.isRunning()){
            update(delta);
        }
        player.render(batch);
    }

    private void update(float delta) {
        player.update(delta);
        batch.setProjectionMatrix(cameraService.getProjectionMatrix().scl(1/PPM));
        updateCamera();
    }

    private void updateCamera(){
        Vector3 position = cameraService.getViewport().getCamera().position;
        position.x = player.getBodyPosition().x * PPM;
        position.y = player.getBodyPosition().y * PPM + player.getNatureSize().getHeight();
        cameraService.setPosition(position);
    }

    public void resize(int width, int height) {
        cameraService.resize(width, height);
    }
    public Player getPlayer() {
        return player;
    }

    @Override
    public void dispose() {
        if(player !=null){
            player.dispose();
            player = null;
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
