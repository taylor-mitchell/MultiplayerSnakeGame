package client;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ClientGUI extends JFrame
{

  private JPanel view1; //3 views
  private JPanel view2;
  private JPanel view3;

  private Client client;
  private CardLayout cardLayout; //Card Layout
  private JPanel container;
  private InitialControl ic;
  private LoginControl lc;
  private CreateAccountControl ac;
  
  public ClientGUI(Client client)
  {
	  this.client = client;
	  init();   
  }
  
  public void init() {
	  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  cardLayout = new CardLayout();
	  container = new JPanel(cardLayout);	  
	  
	  System.out.println(1);
	  
	  initClient();
	  
	  System.out.println(2);
	  
	  initInitial();
	  
	  System.out.println(3);
	  
	  initCreate();
	  
	  System.out.println(4);
	  
	  initLogin();
	  
	  System.out.println(5);
	  
	  container.add(view1,"1");
	  container.add(view2,"2");
	  container.add(view3,"3");
	  
	  System.out.println(6);
	  
	  cardLayout.show(container, "1");
	  
	  System.out.println(7);
	  
	  this.add(container,BorderLayout.CENTER);
	  // Show the JFrame.
	  this.setSize(550, 350);
	  this.setVisible(true); 
  }
  
  public void initClient() {	  
	  
	  client.setContainer(container);
	  client.setCardLayout(cardLayout);
  }
  
  public void initInitial() {
	  ic = new InitialControl(container);
	  view1 = new InitialPanel(ic);
  }
  
  public void initLogin() {
	  lc = new LoginControl(container, client);
	  view2 = new LoginPanel(lc);
  }

  public void initCreate() {
	  ac = new CreateAccountControl(container, client);
	  view3 = new CreateAccountPanel(ac);
  }
  
  public void error(String msg) {
	  System.out.println(msg);
  }
  
 

}