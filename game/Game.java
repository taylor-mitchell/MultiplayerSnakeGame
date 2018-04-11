package game;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UNKNOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import client.Client;
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
	private boolean gameOver;
	private int foodCount;
	private Client client;
	private int currentScore;

	public Game()
	{
		this(new Client("localhost", 8300));
	}

	public Game(Client client)
	{
		this.client = client;
	}

	public void initGame()
	{
		Vector3f pos = new Vector3f(0, 0, 0);
		camera = new Camera(pos);

		// Create an window and allow opengl operations
		display = new GameWindow("Multiplayer Snake Game", 1280, 640);

		// Create the shader and the renderer after we have an opengl context
		shader = new Shader();
		renderer = new Renderer(camera, shader);

		RawQuad rawQuad = QuadLoader.loadToVAO(new Quad());
		Texture snakeTexture = new Texture("game/single_stroke.png");
		Texture foodTexture = new Texture("game/trippy.png");
		snake = new Snake(pos, rawQuad, snakeTexture, 1);
		snake.setSpeed(0.05f);
		snake.setBodyPartDistance(0.5f);
		snake.setDirection(Math.PI);
		snake.setColor(new Vector3f(.23f, .18f, .43f));
		// snake.setBodyScale(new Vector3f(.2f, .2f, 1));

		foodCount = 1000;

		foodList = new ArrayList<Food>();

		for (int i = 0; i < foodCount; i++)
		{
			foodList.add(new Food(
					new Vector3f((float) (Math.random() * 100.f - 50.f), (float) (Math.random() * 100.0f - 50.f), 0),
					rawQuad, foodTexture, 2, 0.1));
		}

		entitiesToRender = new ArrayList<>();
		entitiesToRender.add(snake);
		entitiesToRender.addAll(foodList);

		gameOver = false;
		currentScore = 0;
	}

	public void runGameLoop()
	{
		initGame();

		while (!display.windowShouldClose())
		{
			synchronized (this)
			{
				if (gameOver)
				{
					displayScore();
					break;
				}

				if (!client.isConnected())
				{
					displayConnectionError();
					displayScore();
					break;
				}

				for (Food f : foodList)
				{
					if (f.collisionCheck(snake.getBody().get(0)))
					{
						snake.addToBody(f.getWorth());
						f.setPosition(new Vector3f((float) (Math.random() * 100.f - 50.f),
								(float) (Math.random() * 100.0f - 50.f), 0));
					}
				}

				checkInput();

				renderer.prepare();

				renderer.renderEntities(entitiesToRender);
				display.updateDisplay();
				try
				{
					wait();
				} catch (InterruptedException e)
				{
					gameOver = true;
					e.printStackTrace();
				}
			}

			System.out.println(String.format("Elpased Time: %f seconds", display.getDeltaTime()));
		}

		QuadLoader.cleanUp();
		display.destroy();
		shader.cleanUp();
	}

	private void checkInput()
	{
		int keyPressed;
		if (display.isKeyPressed(GLFW_KEY_LEFT))
		{
			// snake.setTurn(1);
			keyPressed = GLFW_KEY_LEFT;
		}
		else if (display.isKeyPressed(GLFW_KEY_RIGHT))
		{
			// snake.setTurn(-1);
			keyPressed = GLFW_KEY_LEFT;
		}
		else if (display.isKeyPressed(GLFW_KEY_UP))
		{
			// snake.speedUp();
			keyPressed = GLFW_KEY_LEFT;
		}
		else
		{
			// snake.setTurn(0);
			// snake.slowDown();
			keyPressed = GLFW_KEY_UNKNOWN;
		}

		try
		{
			client.sendToServer(keyPressed);
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		// snake.keyUpdate();
		// camera.setPosition(snake.getBody().get(0).getPosition());
	}

	private void displayScore()
	{
		JOptionPane.showMessageDialog(null, String.format("You scored %d", 0), "Game Over",
				JOptionPane.INFORMATION_MESSAGE);
	}

	private void displayConnectionError()
	{
		JOptionPane.showMessageDialog(null, "Lost connection to the server.", "Error", JOptionPane.ERROR_MESSAGE);
	}

	public void setGameOver(boolean gameOver)
	{
		this.gameOver = gameOver;
	}

	public void setCurrentScore(int currentCore)
	{
		this.currentScore = currentCore;
	}

	public void setEntitiesToRender(List<Entity> entities)
	{
		this.entitiesToRender = entities;
	}

	public void setCameraLocation(Vector3f cameraLocation)
	{
		camera.setPosition(cameraLocation);
	}

	public static void main(String[] args)
	{
		// Game game = new Game();
		// game.runGameLoop();
	}
}
