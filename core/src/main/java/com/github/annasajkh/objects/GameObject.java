package com.github.annasajkh.objects;

import com.badlogic.gdx.math.Vector2;

public abstract class GameObject
{
    protected Vector2 position;
    public String chunkKey;
    
    public GameObject(Vector2 position)
    {
        this.position = position;
    }
    
    public abstract void update(float deltaTime);
    
    public abstract void draw();
    
    public float getPositionX()
    {
        return position.x;
    }
    
    public float getPositionY()
    {
        return position.y;
    }
    
    public Vector2 getPosition()
    {
        return position;
    }
}
