package com.company.rpgame.entity.items.basic;


public class Weapon extends Item {

    private Double damage;
    private Double durability;
    private Double weight;

    public Weapon(Double damage, Double durability, Double weight) {
        this.damage = damage;
        this.durability = durability;
        this.weight = weight;
    }

    public Double getWeight() {
        return weight;
    }

    public Double getDurability() {
        return durability;
    }

    public void setDurability(Double durability) {
        this.durability = durability;
    }

    public Double getDamage() {
        return damage;
    }

    @Override
    public void render() {

    }
}
