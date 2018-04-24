package game;

import java.io.Serializable;
import java.util.List;

import math.Vector3f;

public class GameData implements Serializable
{
	private final List<Entity> worldEntities;
	private final Vector3f cameraLocation;
	private final int currrentScore;
	private final boolean isGameOver;

	public GameData(List<Entity> worldEntities, Vector3f cameraLocation, int currrentScore, boolean isGameOver)
	{
		this.worldEntities = worldEntities;
		this.cameraLocation = cameraLocation;
		this.currrentScore = currrentScore;
		this.isGameOver = isGameOver;
	}

	public List<Entity> getWorldEntities()
	{
		return worldEntities;
	}

	public Vector3f getCameraLocation()
	{
		return cameraLocation;
	}

	public int getCurrentCore()
	{
		return currrentScore;
	}

	public boolean isGameOver()
	{
		return isGameOver;
	}
}
