package server;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Timer;

import client.LoginData;
import game.Entity;
import game.Food;
import game.Game;
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
	private Map<Long, User> connectedUsers;
	Vector3f cameraLocation;
	private List<Snake> snakeList;
	private ActionListener timerListener;
	private List<Food> foodList;
	private int foodNum;
	

	public Server(int port)
	{
		super(port);
		
		timerListener = new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				update();
				
			}
			
		};
		
		try {
			this.listen();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		init();
	}

	public Server(ServerGUI serverGUI)
	{
		super(0);
		timerListener = new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				update();
				
			}
			
		};
		try {
			this.listen();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		init();
	}
	
	private void init() {
		connectedUsers = new HashMap<Long, User>();
		snakeList = new ArrayList<Snake>();
		
		foodNum = 50;
		foodList = new ArrayList<Food>();
		
		for (int i = 0; i < foodNum; i++) {
			foodList.add(new Food(new Vector3f((float)(Math.random() * 100.f - 50.f), (float)(Math.random() * 100.f - 50.f), 0), 10, 0.3f));
		}
		
		Timer timer = new Timer(1000/60, timerListener);
		timer.start();
	}

	@Override
	protected void handleMessageFromClient(Object arg0, ConnectionToClient arg1)
	{
		if(arg0 instanceof LoginData) {
			User newUser = new User(arg1);
			newUser.logIn((LoginData)arg0);
			synchronized(this){
				connectedUsers.put(arg1.getId(), newUser);	
				snakeList.add(newUser.getSnake());
				this.notify();
			}
		}else if (arg0 instanceof UserMessage) {
			UserMessage msg = (UserMessage)arg0;
			Snake userSnake = connectedUsers.get(arg1.getId()).getSnake();
			userSnake.setTurn(msg.getTurn());
			userSnake.setZoom(msg.isZoom());
			snakeList.add(userSnake);
		}else if (arg0 instanceof Boolean) {
			connectedUsers.get(arg1.getId()).logOut();
		}
		
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
		
		super.clientConnected(client);
		
	}
	
	public void update() {
		long start = System.currentTimeMillis();
		synchronized(this) {
			
			checkCollisions();
			
			updateSnakes();
			
			sendData();
		}
		System.out.println("Server elapsed time: " + ((System.currentTimeMillis() - start) / 1000.f) + "s");
	}



	private void checkCollisions()
	{
		ArrayList<Snake> deadSnakes = new ArrayList<Snake>();
		for(Snake snake1 : snakeList) {
			for(Snake snake2 : snakeList) {
				if (snake1 != snake2) {
					if (snake1.collisionCheck(snake2.getBody().get(0))){
						deadSnakes.add(snake2);
					}
				}
			}
			
			for(Food food : foodList) {
				if (food.collisionCheck(snake1.getBody().get(0))) {
					snake1.addToBody(food.getWorth());
					food.setPosition(new Vector3f((float)(Math.random() * 100.f - 50.f), (float)(Math.random() * 100.f - 50.f), 0));
				}
			}
		}
		
		for(Snake snake : deadSnakes) {
			snake.setInPlay(false);
		}
	}
	
	private void updateSnakes()
	{
		for (Snake snake : snakeList) {
			snake.keyUpdate();
		}
	}
	
	private void sendData() {
		for (Long userId : connectedUsers.keySet()) {
			if (connectedUsers.get(userId).isLoggedIn()) {
				User user = connectedUsers.get(userId);
				Vector3f userPosition = user.getSnake().getBody().get(0).getPosition().clone();
				
				List<Entity> clonedEntitiesToRender = new ArrayList<>();
				
				/*for (Snake snake : snakeList) {
					for (Entity bp : snake.getBody()) {
						if (Math.hypot(userPosition.getX() - bp.getPosition().getX(), userPosition.getY() - bp.getPosition().getY()) < 8.0f) {
							try {
								clonedEntitiesToRender.add(bp.clone());
							} catch (CloneNotSupportedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					
				}*/
				
				for (Snake snake : snakeList) {
					try {
						clonedEntitiesToRender.add(snake.clone());

					} catch (CloneNotSupportedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				for (Food food : foodList) {
					try {
						clonedEntitiesToRender.add(food.clone());
					} catch (CloneNotSupportedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				user.sendGameData(new GameData(clonedEntitiesToRender, userPosition, 0, !user.getSnake().isInPlay()));
			}
		}
	}

	public static void main(String args[])
	{
		Server server = new Server(8300);

		/*
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
		*/
	}
	
}
