package com.company.rpgame.entity.data;

import com.company.rpgame.helpers.Percentage;

public class EntityData {

    private final float maxHealthPoints;
    private float healthPoints;
    private final float maxStamina;
    private float stamina;

    public enum EntityDataEnum {
        healthPoints,
        stamina
    }

    public EntityData(float maxHealthPoints, float maxStamina){
        this.maxHealthPoints = maxHealthPoints;
        healthPoints = maxHealthPoints;
        this.maxStamina = maxStamina;
        stamina = maxStamina;
    }

    public void changeValue(EntityDataEnum dataType, float value){
        switch (dataType){
            case stamina:
                stamina += value;
                break;
            case healthPoints:
                healthPoints += value;
                break;
            default:
                break;
        }
    }
    public float getPercentageOf(EntityDataEnum dataType){
        float value = 0;
        switch (dataType){
            case stamina:
                value = Percentage.getPercentageValueFromValue(healthPoints, maxHealthPoints);
                break;
            case healthPoints:
                value = Percentage.getPercentageValueFromValue(stamina, maxStamina);
                break;
            default:
                break;
        }
        return value;
    }
}
