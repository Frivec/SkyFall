package fr.frivec.core.database;

public class DatabaseCredentials {
	
	private String host;
	private String user;
	private String pass;
	private String dbName;
	private int port;
	
	public DatabaseCredentials(String host, String user, String dbName, String pass, int port) {
		
		this.host = host;
		this.user = user;
		this.dbName = dbName;
		this.pass = pass;
		this.port = port;
		
	}

	public String toURL() {
		final StringBuilder sb = new StringBuilder();
		
		sb.append("jdbc:mysql://")
		.append(host)
		.append(":")
		.append(port)
		.append("/")
		.append(dbName);
		
		return sb.toString();
	}

	public String getUser() {
		return user;
	}

	public String getPass() {
		return pass;
	}	

}
