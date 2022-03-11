package com.github.annasajkh.shapes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.github.annasajkh.Core;
import com.github.annasajkh.objects.GameObject;

public class Line extends GameObject
{
    public Vector2 point1;
    public Vector2 point2;
    Vector2 normal;
    
    public Line(Vector2 point1, Vector2 point2, Vector2 marchingObjectPosition)
    {
        //calculate center
        super(point1.cpy().add(point2.cpy().sub(point1).scl(0.5f)));
//        
//        normal = new Vector2(position.cpy().sub(new Vector2(marchingObjectPosition.x + Core.squareResolution / 2,
//                                                            marchingObjectPosition.y + Core.squareResolution / 2))).nor();
        
        this.point1 = point1;
        this.point2 = point2;
    }
    
    
    private Vector2 lineIntersection(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4)
    {
        float uA = ((x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3)) 
                    / ((y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1));
        
        float uB = ((x2 - x1) * (y1 - y3) - (y2 - y1) * (x1 - x3)) 
                    / ((y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1));

        if (uA >= 0 && uA <= 1 && uB >= 0 && uB <= 1)
        {
            return new Vector2(x1 + (uA * (x2 - x1)), y1 + (uA * (y2 - y1)));
        }
        return null;
    }

    public Vector2 getIntersectionPoint(Line other)
    {
        return lineIntersection(point1.x, point1.y, point2.x, point2.y, other.point1.x, 
                                other.point1.y, other.point2.x, other.point2.y);
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
        Core.shapeRenderer.rectLine(point1.x, point1.y, point2.x, point2.y, Core.player.camera.zoom + 1);
    }
}
