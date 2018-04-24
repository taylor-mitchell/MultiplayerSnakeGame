package game;

import math.Vector3f;

public class Food extends Entity
{

	private int worth;

	
	public Food(Vector3f position, int worth, double radius)
	{
		super(position, radius);
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
	
	@Override
	public Food clone() throws CloneNotSupportedException 
	{
		Food clonedFood = new Food(position.clone(), worth, radius);
		return clonedFood;
	}
}
