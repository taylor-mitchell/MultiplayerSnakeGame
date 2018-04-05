package game;

import graphics.RawQuad;
import graphics.Texture;
import math.Vector3f;

public abstract class Entity 
{
	protected Vector3f position;
	private Vector3f scale;
	private RawQuad rawQuad;
	private Texture texture;
	private boolean playingOddAnimation;
	protected boolean inPlay;
	private double radius;
	
	public Entity(Vector3f position, RawQuad rawQuad, Texture texture)
	{
		this.position = position;
		this.scale = new Vector3f(1, 1, 1);
		this.rawQuad = rawQuad;
		this.texture = texture;
		this.radius = 0;
		this.inPlay = true;
	}
	
	public Entity(Vector3f position, RawQuad rawQuad, Texture texture, double radius) {
		this.position = position;
		this.scale = new Vector3f(1, 1, 1);
		this.rawQuad = rawQuad;
		this.texture = texture;
		this.radius = radius;
		this.inPlay = true;
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
	
	public boolean collisionCheck(Entity en) {
		if (inPlay) {
			if (Math.hypot(position.getX() - en.getPosition().getX(),position.getY() - en.getPosition().getY()) < radius + en.getRadius()) {
				return true;
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
}
