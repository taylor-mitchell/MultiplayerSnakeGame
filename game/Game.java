package game;

import java.util.ArrayList;
import java.util.List;

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
		snake = new Snake(pos, rawQuad, texture);
	}
	
	public void runGameLoop()
	{
		entitiesToRender = new ArrayList<>(1);
		entitiesToRender.add(snake);
		while(!display.windowShouldClose())
		{
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
