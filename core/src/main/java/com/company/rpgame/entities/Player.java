package com.company.rpgame.entities;

import static com.company.rpgame.helpers.Constants.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.company.rpgame.entities.base.Animator;
import com.company.rpgame.entities.base.Entity;
import com.company.rpgame.service.controls.PlayerControl;
import com.company.rpgame.service.controls.ControlListener;

import java.util.Hashtable;

public class Player extends Entity implements ControlListener {
    private static final float SPEED = .25f*PPM; // Movement force.
    private static final float JUMP = 100f*PPM; // Jump force.
    private static final float CURRENT_SPEED = 0;


    private final PlayerControl playerControl;
    private final Viewport viewport;

    private int healthPoints;
    private int maxHealthPoints;
    private boolean jumped = false;



    private Hashtable<String, Animator> animator;

    private Animator currentAnimator;

    private PlayerState playerState = PlayerState.GROUNDED;

    private PlayerAction playerAction = PlayerAction.WALKING;

    private boolean isMovementDirectionLeft;


    public enum PlayerState{
        GROUNDED,
        JUMPED,
        FALLING
    }
    public enum PlayerAction{
        IDLE,
        WALKING,
        ATTACK
    }



    public Player(final PlayerControl playerControl, final World world, final Viewport viewport){
        this.playerControl = playerControl;
        this.viewport = viewport;
        body = createHitBox(200, 200, 64,128, false, world);
        playerControl.setControlListener(this);
        create();
    }

    public void create () {
        animator = new Hashtable<>();

        setupAnimation();

        maxHealthPoints = 100;

        healthPoints = maxHealthPoints;
    }

    private void setupAnimation() {
        animator.put("idle", new Animator("images/animations/Idle.png", 2, 2));
        animator.put("walk", new Animator("images/animations/Walk.png", 4, 2));
        animator.put("jump", new Animator("images/animations/Jump.png", 1, 1));
    }

    public void update(final float delta) {
        playerControl.update(viewport, getBodyPosition().x, getBodyPosition().y);

        Vector2 movement = getPlayerMovement();

        if(jumped){
            applyJumpVelocity(body);
        }

        resolveEntityState();
        resolveEntityAction(movement);

        body.setActive(true);
        body.setLinearVelocity(movement.x * SPEED, body.getLinearVelocity().y);
        jumped = false;
    }



    public void render(final SpriteBatch batch, final float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        currentAnimator.render(batch, getPlayerPosition(), delta, isMovementDirectionLeft);
    }
    public void draw(final SpriteBatch batch, final float delta){

            switch (playerState){
                case GROUNDED:
                    switch (playerAction){
                        case IDLE:
                            currentAnimator = animator.get("idle");
                            break;
                        case WALKING:
                            currentAnimator = animator.get("walk");
                            break;
                        case ATTACK:
                            currentAnimator = animator.get("attack");
                            break;
                        default:
                            break;
                    }
                    break;
                case JUMPED:
                    currentAnimator = animator.get("jump");
                    break;
                case FALLING:
                    currentAnimator = animator.get("jump");
                    break;
                default:
                    break;
        }
        render(batch, delta);
    }

    private static void applyJumpVelocity(Body body) {
        body.applyForceToCenter(0f, JUMP, true);
    }

    private Vector2 getPlayerMovement(){
        return playerControl.getMovementDirection();
    }

    public Vector2 getBodyPosition(){
        return body.getPosition();
    }
    public PlayerControl getControl() {
        return playerControl;
    }
    @Override
    public void jump() {
        jumped = true;
    }

    public Vector2 getPlayerPosition(){
        return new Vector2(getBodyPosition().x * PPM - (64f/2f),
                getBodyPosition().y * PPM - (128f/2f));
    }


    public void setPlayerState(PlayerState playerState){
        this.playerState = playerState;
    }
    @Override
    public void dispose(){
        for (Animator animator :
                animator.values()) {
            animator.dispose();
        }
        animator.clear();
    }

    private void resolveEntityAction(Vector2 movement) {
        if(movement.x == 0){
            playerAction = PlayerAction.IDLE;
        }
        else{
            playerAction = PlayerAction.WALKING;
            if(movement.x < 0){
                isMovementDirectionLeft = true;
            }else if(movement.x > 0){
                isMovementDirectionLeft = false;
            }
        }
    }

    private void resolveEntityState() {
        if(body.getLinearVelocity().y == 0){
            playerState = PlayerState.GROUNDED;
        }else{
            if(body.getLinearVelocity().y > 0){
                playerState = PlayerState.JUMPED;
            } else if (body.getLinearVelocity().y < 0) {
                playerState = PlayerState.FALLING;
            }
        }
    }


    public int getMaxHealthPoints() {
        return maxHealthPoints;
    }

    public void setMaxHealthPoints(int maxHealthPoints) {
        this.maxHealthPoints = maxHealthPoints;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }
}
