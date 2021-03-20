package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import connectionDB.ConnectionFactory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;

public class TestConnection {
	
	Connection connection;
	
	@BeforeEach
	public void setUp() {
		connection=ConnectionFactory.getConnection();
	}
	
	@Test
	void testGetConnection() throws SQLException {
		assertFalse(connection.isClosed());
	}
	
	@Test
	void testCloseConnection() throws SQLException {
		ConnectionFactory.closeConnection(connection);
		assertTrue(connection.isClosed());
	}
	
}
