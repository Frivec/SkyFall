package fr.frivec.core.database.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.entity.Player;

import fr.frivec.SkyFall;
import fr.frivec.core.SkyFallPlayer;
import fr.frivec.core.game.StatsData;

public class DataUtils {
	
	public static void createTables() {
		
		try {
			final Connection connection = SkyFall.getInstance().getDatabaseManager().getConnection().getConnection();
			final PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS LOCAL_PLAYERS (uuid varchar(255) NOT NULL, name varchar(25) NOT NULL, prefix varchar(255) NOT NULL, color varchar(2) NOT NULL , coins double(11,2) NOT NULL, kills int(11) NOT NULL, victories int(11) NOT NULL)");
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		
	}
	
	public static boolean hasAccount(UUID uuid) {
		
		boolean hasAccount = false;
		
		try {
			final Connection connection = SkyFall.getInstance().getDatabaseManager().getConnection().getConnection();
			final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM LOCAL_PLAYERS WHERE uuid = '" + uuid.toString() + "'");
			
			preparedStatement.executeQuery();
			
			final ResultSet result = preparedStatement.getResultSet();
			
			if(result.next())
				hasAccount = true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return hasAccount;
	}
	
	public static void createAccount(Player player, String rank, String color) {
		
		try {
			final Connection connection = SkyFall.getInstance().getDatabaseManager().getConnection().getConnection();
			final PreparedStatement statement = connection.prepareStatement("INSERT INTO LOCAL_PLAYERS (uuid, name, prefix, color, coins, kills, victories) VALUES (?,?,?,?,?,?,?)");
			
			statement.setString(1, player.getUniqueId().toString());
			statement.setString(2, player.getName());
			statement.setString(3, rank);
			statement.setString(4, color);
			statement.setDouble(5, 00.0);
			statement.setInt(6, 0);
			statement.setInt(7, 0);
			
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static String getInfoFromDatabase(Player player, String info) {
		
		String string = "";
		
		try {
			final Connection connection = SkyFall.getInstance().getDatabaseManager().getConnection().getConnection();
			final PreparedStatement preparedStatement = connection.prepareStatement("SELECT " + info + " FROM LOCAL_PLAYERS WHERE uuid = ?");
			preparedStatement.setString(1, player.getUniqueId().toString());
			
			preparedStatement.executeQuery();
			
			final ResultSet result = preparedStatement.getResultSet();
			
			if(result.next())
				string = result.getString(info);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return string;
	}
	
	public static void updateStats(Player player) {
		
		try {
			final Connection connection = SkyFall.getInstance().getDatabaseManager().getConnection().getConnection();
			
			final SkyFallPlayer sPlayer = SkyFallPlayer.getPlayerByUUID(player.getUniqueId());
			final StatsData stats = sPlayer.getStats();
			
			final PreparedStatement statement = connection.prepareStatement("UPDATE LOCAL_PLAYERS SET coins = ?, kills = ?, victories = ? WHERE uuid = ?");
			
			statement.setDouble(1, stats.getCoinsWon() + sPlayer.getCoins());
			statement.setInt(2, (stats.getKills() + sPlayer.getKills()));
			if(sPlayer.isWinner()) 
				statement.setInt(3, sPlayer.getVictories() + 1);
			else
				statement.setInt(3, sPlayer.getVictories());
			statement.setString(4, player.getUniqueId().toString());
			
			statement.executeUpdate();
			
			System.out.println("");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
