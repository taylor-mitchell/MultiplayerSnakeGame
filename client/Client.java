package client;

import java.awt.CardLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import game.Game;
import game.GameData;
import ocsf.client.AbstractClient;

public class Client extends AbstractClient
{
	private ClientGUI gui;
	private Game game;
	private JPanel container;
	private CardLayout cardLayout;
	
	public Client() {
		this("192.168.50.11", 8300);
	}

	public Client(String host, int port)
	{
		super(host, port);
	
		gui = new ClientGUI(this);
	}
	

	@Override
	protected void handleMessageFromServer(Object arg0)
	{
		if (arg0 instanceof String) {
			if (((String)arg0).equals("Login successful")) {
				game.setReady(true);
			}
		}else {
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

	public JPanel getContainer() {
		return container;
	}
	
	public void setGUI(ClientGUI gui) {
		this.gui = gui;
	}

	public void setContainer(JPanel container) {
		this.container = container;
	}

	public CardLayout getCardLayout() {
		return cardLayout;
	}

	public void setCardLayout(CardLayout cardLayout) {
		this.cardLayout = cardLayout;
	}
}
