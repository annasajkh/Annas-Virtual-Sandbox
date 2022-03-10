package com.github.annasajkh.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.github.annasajkh.Core;

public class InputManager implements InputProcessor
{
    
    public static Vector3 mousePos = new Vector3();
	
	public static void getInput()
	{
	    mousePos.x = Gdx.input.getX();
	    mousePos.y = Gdx.input.getY();
		mousePos = Core.player.camera.unproject(mousePos);
	}

	@Override
	public boolean keyDown(int keycode)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY)
	{
		if(amountY < 0)
		{
			Core.player.camera.zoom -= Core.zoomSpeed * Core.player.camera.zoom * Core.zoomFactor;
		}
		else
		{
		    Core.player.camera.zoom += Core.zoomSpeed * Core.player.camera.zoom * Core.zoomFactor;
		}
		
		Core.player.camera.zoom = MathUtils.clamp(Core.player.camera.zoom, 0.0001f, 20f);
		
		return true;
	}

}
