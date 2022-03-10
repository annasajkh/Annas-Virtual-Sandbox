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
    
//    public void updateStatus(int state)
//    {
//        if(value < 0.5)
//        {            
//            if(state == 0)
//            {
//                if(lines.isEmpty())
//                {
//                    renderRect = true;
//                    
//                }
//            }
//        }
//    }
    
    @Override
    public void update(float deltaTime)
    {
        
    }

    @Override
    public void draw()
    {

        
        
        
//        if( position.x < Core.player.camera.position.x + Gdx.graphics.getWidth() / 2 + 100 &&
//            position.x > Core.player.camera.position.x - Gdx.graphics.getWidth() / 2 - 100 &&
//            position.y < Core.player.camera.position.y + Gdx.graphics.getHeight() / 2 + 100 &&
//            position.y > Core.player.camera.position.y - Gdx.graphics.getHeight() / 2 - 100
//            )
//        {

//        if(renderRect)
//        {
//            Core.shapeRenderer.setColor(Core.hsvToRgba(value, 1, 1, 1));
//            
//            Core.shapeRenderer.triangle(position.x + Core.squareResolution / 2, position.y,
//                                        position.x, position.y + Core.squareResolution / 2,
//                                        position.x + Core.squareResolution, position.y + Core.squareResolution / 2);
//            
//            Core.shapeRenderer.triangle(position.x, position.y + Core.squareResolution / 2,
//                                        position.x + Core.squareResolution, position.y + Core.squareResolution / 2,
//                                        position.x + Core.squareResolution / 2, position.y + Core.squareResolution);
//        }

        for(Line line : lines)
        {
            line.draw();
        }
            
        //}
    }
    
    public float getValue()
    {
        return value;
    }
    
}
