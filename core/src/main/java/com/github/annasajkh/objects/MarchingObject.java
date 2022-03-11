package com.github.annasajkh.objects;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.github.annasajkh.Core;
import com.github.annasajkh.shapes.Line;

public class MarchingObject extends GameObject
{
    protected float value;
    public List<Line> lines;
    public boolean renderRect = false;
    
    public MarchingObject(Vector2 position, float value)
    {
        super(position);
        this.value = value;
        lines =  new ArrayList<>();
        
    }
    
    @Override
    public void update(float deltaTime)
    {
        
    }

    @Override
    public void draw()
    {
        for(Line line : lines)
        {
            line.draw();
        }
    }
    
    public float getValue()
    {
        return value;
    }
    
}
