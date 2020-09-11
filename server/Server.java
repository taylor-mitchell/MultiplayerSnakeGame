package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Timer;

import client.CreateAccountData;
import client.LoginData;
import database.Database;
import game.Entity;
import game.Food;
import game.GameData;
import game.Snake;
import math.Vector3f;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class Server extends AbstractServer
{
	private Database database;
	private Map<Long, User> connectedUsers;
	Vector3f cameraLocation;
	private List<Snake> snakeList;
	private ActionListener timerListener;
	private List<Food> foodList;
	private int foodNum;
	private boolean databaseCheck;
	
	

	public Server(int port)
	{
		super(port);
		
		try {
			this.listen();
		} catch (IOException e) {
			error("Server couldn't start listening");
			error(e.getMessage());
		}
		init();
	}

	public Server()
	{
		super(0);
		
		try {
			this.listen();
		} catch (IOException e) {
			error("Server couldn't start listening");
			error(e.getMessage());
		}
		init();
	}
	
	private void init() {		
		databaseCheck = true;
		
		//This listener controls what the timer does 
		timerListener = new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				update();
				
			}
			
		};
		
		if (databaseCheck) {
			database = new Database();
		}
		
		connectedUsers = new HashMap<Long, User>();
		snakeList = new ArrayList<Snake>();
		
		//FoodNum is the total amount of food initialized in the game
		foodNum = 5000;
		foodList = new ArrayList<Food>();
		
		//Instantiates all of the food in random locations between -50 and 50
		//in the X and Y directions
		for (int i = 0; i < foodNum; i++) {
			foodList.add(new Food(new Vector3f(
					(float)(Math.random() * 1000.f - 500.f), 
					(float)(Math.random() * 1000.f - 500.f),
					0), 
					1, 0.5f));
		}
		
		//Starts the timer so that the game updates at 60 times a second
		Timer timer = new Timer(1000/60, timerListener);
		timer.start();
	}

	protected void handleMessageFromClient(Object arg0, ConnectionToClient arg1)
	{
		if(arg0 instanceof CreateAccountData) {
			
			//Handles all of the creating account functions
			createAccount((CreateAccountData)arg0, arg1);
		}
		if(arg0 instanceof LoginData) {
			
			//Handles all of the logging in functions
			logIn((LoginData)arg0, arg1);
		}else if (arg0 instanceof UserMessage) {
			
			//Handles any message from a user in-game
			handleUserMessage((UserMessage)arg0, arg1);
			
		}else if (arg0 instanceof Boolean) {
			
			if ((Boolean)arg0 == true) {
				play(arg1);
			}else {
				
			snakeList.remove(connectedUsers.get(arg1.getId()).getSnake());
			
			//Right now the server expects a 'false' if the user logs out
			connectedUsers.get(arg1.getId()).logOut();
			}
		}		
	}

	protected void listeningException(Throwable exception)
	{
		super.listeningException(exception);
		error("There was a listening exception");
		error(exception.getMessage());
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
	
	protected void clientDisconnected(ConnectionToClient c2c) {
		User user = connectedUsers.get(c2c.getId());
		
		
		if (user != null) {
			snakeList.remove(user.getSnake());
			connectedUsers.remove(c2c.getId());
		}
	}

	@Override
	protected void clientConnected(ConnectionToClient client)
	{
		
		super.clientConnected(client);
		
	}
	
	public void createAccount(CreateAccountData createData, ConnectionToClient c2c) {
		
		//Checks that the passwords match
		if (databaseCheck) {
			if (!createData.getPassword1().equals(createData.getPassword2())) {
				
				try {
					c2c.sendToClient("Account not created");
				} catch (IOException e) {
					error("Couldn't send 'passwords don't match' message to client");
					error(e.getMessage());
				}
				
			//Checks to see if the username is taken	
			}else if (database.isThere(createData.getUsername())) {
				
				try {
					c2c.sendToClient("Account not created");
				} catch (IOException e) {
					error("Couldn't send 'username taken' message to client");
					error(e.getMessage());
				}
				
			//Adds the new username to the database	
			}else if (!database.queryUsername().contains(createData.getUsername())){
				database.executeDML(createData.getUsername(), createData.getPassword1());
				try {
					c2c.sendToClient("Account created");
				} catch (IOException e) {
					error("Couldn't send 'account created' message to client");
					error(e.getMessage());
				}
				
			//Hopefully handles any other errors
			}else {				
				error("Something weird went wrong with creating an account");
				
				try {
					c2c.sendToClient("Something weird went wrong");
				} catch (IOException e) {
					error("Couldn't send weird message to client");
					error(e.getMessage());
				}
				
			}
		}else {
			try {
				c2c.sendToClient("Account created");
			} catch (IOException e) {
				error("Couldn't send 'account created' message to client");
				error(e.getMessage());
			}
		}
		
		return;		
	}
	
	public void logIn(LoginData loginData, ConnectionToClient c2c) {
		
		//Gets the password from the database if its there
		
		if (databaseCheck) {
			if (!database.isThere(loginData.getUsername(), loginData.getPassword())){
				
				try {
					c2c.sendToClient("Login failed");
				} catch (IOException e) {
					error("Couldn't send 'username doesn't exist' message to client");
					error(e.getMessage());
				}
				

				
			//If the password is right	
			}else if(database.isThere(loginData.getUsername(), loginData.getPassword())) {
				
				try {
					c2c.sendToClient("Login successful");
				} catch (IOException e) {
					error("Couldn't send login successful message to client");
					error(e.getMessage());
				}
				//Make a new user on the server
				User newUser = new User(c2c);
				newUser.logIn(loginData);
				
				//Add that user to the list of connected users and 
				//add its snake to the list of snakes.  I was getting concurrent
				//access errors without the synchronized thing.
				synchronized(this){
					connectedUsers.put(c2c.getId(), newUser);	
					//snakeList.add(newUser.getSnake());
					this.notify();
				}			
				
			//Hopefully handles all other errors
			}else {
				
				error("Something weird went wrong!");
				
				try {
					c2c.sendToClient("Something weird went wrong...");
				} catch (IOException e) {
					error("Couldn't send weird message to client");
					error(e.getMessage());
				}
			}
		}else {
			try {
				c2c.sendToClient("Login successful");
			} catch (IOException e) {
				error("Couldn't send login successful message to client");
				error(e.getMessage());
			}
			//Make a new user on the server
			User newUser = new User(c2c);
			newUser.logIn(loginData);
			
			//Add that user to the list of connected users and 
			//add its snake to the list of snakes.  I was getting concurrent
			//access errors without the synchronized thing.
			synchronized(this){
				connectedUsers.put(c2c.getId(), newUser);	
				//snakeList.add(newUser.getSnake());
				this.notify();
			}	
		}
		
		return;
	}
	
	//This class should handle all message from users who are in-game
	public void handleUserMessage(UserMessage msg, ConnectionToClient c2c) {
		
		//Updates the users snake
		Snake userSnake = connectedUsers.get(c2c.getId()).getSnake();
		userSnake.setTurn(msg.getTurn());
		userSnake.setZoom(msg.isZoom());
		
		//I'm not sure why I had this here but I am afraid to delete it. 
		//If it doesn't work, try uncommenting this back in.
		//snakeList.add(userSnake);
	}
	
	public void play(ConnectionToClient c2c) {
		//int score = database.getScore(connectedUsers.get(
		connectedUsers.get(c2c.getId()).play();
		snakeList.add(connectedUsers.get(c2c.getId()).getSnake());
	}
	
	//Handles the entire update cycle
	public void update() {
		
		long start = System.currentTimeMillis();
		
		//Needs to be synchronized so that there aren't any concurrent access errors
		synchronized(this) {
			
			checkCollisions();
			
			updateSnakes();
			
			sendData();
		}
		
		//System.out.println("Server elapsed time: " + ((System.currentTimeMillis() - start) / 1000.f) + "s");
	}

	//Checks all snake-snake and snake-food collisions
	//Could probably be more efficient
	private void checkCollisions()
	{
		//I keep a list of dead snakes so that I can 
		//'kill' them at the end of the collision check phase.
		//This is just so nothing unexpected happens
		ArrayList<Snake> deadSnakes = new ArrayList<Snake>();
		
		//For every snake, check every other snake for collisions
		//and then check for food collisions
		for(Snake snake1 : snakeList) {
			for(Snake snake2 : snakeList) {
				if (snake2.isInPlay()) {
					if (snake1 != snake2) {
						if (snake1.collisionCheck(snake2.getBody().get(0))){
							deadSnakes.add(snake2);
							snake1.addToScore(snake2.getLength());
						}
					}
				}
			}
			
			for(Food food : foodList) {
				if (food.collisionCheck(snake1.getBody().get(0))) {
					
					//Adds new body parts to the snake
					snake1.addToBody(food.getWorth());
					
					//Moves the food to a new location
					food.setPosition((float)(Math.random() * 100.f - 50.f), (float)(Math.random() * 100.f - 50.f));
				}
			}
		}
		
		//Kills all of the dead snakes
		for(Snake snake : deadSnakes) {
			snake.setInPlay(false);
		}
	}
	
	//After the collision check phase, then all movement can be handled
	//without worrying about collisions.
	private void updateSnakes()
	{
		//Calls the update function for all of the snakes
		for (Snake snake : snakeList) {
			snake.keyUpdate();
		}
	}
	
	public boolean isThere(String username,String password) {
		
		return database.isThere(username, password);
	}
	
	public Database getDatabase() {
		return database;
	}
	
	public void saveUser(String username, String password) {
		database.executeDML(username, password);
		
	}
	
	
	
	//Sends the relevant data to all of the users
	private void sendData() {
		
		//for all users
		for (Long userId : connectedUsers.keySet()) {
			
			//checks if the user is logged in
			if (connectedUsers.get(userId).isLoggedIn()) {
				
				User user = connectedUsers.get(userId);
				
				//This is the user's camera position
				Vector3f userPosition = user.getSnake().getBody().get(0).getPosition().clone();
				
				//Need to make a new list of entities because otherwise it won't serialize correctly
				List<Entity> clonedEntitiesToRender = new ArrayList<>();
				
				//This for loop will make sure that only the snakes that are nearby are sent to the user
				//If we change the client game view size, then we need to change the 8.0f
				for (Snake snake : snakeList) {
					if (snake.isInPlay()) {
						for (Entity bp : snake.getBody()) {
							if (Math.hypot(userPosition.getX() - bp.getPosition().getX(), userPosition.getY() - bp.getPosition().getY()) < 50.0f) {
								try {
									clonedEntitiesToRender.add(snake.clone());
								} catch (CloneNotSupportedException e) {
									error("There was a cloning problem");
									error(e.getMessage());
								}
								break;
							}
						}
					}
					
				}
				
				//This for loop will make sure that only the food that is nearby is sent to the user
				//If we change the client game view size, then we need to change the 8.0f
				for (Food food : foodList) {
					if (Math.hypot(userPosition.getX() - food.getPosition().getX(), userPosition.getY() - food.getPosition().getY()) < 50.0f) {
						try {
							clonedEntitiesToRender.add(food.clone());
						} catch (CloneNotSupportedException e) {
							error("There was a cloning problem");
							error(e.getMessage());
						}
					}
				}
		
				
				//Sends the game data to the user
				//Right now the score won't change

				try {
					user.sendGameData(new GameData(clonedEntitiesToRender, userPosition, user.getSnake().getScore(), !user.getSnake().isInPlay()));
				} catch (IOException e) {
					error("Couldn't send game data to client");
					error(e.getMessage());
				}
			}
		}
	}
	
	
	//Should handle all error messages
	private void error(String msg) {
		System.out.println(msg);
	}
	
	

	public static void main(String args[])
	{
		int port = 8300;		
	
		if (args.length == 1) {

			port = Integer.parseInt(args[0]);
		}	
		
		
		Server server = new Server(port);

		
	}
	
}
