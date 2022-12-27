package com.company.rpgame.helper.Box2D.components;

public final class Size {
    private float width;
    private float height;

    public Size() {
    }

    public Size(float width, float height){
        this.width = width;
        this.height = height;
    }
    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

    @Override
    public String toString() {
        return "Size{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }
}
