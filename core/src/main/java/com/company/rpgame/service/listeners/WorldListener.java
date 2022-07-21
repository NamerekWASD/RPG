package com.company.rpgame.service.listeners;

import com.badlogic.gdx.physics.box2d.*;
import com.company.rpgame.entities.Player;
import com.company.rpgame.service.Box2DService;
import com.github.czyzby.autumn.annotation.Component;
import com.github.czyzby.autumn.annotation.Inject;

import java.awt.*;

public class WorldListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        /*Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if(fa == null || fb == null) return;
        if(fa.getUserData() == null || fb.getUserData() == null) return;
        if(fa.getUserData() instanceof Player){
            Player player = (Player) fa.getUserData();
            if(fb.getBody().getTransform().getPosition().y < player.getPlayerPosition().y){
                player.setPlayerState(Player.PlayerState.GROUNDED);
            }
        }*/
    }

    @Override
    public void endContact(Contact contact) {

        /*Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if(fa == null || fb == null) return;
        if(fa.getUserData() == null || fb.getUserData() == null) return;
        if(fa.getUserData() instanceof Player){
            Player player = (Player) fa.getUserData();
            player.setPlayerState(Player.PlayerState.FALLING);
        }*/
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        Transform transform = contact.getFixtureA().getBody().getTransform();

        Fixture colliderFixture = contact.getFixtureB();

         if (transform.getPosition().y < colliderFixture.getBody().getTransform().getPosition().y) {
            contact.setFriction(0);
        } else {
            contact.setFriction(1);
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
