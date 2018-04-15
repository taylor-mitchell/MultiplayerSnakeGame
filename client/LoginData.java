package client;

import java.io.Serializable;

public class LoginData implements Serializable
{
	private String userame;
	private String password;
	
	public LoginData(String userame, String password)
	{
		// TODO Auto-generated constructor stub
	}

	public String getUserame()
	{
		return userame;
	}

	public String getPassword()
	{
		return password;
	}
}
