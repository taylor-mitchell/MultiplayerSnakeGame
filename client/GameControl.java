package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;

import game.Game;

public class GameControl implements ActionListener{
	
	private JPanel container;
	private Client client;
	private Game game;
	
	public GameControl(Client client) {
		this.client = client;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		
		if (command == "Play") {
			game = new Game(client, (GamePanel)container);
			Thread gameThread = new Thread(game);
			client.setGameReady(true);
			gameThread.start();
		}else if (command == "Stop") {
			client.setGameReady(false);
		}else if (command == "Quit") {
			client.setGameReady(false);
			System.exit(0);
		}		
	}

	public void setContainer(JPanel container)
	{
		this.container = container;
	}
}
