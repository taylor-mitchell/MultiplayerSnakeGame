package client;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JPanel;



public class LoginControl implements ActionListener
{
  // Private data fields for the container and chat client.
  private JPanel container;
  private Client client;
  private boolean ready;
  
  // Constructor for the login controller.
  public LoginControl(JPanel container, Client client)
  {
	this.ready = false;
    this.container = container;
    this.client = client;
   
  }
  
  // Handle button clicks.
  public void actionPerformed(ActionEvent ae)
  {
    // Get the name of the button clicked.
    String command = ae.getActionCommand();

    // The Cancel button takes the user back to the initial panel.
    if (command == "Cancel")
    {
      CardLayout cardLayout = (CardLayout)container.getLayout();
      cardLayout.show(container, "1");
    }

    // The Submit button submits the login information to the server.
    else if (command == "Submit")
    {
      // Get the username and password the user entered.
      LoginPanel loginPanel = (LoginPanel)container.getComponent(1);
      CardLayout cardLayout = (CardLayout)container.getLayout();
      LoginData data = new LoginData(loginPanel.getUsername(), loginPanel.getPassword());
      
      // Check the validity of the information locally first.
      if (data.getUsername().equals("") || data.getPassword().equals(""))
      {
        displayError("You must enter a username and password.");
        return;
      }else {

      	try {
			client.sendToServer(data);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
      	
      	GamePanel gp = (GamePanel)container.getComponent(3);
      	gp.setUsername(data.getUsername());
      	gp.setScore(0);
      	

      }
      
     
     
    }
    
   
  }
  



  // Method that displays a message in the error - could be invoked by ChatClient or by this class (see above)
  public void displayError(String error)
  {
    LoginPanel loginPanel = (LoginPanel)container.getComponent(1);
    loginPanel.setError(error);
  }
}
