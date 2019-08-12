package fr.frivec.core.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DbConnection {
	
	private DatabaseCredentials credentials;
	private Connection connection;
	
	public DbConnection(DatabaseCredentials databaseCredentials) {
		
		this.credentials = databaseCredentials;
		this.connect();
	}
	
	private void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.connection = DriverManager.getConnection(this.credentials.toURL(), this.credentials.getUser(), this.credentials.getPass());
			
			Logger.getLogger("Minecraft").info("Connect to database");
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		if(this.connection != null) {
			try {
				if(!this.connection.isClosed()) {
					this.connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Connection getConnection() throws SQLException {
		if(this.connection != null) {
			if(!this.connection.isClosed()) {
				return this.connection;
			}
		}
		
		connect();
		return this.connection;
	}

}
