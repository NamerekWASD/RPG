package com.company.rpgame.entity.base;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.company.rpgame.helpers.Box2D.components.Size;

import static com.company.rpgame.helpers.Constants.PPM;

public class EntityBody {
    protected Body body;
    private Size bodySize;

    public Body create(Vector2 position, Size size, boolean isStatic, World world){
        bodySize = size;

        BodyDef bodyDefinition = defineBody(isStatic);

        bodyDefinition.position.set(position.x/PPM, position.y/PPM);
        bodyDefinition.fixedRotation = true;

        return initSquareBody(world, bodyDefinition);
    }

    private static BodyDef defineBody(boolean isStatic){
        BodyDef bodyDefinition = new BodyDef();
        bodyDefinition.type = isStatic ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;
        return bodyDefinition;
    }

    private Body initSquareBody(World world, BodyDef def){

        Body body = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox( bodySize.getWidth()/2f/PPM, bodySize.getHeight()/2f/PPM);

        body.createFixture(shape, 1.0f).setUserData(this);

        shape.dispose();

        return body;
    }
}
