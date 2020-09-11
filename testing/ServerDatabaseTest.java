package testing;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import client.Client;
import server.Server;


class ServerDatabaseTest {
	private static Server server;


	
	@BeforeAll
	static void setUp() {
		server = new Server();
		System.out.println("hello");

		
		while(!server.isListening()) {
			
		}

	}	

	
	@Test
	void testDatabaseOpen() {
		assertNotNull(server.getDatabase());
		
	}
	
	@Test
	void testUser() {
		server.saveUser("taylor", "taylor");
		assertTrue(server.isThere("taylor", "taylor"));
	}
	
	
	

}
