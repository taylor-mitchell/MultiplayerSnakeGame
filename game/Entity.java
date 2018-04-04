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
		this.position = position;
		this.scale = new Vector3f(1, 1, 1);
		this.rawQuad = rawQuad;
		this.texture = texture;
	}
	
	public void translate(Vector3f amount)
	{
		position.add(amount);
	}
	
	public void addScale(Vector3f amount)
	{
		scale.add(amount);
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
