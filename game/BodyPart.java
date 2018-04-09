package game;

import graphics.RawQuad;
import graphics.Texture;
import math.Vector3f;

public class BodyPart extends Entity{

	public BodyPart(Vector3f position, RawQuad rawQuad, Texture texture) {
		super(position, rawQuad, texture);
		
	}
	public BodyPart(Vector3f position, RawQuad rawQuad, Texture texture, double radius) {
		super(position, rawQuad, texture, radius);
		
	}

	public void animate() {
		// TODO Auto-generated method stub
		
	}
}
