package client;

import ocsf.client.AbstractClient;

public class Client extends AbstractClient
{
	private ClientGUI gui;
	
	public Client(String host, int port)
	{
		super(host, port);
		// TODO Auto-generated constructor stub
	}

	public Client(ClientGUI gui)
	{
		// TODO Auto-generated constructor stub
		super("",0);
	}
	
	@Override
	protected void handleMessageFromServer(Object arg0)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void connectionException(Exception exception)
	{
		// TODO Auto-generated method stub
		super.connectionException(exception);
	}
	
	@Override
	protected void connectionEstablished()
	{
		// TODO Auto-generated method stub
		super.connectionEstablished();
	}
}
