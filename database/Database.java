package database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

public class Database
{
	private ArrayList<String> username = new ArrayList<String>();
	private ArrayList<String> password = new ArrayList<String>();
	private Connection conn;
	private String key;
 
	public Database()
	{
		
		
		//read from the properties named db.properties
		//Create Connection
		//Read properties file
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			//System.out.println("try hard");
		}
		catch (ClassNotFoundException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	    Properties prop = new Properties();
	    
	    FileInputStream fis = null;
	    try 
	    {
	    	fis = new FileInputStream("database/db.properties");	
	    } 
	    catch (IOException e)
	    {
	    	System.out.println(e);
	    }
	    try 
	    {
	    	prop.load(fis);
	    } 
	    catch(IOException e) 
	    {
	    	System.out.println(e.getMessage());
	    }
	    
	    System.out.println("Gets here");
	    String url = prop.getProperty("url");
	    String user = prop.getProperty("user");
	    String pass = prop.getProperty("password"); 
	    try
	    {
	    	 System.out.println("Gets here too");
	    	 //Read the connection properties as Strings
	    	 System.out.println(url);
	      
	    	 //Perform the Connection
	    	 conn = DriverManager.getConnection(url,user,pass);
	    }
	    catch (SQLException sql) 
	    {
	    	System.out.println("Gets error");
	    	System.out.println(sql);
	    }
	    System.out.println("Gets here");
	}

	public ArrayList getUserPass(String query)
	{
		ArrayList queryString = new ArrayList<String>();
		try 
		{
			Statement stmt = conn.createStatement();
			// Dynamically take the input parameter query and ue in execute query()
			//2. take the results set and extract(parse) each column in each row
			ResultSet rs;
			System.out.println("its me");
			rs=stmt.executeQuery("select '"
			 		+ query +"' , aes_decrypt(password,'key') "
			 				+ " from user");  
			
			
			ResultSetMetaData rmd;
			rmd = rs.getMetaData();
			int no_columns = rmd.getColumnCount();
			//store each string in the array list
			int i = 1;
			while(rs.next()) 
			{	
				System.out.println("all");
		        queryString.add(rs.getString(i));
			}
		      
			//return the arraylist containing the strings or null if no data found
		}
		catch (SQLException sql) 
		{
			System.out.println(sql);
		}
		
		if(queryString.size() > 0)
			return queryString;
	  
		return null;
	}
	
	public ArrayList<String> query(String query)
	{
		ArrayList queryString = new ArrayList<String>();
		try 
		{
			Statement stmt = conn.createStatement();
			// Dynamically 
			//take the input parameter query and ue in execute query()
			//2. take the results set and extract(parse) each column in each row
			ResultSet rs;
			System.out.println("its me");
			rs=stmt.executeQuery("select * from user");  
			//create a string from the columns - concatnate to create a comma
			// delimited string field1, field2,...field3
			ResultSetMetaData rmd;
			rmd = rs.getMetaData();
			int no_columns = rmd.getColumnCount();
			//store each string in the array list
			int i = 1;
			while(rs.next()) 
			{
				System.out.println("all");
				queryString.add(rs.getString(i));
			}
			//return the arraylist containing the strings or null if no data found
		    }
		catch (SQLException sql) 
		{
			System.out.println(sql);
		}
		
		if(queryString.size() > 0)
			return queryString;
	  
		return null;
	}
	
	public ArrayList<String> queryUsername()
	{
		ArrayList queryString = new ArrayList<String>();
		try
		{
			Statement stmt = conn.createStatement();
			// Dynamically 
			//take the input parameter query and ue in execute query()
			//2. take the results set and extract(parse) each column in each row
			ResultSet rs;
			System.out.println("its me");
			rs=stmt.executeQuery("select  username from User");  
			//create a string from the columns - concatnate to create a comma
			// delimited string field1, field2,...field3
			ResultSetMetaData rmd;
			rmd = rs.getMetaData();
			int no_columns = rmd.getColumnCount();
			//store each string in the array list
			int i = 1;
			while(rs.next()) 
			{
				System.out.println("username");
				queryString.add(rs.getString(i));
			}
	      
		  //return the arraylist containing the strings or null if no data found
		}
		catch (SQLException sql) 
		{
			System.out.println(sql);
		}
		if(queryString.size() > 0)
			return queryString;
	  
		return null;
	}
	
	public ArrayList<String> queryPassword(String query)
	{
		ArrayList queryString = new ArrayList<String>();
		try
		{
			Statement stmt = conn.createStatement();
			// Dynamically 
			//take the input parameter query and ue in execute query()
			//2. take the results set and extract(parse) each column in each row
			ResultSet rs;
			System.out.println("its me");
			rs=stmt.executeQuery("select * aes_decrypt(password,'key') from User");  
			//create a string from the columns - concatnate to create a comma
			// delimited string field1, field2,...field3
			ResultSetMetaData rmd;
			rmd = rs.getMetaData();
			int no_columns = rmd.getColumnCount();
			//store each string in the array list
			int i = 1;
			while(rs.next()) 
			{
				System.out.println("is this working");
				queryString.add(rs.getString(i));
			}
	      
		  //return the arraylist containing the strings or null if no data found  
		}
		catch (SQLException sql) 
		{
			System.out.println(sql);
		}
		if(queryString.size() > 0)
			return queryString;
	  
		return null;
	}
	
	public boolean isThere(String username, String password)
	{
		try
		{
			Statement stmt = conn.createStatement();
			// Dynamically 
			//take the input parameter query and ue in execute query()
			//2. take the results set and extract(parse) each column in each row
			ResultSet rs;
			System.out.println("its me");
			rs=stmt.executeQuery("select username, password from user"
					+ " where username ='"+username+"' and"
					+ " password  = aes_encrypt('"+password+"','key')");  
			//create a string from the columns - concatnate to create a comma
			// delimited string field1, field2,...field3
			ResultSetMetaData rmd;
			if(!rs.isBeforeFirst())
				return false;
			
			rmd = rs.getMetaData();
			int no_columns = rmd.getColumnCount();
			//store each string in the array list
			int i = 1;
			while(rs.next()) 
			{
				System.out.println("is this working");     
			}
	      
		  //return the arraylist containing the strings or null if no data found  
		}
		catch (SQLException sql)
		{
			System.out.println(sql);
		}
		
		return true;
	}
  
	public boolean executeDML(String username, String password)
	{
		try 
		{
			 Statement stmt = conn.createStatement();
			 String dml2 = "insert into user values('"+username+"',"
			 		+ "aes_encrypt('"+password+"','key') )";
			 // Dynamically 
			 //take the input parameter query and ue in execute query()
			 //2. take the results set and extract(parse) each column in each row
			 stmt.execute(dml2);
			 System.out.println("inserted");
			 return true;  
		}
		catch (SQLException sql) 
		{
			  
		}
		//use execute instead of execute query
		//no result set
		return false;
	} 
}