package com.github.annasajkh;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ScreenUtils;
import com.github.annasajkh.entities.Player;
import com.github.annasajkh.libs.FastNoiseLite;
import com.github.annasajkh.libs.FastNoiseLite.NoiseType;
import com.github.annasajkh.objects.Chunk;
import com.github.annasajkh.utils.InputManager;
import com.github.annasajkh.utils.WorldGeneration;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Core extends ApplicationAdapter 
{
    public static short squareResolution = 30;
    public static short chunkSize = 10;
    public static short renderDistance = 4;
    
    public static float cameraSmoothFactor = 0.1f;
    public static float gravity = 10;
    public static float playerSpeed = 500;
    public static float playerJumpHeight = 500; 
    public static float zoomFactor = 0.5f;
    public static float zoomSpeed = 0.1f;
    
    public static boolean generateChunk = false;
    
    public static FastNoiseLite fastNoiseLite;
    public static ShapeRenderer shapeRenderer;
    public static Player player;
    public static Vector2 playerSize;
    public static Vector2 spawnPosition;
    public static Thread worldGeneration;

    //the key is position of the chunk "x,y" 
    //position of the chunk calculated by snapping an coordinates inside the chunk
    public static ObjectMap<String, Chunk> chunks;
    
    public static Color hsvToRgba(float hue, float saturation, float value, float alpha)
    {

        int h = (int) (hue * 6);
        float f = hue * 6 - h;
        float p = value * (1 - saturation);
        float q = value * (1 - f * saturation);
        float t = value * (1 - (1 - f) * saturation);

        switch (h)
        {
            case 0:
                return new Color(value, t, p, alpha);
            case 1:
                return new Color(q, value, p, alpha);
            case 2:
                return new Color(p, value, t, alpha);
            case 3:
                return new Color(p, q, value, alpha);
            case 4:
                return new Color(t, p, value, alpha);
            case 5:
                return new Color(value, p, q, alpha);
            default:
                throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " +
                                           hue +
                                           ", " +
                                           saturation +
                                           ", " +
                                           value);
        }
    }
    
	@Override
	public void create() 
	{
        fastNoiseLite = new FastNoiseLite(MathUtils.random(1000000));
        fastNoiseLite.SetNoiseType(NoiseType.OpenSimplex2S);
        shapeRenderer = new ShapeRenderer();
        
        chunks = new ObjectMap<>();
        worldGeneration = new WorldGeneration();
        worldGeneration.start();
        
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(new InputManager());
        Gdx.input.setInputProcessor(inputMultiplexer);
        
        playerSize = new Vector2(50, 100);
        spawnPosition = new Vector2((squareResolution * chunkSize) / 2, (squareResolution * chunkSize) / 2);
        player = new Player(spawnPosition, playerSize, playerSpeed, playerJumpHeight);
	}
	
	public void update(float deltaTime)
	{
	    
	    player.update(deltaTime);
	    
	    //make shapeRenderer be relative to the player
        shapeRenderer.setProjectionMatrix(player.camera.combined);
	}

	@Override
	public void render() 
	{
	    update(Gdx.graphics.getDeltaTime());
	    
	    
	    ScreenUtils.clear(0, 0, 0, 1);

	    shapeRenderer.begin(ShapeType.Filled);
	    synchronized(chunks)
        {
            for(Chunk chunk : chunks.values())
            {
                chunk.draw();
            }
        }
	    player.draw();
	    shapeRenderer.end();
	    
	    
	}
	
	//don't forget to disposes
	@Override
	public void dispose() 
	{
	    worldGeneration.interrupt();
	    shapeRenderer.dispose();
	    System.exit(0);
	}
}