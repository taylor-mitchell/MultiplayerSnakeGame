package server;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import game.Entity;
import game.Food;
import game.GameData;
import game.Snake;
import graphics.Quad;
import graphics.QuadLoader;
import graphics.RawQuad;
import graphics.Texture;
import math.Vector3f;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class Server extends AbstractServer
{
	private ServerGUI serverGUI;
	private Map<Integer, User> connectedUsers;
	Vector3f cameraLocation;

	public Server(int port)
	{
		super(port);
	}

	public Server(ServerGUI serverGUI)
	{
		super(0);
	}

	@Override
	protected void handleMessageFromClient(Object arg0, ConnectionToClient arg1)
	{
		// TODO Auto-generated method stub
	}

	@Override
	protected void listeningException(Throwable exception)
	{
		// TODO Auto-generated method stub
		super.listeningException(exception);
	}

	@Override
	protected void serverStarted()
	{
		// TODO Auto-generated method stub
		super.serverStarted();
	}

	@Override
	protected void serverStopped()
	{
		// TODO Auto-generated method stub
		super.serverStopped();
	}

	@Override
	protected void serverClosed()
	{
		// TODO Auto-generated method stub
		super.serverClosed();
	}

	@Override
	protected void clientConnected(ConnectionToClient client)
	{
		// TODO Auto-generated method stub
		super.clientConnected(client);
	}

	private void updateWorldEntities()
	{

	}

	private boolean checkCollision(Entity lhs, Entity rhs)
	{
		return false;
	}

	public static void main(String args[])
	{
		Server server = new Server(8300);
		try
		{
			server.listen();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		Vector3f pos = new Vector3f(0, 0, 0);
		Snake snake = new Snake(pos, 1);
		snake.setSpeed(0.05f);
		snake.setBodyPartDistance(0.5f);
		snake.setDirection(Math.PI);
		snake.setColor(new Vector3f(.23f, .18f, .43f));

		List<Food> foodList = new ArrayList<>();
		List<Entity> entitiesToRender = new ArrayList<>();
		int foodCount = 300;

		foodList = new ArrayList<Food>();

		for (int i = 0; i < foodCount; i++)
		{
			foodList.add(new Food(
					new Vector3f((float) (Math.random() * 100.f - 50.f), (float) (Math.random() * 100.0f - 50.f), 0), 2,
					0.1));
		}

		entitiesToRender.add(snake);
		entitiesToRender.addAll(foodList);
		Vector3f cameraLocation;

		while (true)
		{
			long start = System.currentTimeMillis();
			if (server.getNumberOfClients() > 0)
			{
				for (Food f : foodList)
				{
					if (f.collisionCheck(snake.getBody().get(0)))
					{
						snake.addToBody(f.getWorth());
						f.setPosition(new Vector3f((float) (Math.random() * 100.f - 50.f),
								(float) (Math.random() * 100.0f - 50.f), 0));
					}
				}

				snake.keyUpdate();

				cameraLocation = snake.getBody().get(0).getPosition().clone();
				// GameData data = new GameData(entitiesToRender.subList(0,
				// entitiesToRender.size()), new Vector3f(0, 0, 0), 0, true);

				List<Entity> clonedEntitiesToRender = new ArrayList<>(entitiesToRender.size());
				for (Entity entity : entitiesToRender)
				{
					try
					{
						//if (Math.abs(Point.distance(cameraLocation.getX(), cameraLocation.getX(), entity.getBody().get(0).getPosition().getX(), entity.getBody().get(0).getPosition().getY())) < 40.f)
							clonedEntitiesToRender.add(entity.clone());
					} catch (CloneNotSupportedException e)
					{
						e.printStackTrace();
					}
				}
				GameData data = new GameData(clonedEntitiesToRender, cameraLocation, 0, false);
				server.sendToAllClients(data);
				System.out.println("Server elpased time: " + ((System.currentTimeMillis() - start) / 1000.f) + "s");
			}
		}
	}
}
