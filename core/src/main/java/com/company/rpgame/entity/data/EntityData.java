package com.company.rpgame.entity.data;

import com.company.rpgame.helpers.Percentage;

public class EntityData {
    private final float maxHealthPoints;
    private float healthPoints;

    public EntityData(float maxHealthPoints){
        this.maxHealthPoints = maxHealthPoints;
        healthPoints = maxHealthPoints;
    }

    public void addHealthPoints(float healthPoints){
        this.healthPoints += healthPoints;
    }

    public void subtractHealthPoints(float healthPoints){
        this.healthPoints -= healthPoints;
    }

    public float getPercentageOfHealthPoints(){
        return Percentage.getPercentageValueFromValue(healthPoints, maxHealthPoints);
    }
}
