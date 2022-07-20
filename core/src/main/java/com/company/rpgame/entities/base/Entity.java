package com.company.rpgame.entities.base;

import static com.company.rpgame.helpers.Constants.PPM;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Entity {
    protected Body body;

    public Entity(Body body){
        this.body = body;
    }
    public Entity(){
    }
    public abstract void update(final float delta);

    public Body createHitBox(int x, int y, int width, int height, boolean isStatic, World world){
        BodyDef def = new BodyDef();
        defineBody(def, isStatic);

        def.position.set(x/PPM, y/PPM);
        def.fixedRotation = true;

        return initSquareBody(world, def, width, height);
    }
    private Body initSquareBody(World world, BodyDef def, int width, int height){

        Body body = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox( width/2f/PPM, height/2f/PPM);

        body.createFixture(shape, 1.0f).setUserData(this);

        shape.dispose();

        return body;
    }

    protected static void defineBody (BodyDef def, boolean isStatic){
        if(isStatic){
            def.type = BodyDef.BodyType.StaticBody;
        }else{
            def.type = BodyDef.BodyType.DynamicBody;
        }
    }

    public abstract void dispose();
}
