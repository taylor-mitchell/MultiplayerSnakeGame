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
	private boolean gameOver;
	private Client client;
	private int currentScore;

	public Game()
	{
		this(new Client("localhost", 8300));
	}

	public Game(Client client)
	{
		this.client = client;
		this.client.setGame(this);
	}

	public void initGame()
	{
		camera = new Camera(new Vector3f(0, 0, 0));

		// Create an window and allow opengl operations
		display = new GameWindow("Multiplayer Snake Game", 1280, 640);

		// Create the shader and the renderer after we have an opengl context
		shader = new Shader();
		renderer = new Renderer(camera, shader);

		gameOver = false;
		currentScore = 0;
	}

	public void runGameLoop()
	{
		initGame();
		try
		{
			client.openConnection();
		} catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.exit(-1);
		}
		
		while (!display.windowShouldClose())
		{
			synchronized (this)
			{
				if (gameOver)
				{
					try
					{
						client.closeConnection();
					} catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					displayScore();
					break;
				}

				if (!client.isConnected())
				{
					displayConnectionError();
					displayScore();
					break;
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

			//System.out.println(String.format("Elpased Time: %f seconds", display.getDeltaTime()));
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
		Game game = new Game();
		game.runGameLoop();
	}
}
