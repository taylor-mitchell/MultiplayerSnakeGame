package server;

import java.util.Map;

import game.Entity;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class Server extends AbstractServer
{
	private ServerGUI serverGUI;
	private Map<Integer, User> connectedUsers;
	
	public Server(int port)
	{
		super(port);
	}

	public Server(ServerGUI serverGUI)
	{
		super(0);
	}

	@Override
	protected void handleMessageFromClient(Object arg0, ConnectionToClient arg1)
	{
		// TODO Auto-generated method stub
	}	
	
	@Override
	protected void listeningException(Throwable exception)
	{
		// TODO Auto-generated method stub
		super.listeningException(exception);
	}
	
	@Override
	protected void serverStarted()
	{
		// TODO Auto-generated method stub
		super.serverStarted();
	}
	
	@Override
	protected void serverStopped()
	{
		// TODO Auto-generated method stub
		super.serverStopped();
	}
	
	@Override
	protected void serverClosed()
	{
		// TODO Auto-generated method stub
		super.serverClosed();
	}
	
	@Override
	protected void clientConnected(ConnectionToClient client)
	{
		// TODO Auto-generated method stub
		super.clientConnected(client);
	}
	
	private void updateWorldEntities()
	{
		
	}
	
	private boolean checkCollision(Entity lhs, Entity rhs)
	{
		return false;
	}
}
