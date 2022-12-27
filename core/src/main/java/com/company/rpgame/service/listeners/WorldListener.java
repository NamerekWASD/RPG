package com.company.rpgame.service.listeners;

import com.badlogic.gdx.physics.box2d.*;
import com.company.rpgame.entity.player.Player;

public class WorldListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if(fa == null || fb == null) return;
        if(fa.getUserData() == null || fb.getUserData() == null) return;
        if(fa.getUserData() instanceof Player){
            Player player = (Player) fa.getUserData();
        }
        if(fb.getUserData() instanceof Player){
            Player player = (Player) fb.getUserData();
        }
    }

    @Override
    public void endContact(Contact contact) {

        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if(fa == null || fb == null) return;
        if(fa.getUserData() == null || fb.getUserData() == null) return;
        if(fa.getUserData() instanceof Player){
            Player player = (Player) fa.getUserData();
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
