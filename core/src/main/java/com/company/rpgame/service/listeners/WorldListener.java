package com.company.rpgame.service.listeners;

import com.badlogic.gdx.physics.box2d.*;

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
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
