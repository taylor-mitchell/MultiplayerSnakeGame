package client;

import game.Game;
import game.GameData;
import ocsf.client.AbstractClient;

public class Client extends AbstractClient
{
	private ClientGUI gui;
	private Game game;

	public Client(String host, int port)
	{
		super(host, port);
	}

	@Override
	protected void handleMessageFromServer(Object arg0)
	{
		synchronized (game)
		{
			GameData data = (GameData) arg0;
			game.setGameOver(data.isGameOver());
			game.setCurrentScore(data.getCurrentCore());
			game.setEntitiesToRender(data.getWorldEntities());
			game.setCameraLocation(data.getCameraLocation());
			game.notify();
		}
	}

	@Override
	protected void connectionException(Exception exception)
	{
		super.connectionException(exception);
		onGameOver();
	}

	@Override
	protected void connectionClosed()
	{
		super.connectionClosed();
		onGameOver();
	}

	@Override
	protected void connectionEstablished()
	{
		super.connectionEstablished();
	}
	
	private void onGameOver()
	{
		synchronized (game)
		{
			game.setGameOver(true);
			game.notify();
		}
	}

	public void setGui(ClientGUI gui)
	{
		this.gui = gui;
	}

	public void setGame(Game game)
	{
		this.game = game;
	}
}
