package com.company.rpgame.entity.base;

import com.badlogic.gdx.math.Vector2;

import static com.company.rpgame.entity.base.EntityStateEnum.*;

public class EntityState {

    private boolean isMovementDirectionLeft = true;
    private boolean isStateChanged = false;

    private EntityStateEnum currentState = Idle, previousState = currentState;

    public void updateState(Vector2 direction){
        if(direction.x == 0 && direction.y == 0){
            currentState = Idle;
        }
        if(direction.x != 0){
            currentState = Walk;
        }
        if(direction.y > 0 ){
            currentState = Jump;
        }
        if(direction.y < 0){
            currentState = Falling;
        }

        if(direction.x > 0){
            isMovementDirectionLeft = false;
        }else if (direction.x < 0){
            isMovementDirectionLeft = true;
        }

        if(previousState != currentState){
            previousState = currentState;
            isStateChanged = true;
        }
    }

    public EntityStateEnum getState() {
        return currentState;
    }

    public boolean isStateChanged() {
        return isStateChanged;
    }

    public boolean isMovementDirectionLeft() {
        return isMovementDirectionLeft;
    }

    public void setStateChanged(boolean stateChanged) {
        isStateChanged = stateChanged;
    }
}
