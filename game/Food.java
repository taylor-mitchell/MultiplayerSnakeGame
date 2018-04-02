package game;

import graphics.RawQuad;
import graphics.Texture;
import math.Vector3f;

public class Food extends Entity
{
	private float maxSize;
	private float currentSize;
	private int worth;
	private boolean shouldGrow;
	
	public Food(Vector3f position, RawQuad rawQuad, Texture texture)
	{
		super(position, rawQuad, texture);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void animate()
	{
		// TODO Auto-generated method stub
	}
	
	public int getWorth()
	{
		return worth;
	}
}
