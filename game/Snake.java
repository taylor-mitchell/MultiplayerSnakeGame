package game;

import java.util.List;

import math.Vector3f;


public class Snake extends Entity
{
	private int score;
	private float speed;
	private int length;
	private double direction;
	private float bodyPartDistance;
	private int turn;
	private Vector3f translation;
	private boolean zoom;
	private Vector3f targetPosition;
	private float turnRadius;

	private int foodToAdd;
	
	public Snake()
	{
		super();
	}
	
	public Snake(Vector3f position)
	{
		super(position);
		this.setScore(0);
		this.setSpeed(0);
		this.setLength(0);
		this.setDirection(0);
		this.setBodyPartDistance(0);
		this.setTurn(0);
		this.setZoom(false);
		this.setTargetPosition(new Vector3f(0, 0, 0));		
		turnRadius = 0.05f;
	}
	
	public Snake(Vector3f position, int length)
	{
		super(position);
		this.setScore(0);
		this.setSpeed(0);
		this.setLength(length);
		this.setDirection(0);
		this.setBodyPartDistance(0);
		this.setTurn(0);
		this.setZoom(false);
		this.setTargetPosition(new Vector3f(0, 0, 0));		
		this.setRadius(0.5);
		turnRadius = 0.03f;
		
		body.add(new BodyPart(new Vector3f(position.getX(), position.getY(), position.getZ())));		
		for (int i = 1; i < length; i++) {
			body.add(new BodyPart(new Vector3f(i *  -1 * bodyPartDistance, position.getY(), position.getZ()), this.getRadius()));
		}
	}

	@Override
	public void animate()
	{
		// TODO Auto-generated method stub
	}
	
	public boolean collisionCheck(BodyPart head) {
		if(inPlay) {
			for (int i = 0; i < length; i++) {
				if(body.get(i).collisionCheck(head)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	private void eat() {
		if (foodToAdd > 0) {
			
			/*for(Entity en : body) {
				en.setRadius(this.getRadius());
			}*/
			body.add(new BodyPart(body.get(length - 1).getPosition(), this.getRadius()));			
			foodToAdd--;
			length++;
		}
	}
	
	public void keyUpdate() {
		double sp;
		if (zoom) {
			sp = speed * 2;
		}else {
			sp = speed;
		}
		if (inPlay) {
			eat();
			if (turn == 1) {
				direction = direction + turnRadius;
			}else if (turn == -1) {
				direction = direction - turnRadius;
			}
			
			body.get(0).translate(new Vector3f((float)(Math.cos(direction) * sp), (float)(Math.sin(direction) * sp), 0));
			//body.get(0).translate((float)(Math.cos(direction) * sp), (float)(Math.sin(direction) * sp));
			this.position = body.get(0).getPosition();
			double newDirection;
			for (int i = 1; i < length; i++) {
				Vector3f pos1 = body.get(i-1).getPosition();
				Vector3f pos2 = body.get(i).getPosition();
				double deltaX = pos1.getX() - pos2.getX();
				double deltaY = pos1.getY() - pos2.getY();
	
				if (deltaX == 0) {		
					if (deltaY < 0) {
						newDirection = 3 * Math.PI / 2;
					}else {
						newDirection = Math.PI / 2;
					}	
					body.get(i).setPosition(new Vector3f((float)(pos1.getX() + Math.cos(newDirection) * bodyPartDistance), (float)(pos1.getY() + Math.sin(newDirection) * bodyPartDistance), 0));
					//body.get(i).setPosition((float)(pos1.getX() + Math.cos(newDirection) * bodyPartDistance), (float)(pos1.getY() + Math.sin(newDirection) * bodyPartDistance));
				}else if (deltaX < 0){
					newDirection = Math.atan(deltaY/ deltaX);
					body.get(i).setPosition(new Vector3f((float)(pos1.getX() + Math.cos(newDirection) * bodyPartDistance), (float)(pos1.getY() + Math.sin(newDirection) * bodyPartDistance), 0));
					//body.get(i).setPosition((float)(pos1.getX() + Math.cos(newDirection) * bodyPartDistance), (float)(pos1.getY() + Math.sin(newDirection) * bodyPartDistance));
				}else {
					newDirection = -Math.atan(deltaY/ deltaX);
					body.get(i).setPosition(new Vector3f((float)(pos1.getX() - Math.cos(newDirection) * bodyPartDistance), (float)(pos1.getY() + Math.sin(newDirection) * bodyPartDistance), 0));
					//body.get(i).setPosition((float)(pos1.getX() - Math.cos(newDirection) * bodyPartDistance), (float)(pos1.getY() + Math.sin(newDirection) * bodyPartDistance));
				}
			}
		}
		
	}
	
	/*public void randomUpdate() {
		if (inPlay) {
			eat();
			if (direction > Math.PI * 2) {
				direction = direction - Math.PI * 2;
			}
			if (direction < 0) {
				direction = direction + Math.PI * 2;
			}
			double targetDirection;
			double deltaTargetX = targetPosition.getX()- body.get(0).getPosition().getX();	
			double deltaTargetY = targetPosition.getY() - body.get(0).getPosition().getX();	
			
			if (Math.hypot(deltaTargetX, deltaTargetY) < body.get(0).getRadius()) {
				targetPosition = new Vector3f(Math.random() * this.getRawQuad().)
				targetX = Math.random() * 900;
				targetY = Math.random() * 600;
			}
			double deltaTargetX = targetX - body.get(0).getX();			
			
			
			if (deltaTargetX == 0) {
				if (targetY < body.get(0).getY()) {
					targetDirection = -3 * Math.PI / 2;
				}else {
					targetDirection = -Math.PI / 2;
				}
				body.get(0).setX(body.get(0).getX() + Math.cos(direction) * speed);
				body.get(0).setY(body.get(0).getY() - Math.sin(direction) * speed);
			}else if (deltaTargetX < 0){
				if (targetY - body.get(0).getY() < 0) {
					targetDirection = Math.PI  - Math.atan((targetY - body.get(0).getY())/ (targetX - body.get(0).getX()));
				}else {
					targetDirection = Math.PI - Math.atan((targetY - body.get(0).getY())/ (targetX - body.get(0).getX()));
				}
				
				body.get(0).setX(body.get(0).getX() + Math.cos(direction) * speed);
				body.get(0).setY(body.get(0).getY() - Math.sin(direction) * speed);
			}else {
				if (targetY - body.get(0).getY() < 0) {
					targetDirection =  -Math.atan((targetY - body.get(0).getY())/ (targetX - body.get(0).getX()));
				}else {
					targetDirection = Math.PI* 2 - Math.atan((targetY - body.get(0).getY())/ (targetX - body.get(0).getX()));
				}
				body.get(0).setX(body.get(0).getX() + Math.cos(direction) * speed);
				
				body.get(0).setY(body.get(0).getY() - Math.sin(direction) * speed);
			}
	
			if (Math.abs(direction - targetDirection) > 0.05) {
				if (targetDirection < direction) {
					if (direction - targetDirection > Math.PI) {
						direction = direction + 0.05;
					}else {
						direction = direction - 0.05;
					}					
				}else {
					if (targetDirection - direction > Math.PI) {
						direction = direction - 0.05;
					}else {
						direction = direction + 0.05;
					}
				}
			}
		
			
			
			body.get(0).setX(body.get(0).getX() + Math.cos(direction) * speed);
			body.get(0).setY(body.get(0).getY() - Math.sin(direction) * speed);
			double newDirection;
			for (int i = 1; i < length; i++) {
				double deltaX = body.get(i-1).getX() - body.get(i).getX();
				if (Math.abs(deltaX) < 0.00001) {
		
					if (body.get(i-1).getY() < body.get(i).getY()) {
						newDirection = 3 * Math.PI / 2;
					}else {
						newDirection = Math.PI / 2;
					}			
					body.get(i).setX(body.get(i-1).getX() + Math.cos(newDirection) * bodyPartDistance);
					body.get(i).setY(body.get(i-1).getY() - Math.sin(newDirection) * bodyPartDistance);
				}else if (deltaX < 0){
					newDirection = -Math.atan((body.get(i-1).getY() - body.get(i).getY())/ (body.get(i-1).getX() - body.get(i).getX()));
					body.get(i).setX(body.get(i-1).getX() + Math.cos(newDirection) * bodyPartDistance);
					body.get(i).setY(body.get(i-1).getY() - Math.sin(newDirection) * bodyPartDistance);
				}else {
					newDirection = -Math.atan((body.get(i-1).getY() - body.get(i).getY())/ (body.get(i-1).getX() - body.get(i).getX()));
					body.get(i).setX(body.get(i-1).getX() - Math.cos(newDirection) * bodyPartDistance);
					body.get(i).setY(body.get(i-1).getY() + Math.sin(newDirection) * bodyPartDistance);
				}
			}
		}
	}*/
	
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



	public int getScore() {
		return score;
	}



	public void setScore(int score) {
		this.score = score;
	}



	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public List<Entity> getBody(){
		return body;
	}


	public int getLength() {
		return length;
	}



	public void setLength(int length) {
		this.length = length;
	}



	public double getDirection() {
		return direction;
	}



	public void setDirection(double direction) {
		this.direction = direction;
	}



	public float getBodyPartDistance() {
		return bodyPartDistance;
	}

	public void addToScore(int s) {
		score += s;
	}

	public void setBodyPartDistance(float bodyPartDistance) {
		this.bodyPartDistance = bodyPartDistance;
	}



	public int getTurn() {
		return turn;
	}



	public void setTurn(int turn) {
		this.turn = turn;
	}



	public boolean isZoom() {
		return zoom;
	}



	public void setZoom(boolean zoom) {
		this.zoom = zoom;
	}



	public Vector3f getTargetPosition() {
		return targetPosition;
	}

	public void setTargetPosition(Vector3f targetPosition) {
		this.targetPosition = targetPosition;
	}


	public void addToBody(int f) {
		foodToAdd = foodToAdd + f;
		addToScore(f);
	}
	
	public void speedUp() {
		zoom = true;
	}
	
	public void slowDown() {
		zoom = false;
	}
	
	public void setColor(Vector3f color)
	{
		this.color = color;
	}
	
	@Override
	public Snake clone() throws CloneNotSupportedException 
	{
		Snake clonedSnake = new Snake(position.clone(), length);
		clonedSnake.setScore(score);
		clonedSnake.setSpeed(speed);
		clonedSnake.setLength(length);
		clonedSnake.setDirection(direction);
		clonedSnake.setBodyPartDistance(bodyPartDistance);
		clonedSnake.setTurn(turn);
		clonedSnake.setZoom(zoom);
		clonedSnake.setTargetPosition(targetPosition.clone());		
		clonedSnake.setRadius(0.3);
		clonedSnake.setColor(color.clone());
		clonedSnake.body.add(new BodyPart(position.clone()));
		for (int i = 1; i < length; i++) {
			clonedSnake.body.add(new BodyPart(body.get(i).getPosition().clone(), radius));
		}
		
		clonedSnake.foodToAdd = foodToAdd;
		
		return clonedSnake;
	}
}
