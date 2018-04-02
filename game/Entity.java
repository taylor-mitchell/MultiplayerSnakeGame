package game;

import graphics.RawQuad;
import graphics.Texture;
import math.Vector3f;

public abstract class Entity 
{
	private Vector3f position;
	private Vector3f scale;
	private RawQuad rawQuad;
	private Texture texture;
	private boolean playingOddAnimation;
	
	public Entity(Vector3f position, RawQuad rawQuad, Texture texture)
	{
		
	}
	
	public void translate(Vector3f vec)
	{
		
	}
	
	public void addScale(float amount)
	{
		
	}

	public Vector3f getPosition()
	{
		return position;
	}

	public Vector3f getScale()
	{
		return scale;
	}

	public RawQuad getRawQuad()
	{
		return rawQuad;
	}

	public Texture getTexture()
	{
		return texture;
	}

	public boolean isPlayingOddAnimation()
	{
		return playingOddAnimation;
	}
	
	public abstract void animate();
}
