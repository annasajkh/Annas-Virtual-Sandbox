package com.github.annasajkh.shapes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.github.annasajkh.Core;
import com.github.annasajkh.objects.GameObject;

public abstract class Rect extends GameObject
{
    Vector2 size;
    protected Color color;
    public Rect(Vector2 position, Vector2 size)
    {
        super(position);
        
        this.size = size;
        color = Color.WHITE;
    }


    @Override
    public void draw()
    {
        Core.shapeRenderer.setColor(color);
        Core.shapeRenderer.rect(position.x - size.x / 2, position.y - size.y / 2, size.x, size.y);
    }

}
