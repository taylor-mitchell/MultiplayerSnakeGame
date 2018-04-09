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
	private boolean eaten;
	
	public Food(Vector3f position, RawQuad rawQuad, Texture texture, int worth, double radius)
	{
		super(position, rawQuad, texture, radius);
		this.worth = worth;
		body.add(this);
	}

	public void animate()
	{
		// TODO Auto-generated method stub
	}
	
	public int getWorth()
	{
		return worth;
	}
}
