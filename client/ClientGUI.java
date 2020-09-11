package client;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ClientGUI extends JFrame
{

  private JPanel view1; //4 views
  private LoginPanel view2;
  private CreateAccountPanel view3;
  private GamePanel view4;

  private Client client;
  private CardLayout cardLayout; //Card Layout
  private JPanel container;
  private InitialControl ic;
  private LoginControl lc;
  private CreateAccountControl ac;
  private GameControl gc;
  
  public ClientGUI(Client client)
  {
	  this.client = client;
	  init();   
  }
  
  public void init() {
	  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  cardLayout = new CardLayout();
	  container = new JPanel(cardLayout);	  
	  
	  initClient();
	  
	  initInitialScreen();	 
	  
	  initCreateScreen();
	  
	  initLoginScreen();
	  
	  initGameScreen();
	  
	  container.add(view1,"1");
	  container.add(view2,"2");
	  container.add(view3,"3");
	  container.add(view4, "4");
	  
	  cardLayout.show(container, "1");
	  
	  this.add(container,BorderLayout.CENTER);
	  // Show the JFrame.
	  this.setSize(550, 350);
	  this.setVisible(true); 
  }
  
  public void initClient() {	  
	  
	  client.setContainer(container);
	  client.setCardLayout(cardLayout);
  }
  
  public void initInitialScreen() {
	  ic = new InitialControl(container);
	  view1 = new InitialPanel(ic);
  }
  
  public void initLoginScreen() {
	  lc = new LoginControl(container, client);
	  view2 = new LoginPanel(lc);
  }

  public void initCreateScreen() {
	  ac = new CreateAccountControl(container, client);
	  view3 = new CreateAccountPanel(ac);
  }
  
  public void initGameScreen() {
	  gc = new GameControl(client);
	  view4 = new GamePanel(gc);
	  gc.setContainer(view4);
  }
  
  public void error(String msg) {
	  System.out.println(msg);
  }
  
  public void setUsername(String username) {
	  view4.setUsername(username);
  }
  
  public void setScore(Integer score) {
	  view4.setScore(score);
  } 
  
  public void setReady() {
	  cardLayout.show(container, "4");
  }
  
  public void setNotReady() {
	  lc.displayError("Login Failed");
  }
  
  public void createAccountSuccess() {
	  cardLayout.show(container, "2");
  }
  
  public void createAccountFailed() {
	  ac.displayError("Account not created");
  }
  
}