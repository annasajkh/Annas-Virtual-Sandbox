package com.github.annasajkh.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.github.annasajkh.Core;
import com.github.annasajkh.utils.WorldGeneration;

public class Player extends Entity
{
    public OrthographicCamera camera;
    
    protected Vector2 movementVector;
    protected Vector2 cameraSnapPosition;
    protected Vector2 cameraCheckPosition;
    
    public Player(Vector2 position, Vector2 size, float speed, float jumpHeight)
    {
        super(position, size, speed, jumpHeight);
        
        movementVector = new Vector2();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        camera.position.x = position.x;
        camera.position.y = position.y;
        camera.update();
        
        cameraSnapPosition = new Vector2(WorldGeneration.snapToChunkPosition(camera.position.x),
                                         WorldGeneration.snapToChunkPosition(camera.position.y));
        cameraCheckPosition = new Vector2(WorldGeneration.snapToChunkPosition(camera.position.x),
                                          WorldGeneration.snapToChunkPosition(camera.position.y));

        Core.chunks.putAll(WorldGeneration.generateChunks(camera.position.x, camera.position.y));
        System.out.println("generate");
        System.out.println(Core.chunks.size);
    }
    
    public void movePlayerCamera()
    {
        //smooth camera movement
        if(!camera.position.epsilonEquals(position.x, position.y, 0))
        {   
            float dirX = position.x - camera.position.x;
            float dirY = position.y - camera.position.y;
            
            float length = Vector2.len(dirX, dirY);
            
            dirX /= length;
            dirY /= length;
            
            camera.position.x += dirX * length * Core.cameraSmoothFactor;
            camera.position.y += dirY * length * Core.cameraSmoothFactor;
        }

        camera.update();
    }

    @Override
    public void control()
    {
        movementVector.setZero();
        
        if(Gdx.input.isKeyPressed(Keys.D))
        {
            movementVector.x += speed;
        }
        else if(Gdx.input.isKeyPressed(Keys.A))
        {
            movementVector.x -= speed;
        }
        
        if(Gdx.input.isKeyPressed(Keys.W))
        {
            movementVector.y += speed;
        }
        else if(Gdx.input.isKeyPressed(Keys.S))
        {
            movementVector.y -= speed;
        }


        movementVector.nor().scl(speed);
        
        velocity = movementVector;
        
//        if(Gdx.input.isKeyJustPressed(Keys.SPACE) && velocity.y < 0 )
//        {
//            velocity.y += jumpHeight;
//        }
       
    }
    
    //check if player leave the current chunk
    public void checkLeaveChunk()
    {   
        cameraCheckPosition.x =  WorldGeneration.snapToChunkPosition(camera.position.x);
        cameraCheckPosition.y = WorldGeneration.snapToChunkPosition(camera.position.y);
        
        if(!cameraSnapPosition.epsilonEquals(cameraCheckPosition))
        {
            //the key is the camera snap position
            String key = cameraCheckPosition + "" + cameraCheckPosition.x;
            
            if(!Core.chunks.containsKey(key))
            {
                Core.generateChunk = true;
            }
            
            cameraSnapPosition.x = cameraCheckPosition.x;
            cameraSnapPosition.y = cameraCheckPosition.y;
        }

    }

    @Override
    public void updateBegin()
    {
       checkLeaveChunk();
    }

    @Override
    public void updateEnd()
    {
        movePlayerCamera();
    }

}
