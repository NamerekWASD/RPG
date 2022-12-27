package com.company.rpgame.entity.data;

import static com.company.rpgame.helper.Constants.PPM;

public class EntityMovement {
    private final float speed = .25f*PPM;
    private final float maxSpeed = .5f*PPM;
    private float jumpForce = 100f*PPM;
    private float moveSpeed = 0;

    public float getJumpForce() {
        return jumpForce;
    }

    public float calculateAndGetMoveSpeed(final float moveOnX, final float delta){
        if(moveOnX == 0){
            decreaseSpeed(delta);
        }
        else {
            increaseSpeed(moveOnX, delta);
        }
        return moveSpeed;
    }

    private void increaseSpeed(final float moveOnX, final float delta) {
        if(moveSpeed < 5 || moveSpeed > 5){
            moveSpeed = moveOnX * 5;
        }
        moveSpeed += moveOnX * speed * delta;
        if(isExceededSpeedLimit(moveOnX)){
            decreaseSpeed(delta);
        }
    }

    private boolean isExceededSpeedLimit(final float moveOnX) {
        if(isMovingLeft(moveOnX)){
            return moveSpeed < maxSpeed * moveOnX;
        }else {
            return moveSpeed > maxSpeed * moveOnX;
        }
    }

    private boolean isMovingLeft(final float moveOnX){
        return moveOnX < 0;
    }

    private void decreaseSpeed(final float delta) {
        if(moveSpeed == 0)
            return;
        if(isMovingLeft(moveSpeed)){
            moveSpeed += speed * delta;
        }else{
            moveSpeed -= speed * delta;
        }
        if(moveSpeed <= 3f && moveSpeed >= -3f){
            moveSpeed = 0;
        }
    }

}
