package com.github.annasajkh.utils;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.github.annasajkh.Core;
import com.github.annasajkh.objects.Chunk;
import com.github.annasajkh.objects.MarchingObject;
import com.github.annasajkh.shapes.Line;

public class WorldGeneration extends Thread
{
    private static List<Vector2> floodedVectors = new ArrayList<>();
    
    public static int getState(float a, float b, float c, float d)
    {
        int aInt = a > 0.5f ? 1 : 0;
        int bInt = b > 0.5f ? 1 : 0;
        int cInt = c > 0.5f ? 1 : 0;
        int dInt = d > 0.5f ? 1 : 0;
        
        return dInt * 8 + cInt * 4 + bInt * 2 + aInt * 1;
    }
    
    public static MarchingObject[][] generateChunk(float x, float y)
    {
        MarchingObject[][] marchingObjects = new MarchingObject[Core.chunkSize + 2][Core.chunkSize + 2];
        
        for(int i = 0; i < Core.chunkSize + 2; i++)
        {
            for(int j = 0; j < Core.chunkSize + 2; j++)
            {
                float xOffset = WorldGeneration.snapToChunkPosition(x) - Core.squareResolution;
                float yOffset = WorldGeneration.snapToChunkPosition(y) - Core.squareResolution;

                //multiply to add spacing 
                xOffset += j * Core.squareResolution;
                yOffset += i * Core.squareResolution;
                
                float noise = (Core.fastNoiseLite.GetNoise(xOffset * 0.25f, yOffset * 0.25f) + 1) / 2;
                marchingObjects[i][j] = new MarchingObject(new Vector2(xOffset, yOffset), noise);

            }
        }
        
        for (int i = 0; i < marchingObjects.length - 1; i++)
        {
            for (int j = 0; j < marchingObjects[0].length - 1; j++)
            {
                
                float xLocal = marchingObjects[i][j].getPositionX();
                float yLocal = marchingObjects[i][j].getPositionY();
                
                MarchingObject stateA = marchingObjects[i][j];
                MarchingObject stateB = marchingObjects[i][j + 1];
                MarchingObject stateC = marchingObjects[i + 1][j + 1];
                MarchingObject stateD = marchingObjects[i + 1][j];
                
                int state = getState(stateA.getValue(), 
                                     stateB.getValue(),
                                     stateC.getValue(), 
                                     stateD.getValue());
                

                /*
                SD      SC
                |---a---|
                d       b
                |---c---|
                SA      SB
                */

                Vector2 a = new Vector2(xLocal + Core.squareResolution / 2, yLocal + Core.squareResolution);
                Vector2 b = new Vector2(xLocal + Core.squareResolution, yLocal + Core.squareResolution / 2);
                Vector2 c = new Vector2(xLocal + Core.squareResolution / 2, yLocal);
                Vector2 d = new Vector2(xLocal, yLocal + Core.squareResolution / 2);

                switch (state)
                {
                    case 1:
                    case 14:
                        marchingObjects[i][j].lines.add(new Line(c, d, marchingObjects[i][j].getPosition()));
                        break;

                    case 2:
                    case 13:
                        marchingObjects[i][j].lines.add(new Line(b, c, marchingObjects[i][j].getPosition()));
                        break;

                    case 3:
                    case 12:
                        marchingObjects[i][j].lines.add(new Line(b, d, marchingObjects[i][j].getPosition()));
                        break;

                    case 4:
                    case 11:
                        marchingObjects[i][j].lines.add(new Line(a, b, marchingObjects[i][j].getPosition()));
                        break;

                    case 5:
                        marchingObjects[i][j].lines.add(new Line(a, d, marchingObjects[i][j].getPosition()));
                        marchingObjects[i][j].lines.add(new Line(c, b, marchingObjects[i][j].getPosition()));
                        break;

                    case 6:
                    case 9:
                        marchingObjects[i][j].lines.add(new Line(a, c, marchingObjects[i][j].getPosition()));
                        break;

                    case 7:
                    case 8:            
                        marchingObjects[i][j].lines.add(new Line(a, d, marchingObjects[i][j].getPosition()));
                        break;

                    case 10:
                        marchingObjects[i][j].lines.add(new Line(a, b, marchingObjects[i][j].getPosition()));
                        marchingObjects[i][j].lines.add(new Line(c, d, marchingObjects[i][j].getPosition()));
                        break;
                }
            }
            
            
            

        }
        
        return marchingObjects;
    }
    
    public static int snapToChunkPosition(float value)
    {
        return MathUtils.floor(value / (Core.squareResolution * Core.chunkSize)) * Core.squareResolution * Core.chunkSize;   
    }
    
    public static String convertPositionToString(int x, int y)
    {
        return x + "," + y;
    }
    
    public static Vector2 convertStringToPosition(String positionString)
    {
        String[] splitted = positionString.split(",");
        return new Vector2(Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1]));
    }
    
    private static List<Vector2> flood(Vector2 center)
    {
        List<Vector2> brances = new ArrayList<>(4);
        
        Vector2 branch = new Vector2(center.x - (Core.chunkSize * Core.squareResolution), center.y);
        if(!floodedVectors.contains(branch))
        {
            brances.add(branch);
        }
        
        
        branch = new Vector2(center.x + (Core.chunkSize * Core.squareResolution), center.y);
        if(!floodedVectors.contains(branch))
        {
            brances.add(branch);
        }
        
        
        branch = new Vector2(center.x, center.y + (Core.chunkSize * Core.squareResolution));
        if(!floodedVectors.contains(branch))
        {
            brances.add(branch);
        }
        
        
        branch = new Vector2(center.x, center.y - (Core.chunkSize * Core.squareResolution));
        if(!floodedVectors.contains(branch))
        {
            brances.add(branch);
        }
        
        return brances;
    }
    
    public static List<Vector2> floodFill(Vector2 startPosition)
    {
        
        List<Vector2> currentfloodedVectors = new ArrayList<>();
        
        floodedVectors.addAll(flood(startPosition));
        
        
        for(int i = 1; i < Core.renderDistance; i++)
        {
            for(Vector2 floodedVector: floodedVectors)
            {
                currentfloodedVectors.addAll(flood(floodedVector));
            }
            
            floodedVectors.addAll(currentfloodedVectors);
            currentfloodedVectors.clear();
        }
        
        return currentfloodedVectors;
    }
    
    
    public static ObjectMap<String, Chunk> generateChunks(float x, float y)
    {
        ObjectMap<String, Chunk> chunks = new ObjectMap<>();
        
        floodedVectors.addAll(floodFill(new Vector2(x, y)));
        
        for(Vector2 floadedVector : floodedVectors)
        {
            float xSnap = snapToChunkPosition(floadedVector.x);
            float ySnap = snapToChunkPosition(floadedVector.y);
            
            String key = xSnap + "" + ySnap;

            chunks.put(key, new Chunk(new Vector2(floadedVector.x, floadedVector.y),
                                      generateChunk(floadedVector.x, floadedVector.y)));
        }
        
        floodedVectors.clear();
        
        return chunks;
    }
    
    @Override
    public void run()
    {
        while(!Thread.currentThread().isInterrupted())
        {
            if(Core.generateChunk)
            {
                ObjectMap<String, Chunk> chunks = WorldGeneration.generateChunks(Core.player.camera.position.x, Core.player.camera.position.y);
                
                synchronized(Core.chunks)
                {
                    Core.chunks.putAll(chunks);
                }

                List<String> chunkToRemoveKeys = new ArrayList<>();
                
                for(Entry<String, Chunk> set : Core.chunks.entries())
                {
                    Vector2 chunkPosition = set.value.getPosition();
                    float renderLimit = Core.renderDistance * Core.chunkSize * Core.squareResolution;
                    
                    
                    if(Vector2.dst2(chunkPosition.x, chunkPosition.y, 
                                    Core.player.camera.position.x, Core.player.camera.position.y) >= 
                                    (renderLimit * 2) * (renderLimit * 2))
                    {
                        chunkToRemoveKeys.add(set.key);
                    }
                }
                
                synchronized(Core.chunks)
                {
                    for(String chunkKey : chunkToRemoveKeys)
                    {
                        Core.chunks.remove(chunkKey);
                    }
                }
                
                System.out.println("generate");
                System.out.println(Core.chunks.size);

                Core.generateChunk = false;
            }
        }
        
        System.exit(0);
    }
}
