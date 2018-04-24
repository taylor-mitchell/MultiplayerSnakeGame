package client;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class CreateAccountPanel extends JPanel
{

	private JTextField usernameField;
	private JTextField password1Field;
	private JTextField password2Field;
	private JLabel errorLabel;

	public String getUsername()
	{
		return usernameField.getText();
	}

	public String getPassword1()
	{
		return password1Field.getText();
	}

	public String getPassword2()
	{
		return password2Field.getText();
	}

	public void setError(String error)
	{
		errorLabel.setText(error);
	}

	public CreateAccountPanel(CreateAccountControl ac)
	{
		// Create a panel for the labels at the top of the GUI.
		JPanel labelPanel = new JPanel(new GridLayout(3, 1, 5, 5));
		errorLabel = new JLabel("", JLabel.CENTER);
		errorLabel.setForeground(Color.RED);
		JLabel instructionLabel = new JLabel("Enter your username and password to log in.", JLabel.CENTER);
		labelPanel.add(errorLabel);
		labelPanel.add(instructionLabel);

		// Create a panel for the login information form.
		JPanel createPanel = new JPanel(new GridLayout(3, 3, 5, 5));
		JLabel usernameLabel = new JLabel("Username:", JLabel.RIGHT);
		usernameField = new JTextField(10);
		JLabel password1Label = new JLabel("Password:", JLabel.RIGHT);
		password1Field = new JPasswordField(10);
		JLabel password2Label = new JLabel("Re-enter password:", JLabel.RIGHT);
		password2Field = new JPasswordField(10);
		createPanel.add(usernameLabel);
		createPanel.add(usernameField);
		createPanel.add(password1Label);
		createPanel.add(password1Field);
		createPanel.add(password2Label);
		createPanel.add(password2Field);

		// Create a panel for the buttons.
		JPanel buttonPanel = new JPanel();
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(ac);
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(ac);
		buttonPanel.add(submitButton);
		buttonPanel.add(cancelButton);

		// Arrange the three panels in a grid.
		JPanel grid = new JPanel(new GridLayout(3, 1, 0, 10));
		grid.add(labelPanel);
		grid.add(createPanel);
		grid.add(buttonPanel);
		this.add(grid);
	}

}
