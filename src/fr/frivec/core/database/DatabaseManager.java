package fr.frivec.core.database;

import fr.frivec.SkyFall;

public class DatabaseManager {
	
	private DbConnection connection;
	
	public DatabaseManager() {
		
		final SkyFall skyFall = SkyFall.getInstance();
		
		this.connection = new DbConnection(new DatabaseCredentials(skyFall.getConfig().getString("database.host"),skyFall.getConfig().getString("database.user"), skyFall.getConfig().getString("database.database"), skyFall.getConfig().getString("database.password"), skyFall.getConfig().getInt("database.port")));
	}
	
	public void close() {
		this.connection.close();
	}
	
	public DbConnection getConnection() {
		return connection;
	}

}