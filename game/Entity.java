package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import math.Vector3f;

public abstract class Entity implements Serializable
{
	protected Vector3f color;
	protected List<Entity> body;
	protected Vector3f position;
	protected Vector3f scale;
	protected boolean playingOddAnimation;
	protected boolean inPlay;
	protected double radius;
	
	public Entity()
	{
		this(new Vector3f(0, 0, 0), 0);
	}
	
	public Entity(Vector3f position)
	{
		this(position, 0);
	}
	
	public Entity(Vector3f position, double radius) {
		this.body = new ArrayList<Entity>();
		this.position = position;
		this.scale = new Vector3f(1, 1, 1);
		this.radius = radius;
		this.inPlay = true;
	}
	
	public void translate(Vector3f amount)
	{
		position.add(amount);
	}
	
	public void translate(float x, float y) {
		this.position.add(x, y, 0);
	}
	
	public void addScale(Vector3f amount)
	{
		scale.add(amount);
	}

	public void setBodyScale(Vector3f scale)
	{
		this.scale = scale;
		
		for (Entity entity : body)
			entity.setScale(scale);
	}
	
	public void setScale(Vector3f scale)
	{
		this.scale = scale;
	}
	
	public Vector3f getPosition()
	{
		return position;
	}


	public Vector3f getScale()
	{
		return scale;
	}

	public boolean isPlayingOddAnimation()
	{
		return playingOddAnimation;
	}
	
	public boolean collisionCheck(Entity en) {
		if (inPlay) {
			for(Entity bp : body) {
				if (Math.hypot(bp.getPosition().getX() - en.getPosition().getX(),bp.getPosition().getY() - en.getPosition().getY()) < radius + en.getRadius()) {
					return true;
				}
			}
		}		
		return false;
	}
	
	public abstract void animate();

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}
	
	public boolean isInPlay() {
		return inPlay;
	}
	
	public void setInPlay(boolean play) {
		inPlay = play;
	}

	public void setPosition(Vector3f vector3f) {
		this.position = vector3f;
		
	}
	
	public void setPosition(float x, float y) {
		this.position.setX(x);
		this.position.setY(y);
	}

	public List<Entity> getBody()
	{
		return body;
	}
	
	/** 
	 * 
	 * @return null if the color was not manually set.
	 */
	public Vector3f getColor()
	{
		return color;
	}
	
	public abstract Entity clone()  throws CloneNotSupportedException ;
}
