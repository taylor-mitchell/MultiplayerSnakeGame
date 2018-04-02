package game;

import java.util.List;

import graphics.RawQuad;
import graphics.Texture;
import math.Vector3f;

public class Snake extends Entity
{
	private List<Entity> bodyParts;
	private int score;
	private float speed;
	
	public Snake(Vector3f position, RawQuad rawQuad, Texture texture)
	{
		super(position, rawQuad, texture);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void animate()
	{
		// TODO Auto-generated method stub
	}
	
	public void addPoint(int amount)
	{
		
	}
	
	public void checkInput()
	{
		
	}
	
	public float getSpeed()
	{
		return 0;
	}
}
