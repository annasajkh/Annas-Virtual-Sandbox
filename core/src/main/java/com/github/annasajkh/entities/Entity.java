package com.github.annasajkh.entities;

import com.badlogic.gdx.math.Vector2;
import com.github.annasajkh.shapes.Rect;

public abstract class Entity extends Rect
{
    Vector2 velocity;
    float speed, jumpHeight;
    

    public Entity(Vector2 position, Vector2 size, float speed, float jumpHeight)
    {
        super(position, size);
        
        this.speed = speed;
        this.jumpHeight = jumpHeight;
        this.velocity = new Vector2();
    }

    @Override
    public void update(float deltaTime)
    {
        control();
        updateBegin();
        
        //update position
        position.x += velocity.x * deltaTime; 
        position.y += velocity.y * deltaTime;
        
        updateEnd();
    }
    
    public abstract void control();
    
    public abstract void updateBegin();
    
    public abstract void updateEnd();
}
