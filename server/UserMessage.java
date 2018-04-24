package server;

import java.io.Serializable;

public class UserMessage implements Serializable
{
	private int turn;
	private boolean zoom;

	public UserMessage()
	{
		setTurn(0);
		setZoom(false);
	}

	public UserMessage(int turn, boolean zoom)
	{
		this.setTurn(turn);
		this.setZoom(zoom);
	}

	public int getTurn()
	{
		return turn;
	}

	public void setTurn(int turn)
	{
		this.turn = turn;
	}

	public boolean isZoom()
	{
		return zoom;
	}

	public void setZoom(boolean zoom)
	{
		this.zoom = zoom;
	}

	public UserMessage clone()
	{
		return new UserMessage(turn, zoom);
	}

	public boolean equals(UserMessage rhs)
	{
		if (this.turn == rhs.getTurn() && this.zoom == rhs.isZoom())
		{
			return true;
		}
		else
		{
			return false;
		}

	}

}
