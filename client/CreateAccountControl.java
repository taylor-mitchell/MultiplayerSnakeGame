package client;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JPanel;



public class CreateAccountControl implements ActionListener{

	private JPanel container;
	private Client client;
	
	public CreateAccountControl(JPanel container, Client client) {
		this.container = container;
		this.client = client;
	}
	
	public void actionPerformed(ActionEvent ae) {
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
	      CreateAccountPanel createPanel = (CreateAccountPanel)container.getComponent(2);
	      CreateAccountData data = new CreateAccountData(createPanel.getUsername(), createPanel.getPassword1(), createPanel.getPassword2());
	      
	      // Check the validity of the information locally first.
	      if (data.getUsername().equals("") || data.getPassword1().equals("") || data.getPassword2().equals(""))
	      {
	        displayError("You must enter a username and password.");
	        return;
	      }else if (!data.getPassword1().equals(data.getPassword2())) {
	    	  displayError("The passwords are not the same!");
	    	  return;
	      }else {

	      	try {
				client.sendToServer(data);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
	      }
	      
	      
	     
	    }
		
	}

	
	public void displayError(String error)
	  {
	    CreateAccountPanel createPanel = (CreateAccountPanel)container.getComponent(2);
	    createPanel.setError(error);
	  }

}
