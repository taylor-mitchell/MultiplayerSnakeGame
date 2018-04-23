package client;
import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamePanel extends JPanel{
	
	private GameControl gc;
	private JLabel scoreLabel;
//	private JLabel topScoreLabel;
	private JButton play;
	private JButton stop;
	private JButton quit;
	private String username;
	
	public GamePanel(GameControl gc) {
		this.gc = gc;
		this.setLayout(new GridLayout(2, 1, 5, 5));
//		this.setLayout(new GridLayout(3, 1, 5, 5));
		
		username = "";
		scoreLabel = new JLabel("", JLabel.CENTER);
//		topScoreLabel = new JLabel("", JLabel.CENTER);
		JPanel buttonPanel = new JPanel();
		
		play = new JButton("Play");
		play.addActionListener(gc);
		
		stop = new JButton("Stop");
		stop.addActionListener(gc);
		
		quit = new JButton("Quit");
		quit.addActionListener(gc);
		
		buttonPanel.add(play);
		buttonPanel.add(stop);
		buttonPanel.add(quit);
		
		
		this.add(scoreLabel, BorderLayout.NORTH);
//		this.add(topScoreLabel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
	}
	
	public void setScore(Integer score) {
		scoreLabel.setText(username + ": " + score.toString());
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
//	public void setTopScore(Integer score) {
//		topScoreLabel.setText("Top Score: " + score.toString());
//	}

}
