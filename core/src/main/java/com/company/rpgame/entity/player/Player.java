package com.company.rpgame.entity.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.company.rpgame.entity.base.Entity;
import com.company.rpgame.entity.base.EntityState;
import com.company.rpgame.entity.base.EntityStateEnum;
import com.company.rpgame.entity.components.EntityAnimator;
import com.company.rpgame.entity.data.EntityData;
import com.company.rpgame.entity.data.EntityMovement;
import com.company.rpgame.entity.items.basic.Item;
import com.company.rpgame.helpers.Box2D.components.Size;
import com.company.rpgame.service.controls.ControlListener;
import com.company.rpgame.service.controls.PlayerControl;

import java.util.Arrays;

import static com.company.rpgame.entity.base.EntityStateEnum.*;
import static com.company.rpgame.helpers.Constants.PPM;

public class Player extends Entity implements ControlListener{
    private final EntityState playerState = new EntityState();
    private final ObjectMap<EntityStateEnum, EntityAnimator> animators = new ObjectMap<>();
    private final PlayerInventory inventory;
    private final PlayerControl playerControl;
    private final Viewport viewport;
    private final SpriteBatch batch;
    private EntityAnimator currentEntityAnimator;
    private Size natureSize;
    private boolean jumped = false;


    public Player(final SpriteBatch batch,
                  final PlayerControl playerControl,
                  final World world,
                  final Vector2 spawnPoint,
                  final Viewport viewport){
        this.batch = batch;
        this.playerControl = playerControl;
        this.viewport = viewport;
        this.inventory = new PlayerInventory();
        this.movement = new EntityMovement();
        this.data = new EntityData(100, 100);
        initializeBody(world, spawnPoint);
        playerControl.setControlListener(this);
        create();
    }

    private void initializeBody(World world, Vector2 spawnPoint) {
        Size bodySize = new Size(50, 100);
        boolean isStatic = false;
        boolean isFixedRotation = true;

        createBody(world, spawnPoint, bodySize, isStatic, isFixedRotation);
    }

    public void create () {
        setupAnimation();
        currentEntityAnimator = animators.get(Idle);
        natureSize = currentEntityAnimator.getTextureSize();
    }

    private void setupAnimation() {
        Arrays.stream(values()).forEach(state -> animators.put(state,
                new EntityAnimator(batch,  state.name(),
                Player.class.getSimpleName(), getBodySize())));
    }

    public void render(final float delta) {
        update(delta);
        currentEntityAnimator.updateDirection(playerState.isMovementDirectionLeft());
        currentEntityAnimator.setCurrentPosition(getPlayerCenter());
        currentEntityAnimator.act(delta);
        currentEntityAnimator.draw();
    }

    public void update(final float delta) {
        playerControl.update(viewport, getBodyPosition().x, getBodyPosition().y);
        Vector2 velocity = new Vector2(movement.calculateAndGetMoveSpeed(getPlayerMovement().x, delta),
                getBodyVelocity().y);
        if(jumped){
            applyJumpVelocity();
            jumped = false;
        }
        playerState.updateState(velocity);
        updateAnimation();
        move(velocity);
    }

    private void updateAnimation() {
        if(playerState.isStateChanged()){
            currentEntityAnimator = animators.get(playerState.getState());
            playerState.setStateChanged(false);
        }
    }

    public Vector2 getPlayerCenter(){
        return new Vector2(getBodyPosition().x * PPM - (natureSize.getWidth()/2f),
                getBodyPosition().y * PPM - (natureSize.getHeight()/2f));
    }
    private Vector2 getPlayerMovement(){
        return playerControl.getMovementDirection();
    }
    public PlayerControl getControl() {
        return playerControl;
    }

    @Override
    public void jump() {
        if(playerState.getState() != Jump && playerState.getState() != Falling){
            jumped = true;
        }
    }
    @Override
    public void dispose(){
        animators.forEach(animator -> animator.value.dispose());
        currentEntityAnimator.dispose();
        animators.clear();
    }

    public PlayerInventory getInventory() {
        return inventory;
    }

    public void updateInventory(Array<Item> items) {
        inventory.setItems(items);
    }

    public void updateEquipped(Array<Item> equippedItems) {
        inventory.setEquippedItems(equippedItems);
    }
}
