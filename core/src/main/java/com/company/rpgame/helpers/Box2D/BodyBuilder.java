package com.company.rpgame.helpers.Box2D;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.company.rpgame.helpers.Box2D.components.Size;
import com.company.rpgame.helpers.Constants;

import static com.company.rpgame.helpers.Constants.PPM;

public final class BodyBuilder {
    public static Body createBody(World world, Vector2 position, Size size,
                                  boolean isStatic, boolean isFixedRotation) {
        return createBody(world, position, size, isStatic, isFixedRotation,
                Constants.BIT_WALL, (short) (Constants.BIT_PLAYER | Constants.BIT_WALL| Constants.BIT_SENSOR),
                (short) 0);
    }

    public static Body createBody(final World world, Vector2 position, Size size,
                                  boolean isStatic, boolean isFixedRotation, short cBits, short mBits, short gIndex) {

        Body body = defineBody(world, position, isStatic, isFixedRotation);
        initializeBodyWithFixture(body, size, cBits, mBits, gIndex);
        return body;
    }

    private static Body defineBody(World world, Vector2 position, boolean isStatic, boolean isFixedRotation){
        BodyDef bodyDefinition = new BodyDef();
        bodyDefinition.position.set(position.x / PPM, position.y / PPM);
        bodyDefinition.fixedRotation = isFixedRotation;
        defineBodyType(bodyDefinition, isStatic);
        return world.createBody(bodyDefinition);
    }

    private static void defineBodyType(BodyDef bodyDefinition, boolean isStatic){
        bodyDefinition.type = isStatic ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;
    }

    private static void initializeBodyWithFixture(Body body, Size size, short cBits, short mBits, short gIndex){
        FixtureDef fixtureDef = createFixture(size, cBits, mBits, gIndex);
        body.createFixture(fixtureDef);
    }
    private static FixtureDef createFixture(Size size, short cBits, short mBits, short gIndex){
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size.getWidth() / PPM / 2, size.getHeight() / PPM / 2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0f;
        assignFilters(fixtureDef, cBits, mBits, gIndex);
        return fixtureDef;
    }

    private static void assignFilters(FixtureDef fixtureDef, short cBits, short mBits, short gIndex){
        fixtureDef.filter.categoryBits = cBits; // Is a
        fixtureDef.filter.maskBits = mBits; // Collides with
        fixtureDef.filter.groupIndex = gIndex;
    }
}
