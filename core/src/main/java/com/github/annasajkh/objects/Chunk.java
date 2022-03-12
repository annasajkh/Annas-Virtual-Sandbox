package com.github.annasajkh.objects;

import com.badlogic.gdx.math.Vector2;

public class Chunk extends GameObject
{
    public MarchingObject[][] marchingObjects;
    
    public Chunk(Vector2 position, MarchingObject[][] marchingObjects)
    {
        super(position);
        this.marchingObjects = marchingObjects;
    }

    @Override
    public void update(float deltaTime)
    {
        
    }

    @Override
    public void draw()
    {
       for(MarchingObject[] marchingObjectRow : marchingObjects)
        {
            for(MarchingObject marchingObject : marchingObjectRow)
            {
                marchingObject.draw();
            }
        }
    }
    
}
