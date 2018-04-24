package client;

import java.awt.CardLayout;
import java.io.IOException;

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

	public Client()
	{
		this("localhost", 8300);
	}

	public Client(String host, int port)
	{
		super(host, port);

		gui = new ClientGUI(this);
	}

	@Override
	protected void handleMessageFromServer(Object arg0)
	{
		if (arg0 instanceof String)
		{
			String msg = (String) arg0;
			System.out.println(msg);
			if (msg.equals("Login successful"))
			{
				gui.setReady();

			}
			else if (msg.equals("Login failed"))
			{
				gui.setNotReady();
			}
			// }else if (arg0 instanceof Integer){
			// gui.setTopScore((Integer)arg0);
		}
		else
		{
			synchronized (game)
			{
				GameData data = (GameData) arg0;
				System.out.println(data.getCameraLocation());
				game.setGameOver(data.isGameOver());
				game.setCurrentScore(data.getCurrentCore());
				game.setEntitiesToRender(data.getWorldEntities());
				game.setCameraLocation(data.getCameraLocation());
				gui.setScore(data.getCurrentCore());
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

	public JPanel getContainer()
	{
		return container;
	}

	public void setGUI(ClientGUI gui)
	{
		this.gui = gui;
	}

	public void setContainer(JPanel container)
	{
		this.container = container;
	}

	public CardLayout getCardLayout()
	{
		return cardLayout;
	}

	public void setCardLayout(CardLayout cardLayout)
	{
		this.cardLayout = cardLayout;
	}

	public void setGameReady(boolean ready)
	{
		try
		{
			this.sendToServer(ready);
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		synchronized (game)
		{
			game.setReady(ready);
			game.notify();
		}
	}

	public static void main(String[] args)
	{
		String host = "localhost";
		int port = 8300;

		if (args.length == 2)
		{
			host = args[0];
			port = Integer.parseInt(args[1]);
		}

		Client client = new Client(host, port);
		try
		{
			client.openConnection();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
