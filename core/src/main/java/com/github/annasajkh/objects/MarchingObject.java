package com.github.annasajkh.objects;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.github.annasajkh.shapes.Triangle;

public class MarchingObject extends GameObject
{
    protected float value;
    public List<Triangle> triangles;
    
    public MarchingObject(Vector2 position, float value)
    {
        super(position);
        this.value = value;
        triangles =  new ArrayList<>();
        
    }
    
    @Override
    public void update(float deltaTime)
    {
        
    }

    @Override
    public void draw()
    {
        for(Triangle triangle : triangles)
        {
            triangle.draw();
        }
    }
    
    public float getValue()
    {
        return value;
    }
    
}
