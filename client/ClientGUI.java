package client;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ClientGUI extends JFrame
{
  private static final long serialVersionUID = 1L;
  private JPanel view1; //3 views
  private JPanel view2;
  private JPanel view3;
  private JPanel view4;
  private Client client;
  private CardLayout cardLayout = new CardLayout(); //Card Layout
  private JPanel container = new JPanel(cardLayout);
  public ClientGUI()
  {
    //Set close
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	client = new Client();
	client.setHost("localhost");
	client.setPort(8300);
	try

	{
		client.openConnection();
	} catch (IOException e)

	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}   
    
    //Set container to CardLayout
	container = new JPanel(cardLayout);
	client.setContainer(container);
    client.setCardLayout(cardLayout);
    
    //Create the different views
    view1 = new InitialPanel(cardLayout, container);
    view2 = new LoginPanel(cardLayout, container,client);
    view3 = new CreateAccount(cardLayout, container,client);
    view4 = new ContactPanel(cardLayout, container,client);
    
    //Add the different views to the CardLayoutContainer
    container.add(view1,"1");
    container.add(view2,"2");
    container.add(view3,"3");
    container.add(view4,"4");
      
    //Show the first 1
    cardLayout.show(container, "1");
    
    this.add(container,BorderLayout.CENTER);
	// Show the JFrame.
	this.setSize(550, 350);
	this.setVisible(true); 
  }
 
  public static void main(String[] args)
  {
    new ClientGUI();
  }
}