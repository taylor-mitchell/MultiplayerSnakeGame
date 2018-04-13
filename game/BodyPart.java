package game;

import graphics.RawQuad;
import graphics.Texture;
import math.Vector3f;

public class BodyPart extends Entity{

	public BodyPart(Vector3f position) {
		super(position);
		
	}
	
	public BodyPart(Vector3f position, double radius) {
		super(position, radius);
		
	}

	public void animate() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public Entity clone() throws CloneNotSupportedException 
	{
		throw new CloneNotSupportedException();
	}
}
