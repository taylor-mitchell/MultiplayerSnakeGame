package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JPanel;

public class GameControl implements ActionListener{
	
	private JPanel container;
	private Client client;

	public GameControl(JPanel container, Client client) {
		this.container = container;
		this.client = client;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		
		if (command == "Play") {
			client.setGameReady(true);
		}else if (command == "Stop") {
			client.setGameReady(false);
		}else if (command == "Quit") {
			try {
				client.sendToServer(false);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.exit(0);
		}		
	}
	


}
