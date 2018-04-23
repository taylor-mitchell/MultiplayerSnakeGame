package game;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;

import java.io.IOException;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import client.Client;
import client.GamePanel;
import graphics.Camera;
import graphics.GameWindow;
import graphics.QuadLoader;
import graphics.Renderer;
import graphics.Shader;
import math.Vector3f;
import server.UserMessage;

public class Game implements Runnable
{
	private GameWindow display;
	private Renderer renderer;
	private Camera camera;
	private Shader shader;
	private List<Entity> entitiesToRender;
	private boolean gameOver;
	private Client client;
	private int currentScore;
	private UserMessage message;
	private boolean ready;
	private GamePanel gamePanel;

	public Game(Client client, GamePanel gamePanel)
	{
		this.client = client;
		this.client.setGame(this);
		this.gamePanel = gamePanel;
		ready = false;
	}

	public void initGame()
	{
		message = new UserMessage(0, false);
		
		camera = new Camera(new Vector3f(0, 0, 0));

		// Create an window and allow opengl operations
		display = new GameWindow("Multiplayer Snake Game", 1280, 640);

		// Create the shader and the renderer after we have an opengl context
		shader = new Shader();
		renderer = new Renderer(camera, shader);

		gameOver = false;
		currentScore = 0;
	}

	@Override
	public void run()
	{
		initGame();

		while (!display.windowShouldClose())
		{
			synchronized (this)
			{
				if (ready) {
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
						break;
					}
					
					if (!client.isConnected())
					{
						displayConnectionError();
						break;
					}
	
					updateScore();
					
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
			}
		}

		//System.out.println(String.format("Elpased Time: %f seconds", display.getDeltaTime()));
		
		client.setGameReady(false);

		QuadLoader.cleanUp();
		display.destroy();
		shader.cleanUp();
	}

	private void checkInput()
	{
		UserMessage newMessage = new UserMessage(0, false);
		if (display.isKeyPressed(GLFW_KEY_LEFT))
		{
			newMessage.setTurn(1);
		}
		else if (display.isKeyPressed(GLFW_KEY_RIGHT))
		{
			newMessage.setTurn(-1);
		}else {
			newMessage.setTurn(0);
		}
		
		
		if (display.isKeyPressed(GLFW_KEY_UP))
		{
			newMessage.setZoom(true);
		}
		else
		{
			newMessage.setZoom(false);
		}
		
		if (!newMessage.equals(message)) {
			try
			{
				client.sendToServer(newMessage);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			
			message.setTurn(newMessage.getTurn());
			message.setZoom(newMessage.isZoom());
		}
	}

	private void updateScore()
	{
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			public void run() {
				gamePanel.setScore(currentScore);
			}
		});
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
	
	public void setReady(boolean ready) {
		this.ready = ready;
	}
}
