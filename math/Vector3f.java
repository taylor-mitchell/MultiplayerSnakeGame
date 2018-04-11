package math;

import java.io.Serializable;

public class Vector3f implements Serializable
{
	private float x;
	private float y;
	private float z;
	
	public Vector3f(Vector3f vec)
	{
		this.x = vec.x;
		this.y = vec.y;
		this.z = vec.z;
	}
	
	public Vector3f(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public float getX()
	{
		return x;
	}

	public void setX(float x)
	{
		this.x = x;
	}

	public float getY()
	{
		return y;
	}

	public void setY(float y)
	{
		this.y = y;
	}

	public float getZ()
	{
		return z;
	}

	public void setZ(float z)
	{
		this.z = z;
	}
	
	public Vector3f add(Vector3f rhs)
	{
		x += rhs.x;
		y += rhs.y;
		z += rhs.z;
		return this;
	}
	
	public Vector3f sub(Vector3f rhs)
	{
		x -= rhs.x;
		y -= rhs.y;
		z -= rhs.z;
		return this;
	}
	
	public Vector3f mul(Vector3f rhs)
	{
		x *= rhs.x;
		y *= rhs.y;
		z *= rhs.z;
		return this;
	}
	
	public Vector3f flip()
	{
		return new Vector3f(-x, -y, -z);
	}

	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		return String.format("(%f, %f, %f)", x, y, z);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		Vector3f rhs = (Vector3f) obj;
		return x == rhs.x && y == rhs.y && z == rhs.z;
	}

	}
