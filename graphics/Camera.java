package graphics;

import math.Vector3f;

public class Camera
{
	private Vector3f position;

	public Camera(Vector3f position)
	{
		super();
		this.position = position;
	}

	public Vector3f getPosition()
	{
		return position;
	}
	
	public void translate(Vector3f vec)
	{
		
	}
}
