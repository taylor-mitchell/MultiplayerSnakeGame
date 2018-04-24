package server;

import java.io.IOException;

import client.LoginData;
import game.GameData;
import game.Snake;
import math.Vector3f;
import ocsf.server.ConnectionToClient;

public class User
{
	private LoginData loginInfo;
	private boolean loginStatus;
	private boolean ready;
	private Snake snake;
	private ConnectionToClient c2c;

	public User(ConnectionToClient c2c)
	{
		this.c2c = c2c;
		loginStatus = false;
		ready = false;
	}

	public void logIn(LoginData loginInfo)
	{
		this.loginInfo = loginInfo;
		// snake = new Snake(new Vector3f((float)(Math.random() * 1000.f -
		// 500.f), (float)(Math.random() * 1000.f - 500.f), 0), 10);
		// snake.setSpeed(0.05f);
		// snake.setBodyPartDistance(0.5f);
		// snake.setDirection(Math.PI);
		// snake.setColor(new Vector3f((float)Math.random(),
		// (float)Math.random(), (float)Math.random()));
		// snake.setInPlay(true);
		// loginStatus = true;
	}

	public void play()
	{
		snake = new Snake(
				new Vector3f((float) (Math.random() * 1000.f - 500.f), (float) (Math.random() * 1000.f - 500.f), 0),
				10);
		snake.setSpeed(0.07f);
		snake.setBodyPartDistance(0.5f);
		snake.setDirection(Math.PI);
		snake.setColor(new Vector3f((float) Math.random(), (float) Math.random(), (float) Math.random()));
		snake.setInPlay(true);
		loginStatus = true;
	}

	public boolean isLoggedIn()
	{
		return loginStatus;
	}

	public void logOut()
	{
		loginStatus = false;
	}

	public LoginData getLoginInfo()
	{
		return loginInfo;
	}

	public Snake getSnake()
	{
		return snake;
	}

	public void setReady(boolean ready)
	{
		this.ready = ready;
	}

	public boolean getReady()
	{
		return ready;
	}

	public void sendGameData(GameData gd) throws IOException
	{
		c2c.sendToClient(gd);
	}
}
