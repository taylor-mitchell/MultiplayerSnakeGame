package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import org.lwjgl.glfw.GLFW;

import InputHandler.Input;
import graphics.Camera;
import graphics.GameWindow;
import graphics.Quad;
import graphics.QuadLoader;
import graphics.RawQuad;
import graphics.Renderer;
import graphics.Shader;
import graphics.Texture;
import math.Vector3f;

public class Game 
{
	private GameWindow display;
	private Renderer renderer;
	private Camera camera;
	private Shader shader;
	private List<Entity> entitiesToRender;
	private Snake snake;
	private List<Food> foodList;
	
	private int foodCount;

	
	public Game()
	{
		initGame();
		runGameLoop();
	}
	
	public void initGame()
	{				
		Vector3f pos = new Vector3f(0, 0, 0);
		camera = new Camera(pos);
		
		// Create an window and allow opengl operations
		display = new GameWindow("Multiplayer Snake Game", 1280, 640);
		
		// Create the shader and the renderer after we have an opengl context
		shader = new Shader();
		renderer = new Renderer(camera, display, shader);
		
		RawQuad rawQuad = QuadLoader.loadToVAO(new Quad());
		Texture texture = new Texture("game/placeholder.png");
		Texture snakeTexture = new Texture("game/single_stroke.png");
		Texture foodTexture = new Texture("game/trippy.png");
		snake = new Snake(pos, rawQuad, snakeTexture, 100);
		snake.setSpeed(0.05f);
		snake.setBodyPartDistance(0.5f);
		snake.setDirection(Math.PI);
		
		foodCount = 1000;
		
		foodList = new ArrayList<Food>();
		
		for(int i = 0; i < foodCount; i++) {
			foodList.add(new Food(new Vector3f((float)(Math.random() * 100.f - 50.f), (float)(Math.random() * 100.0f - 50.f), 0), rawQuad, foodTexture, 2, 0.1)); 
		}

	}


	public void runGameLoop()
	{
		entitiesToRender = new ArrayList<>();
		entitiesToRender.addAll(snake.getBody());
		entitiesToRender.addAll(foodList);
		while(!display.windowShouldClose())
		{
			for (Food f : foodList) {
				if (f.collisionCheck(snake.getBody().get(0))){
					snake.addToBody(f.getWorth());
					f.setPosition(new Vector3f((float)(Math.random() * 100.f - 50.f), (float)(Math.random() * 100.0f - 50.f), 0));
				}
			}
			if (display.isLeftPressed()) {
				snake.setTurn(1);
			}else if (display.isRightPressed()) {
				snake.setTurn(-1);
			}else if (display.isUpPressed()){
				snake.speedUp();
			}else {
				snake.setTurn(0);
				snake.slowDown();
			}
			snake.keyUpdate();
			camera.setPosition(snake.getBody().get(0).getPosition());
			display.updateDisplay();
			renderer.prepare();
			renderer.renderEntities(entitiesToRender);
		}
		display.destroy();
	}
	
	public synchronized void setEntitiesToRender(List<Entity> entities)
	{
		
	}
	
	public synchronized List<Entity> getEntitiesToRender()
	{
		return entitiesToRender;
	}
	
	public static void main(String[] args)
	{
		new Game();
	}
}
