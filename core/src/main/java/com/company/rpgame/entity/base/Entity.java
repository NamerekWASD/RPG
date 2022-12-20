package com.company.rpgame.entity.base;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.company.rpgame.entity.data.EntityData;
import com.company.rpgame.entity.data.EntityMovement;
import com.company.rpgame.helpers.Box2D.BodyBuilder;
import com.company.rpgame.helpers.Box2D.components.Size;

import static com.company.rpgame.helpers.Constants.PPM;

public abstract class Entity {

    private final EntityState entityState = new EntityState();
    private EntityData data = new EntityData(100);
    private Body body;
    private Size bodySize;
    protected EntityMovement movement = new EntityMovement();
    public Entity(){}

    protected void createBody(World world, Vector2 position, Size size,
                              boolean isStatic, boolean isFixedRotation){
        body = BodyBuilder.createBody(world, position, size, isStatic, isFixedRotation);
        bodySize = size;
    }
    protected Size getBodySize(){
        return bodySize;
    }

    protected Vector2 getBodyVelocity() {
        return body.getLinearVelocity();
    }

    public Vector2 getBodyPosition(){return body.getPosition();}

    public Vector2 getBodyCenter(){
        return new Vector2(getBodyPosition().x * PPM - (bodySize.getWidth()/2f),
                getBodyPosition().y * PPM - (bodySize.getHeight()/2f));
    }

    protected void applyJumpVelocity() {
        body.applyForceToCenter(0f, movement.getJumpForce(), true);
    }

    protected void move(Vector2 velocity){
        body.setActive(true);
        body.setLinearVelocity(velocity);
    }

    public abstract void update(final float delta);

    public abstract void dispose();

    public EntityData getData() {
        return data;
    }

}