package com.github.annasajkh.shapes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.github.annasajkh.Core;
import com.github.annasajkh.objects.GameObject;

public class Triangle extends GameObject
{   
    public Vector2 point1;
    public Vector2 point2;
    public Vector2 point3;
    
    public Triangle(Vector2 point1, Vector2 point2, Vector2 point3, Vector2 center)
    {
        super(new Vector2());
        
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
    }
    
    

    @Override
    public void update(float deltaTime)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void draw()
    {
        Core.shapeRenderer.setColor(Color.WHITE);
        Core.shapeRenderer.triangle(point1.x, point1.y, point2.x, point2.y, point3.x, point3.y);
    }
}
