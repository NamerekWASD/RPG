package com.company.rpgame.helpers;

public final class Percentage {
    public static float getPercentageValueFromValue(float dividedBy, float divisor){
        float percentage;
        percentage = (dividedBy/divisor) * 100;
        return percentage;
    }
    public static float getValuePercentageFromValue(float percentage, float value){
        float result;
        result = percentage/100 * value;
        return result;
    }
}
