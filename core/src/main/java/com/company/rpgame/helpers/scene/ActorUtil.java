package com.company.rpgame.helpers.scene;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class ActorUtil {
    public static Actor getActor(Stage stage, String actorName){
        return stage.getRoot().findActor(actorName);
    }
    public static Actor getActor(Stage stage, int actorName){
        return stage.getRoot().findActor(String.valueOf(actorName));
    }

    public static Vector2 convertCoordinates(Actor sourceActor, Actor targetActor, Actor actor){
        return sourceActor.localToActorCoordinates(targetActor, new Vector2(actor.getX(), actor.getY()));
    }
    public static Vector2 convertCoordinates(Actor sourceActor, Actor targetActor, Vector2 coordinates){
        return sourceActor.localToActorCoordinates(targetActor, new Vector2(coordinates.x, coordinates.y));
    }
}
