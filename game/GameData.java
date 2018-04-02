package game;

import math.Vector3f;

public class GameData 
{
	private Entity worldEntities;
	private Vector3f cameraLocation;
	
	public GameData(Entity worldEntities, Vector3f cameraLocation)
	{
		super();
		this.worldEntities = worldEntities;
		this.cameraLocation = cameraLocation;
	}

	public Entity getWorldEntities()
	{
		return worldEntities;
	}

	public Vector3f getCameraLocation()
	{
		return cameraLocation;
	}
}
