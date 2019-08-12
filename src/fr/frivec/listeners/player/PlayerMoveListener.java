package fr.frivec.listeners.player;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import fr.frivec.SkyFall;
import fr.frivec.core.game.Game;
import fr.frivec.core.game.States;
import fr.frivec.core.game.Teams;

public class PlayerMoveListener implements Listener {
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		
		final Player player = e.getPlayer();
		final Location location = player.getLocation();
		final Game game = SkyFall.getInstance().getGame();
		
		if(States.isState(States.GAME) && Teams.getPlayerTeam(player).equals(Teams.PLAYERS) && game.getMap() != null && player.getGameMode().equals(GameMode.SURVIVAL))
			SkyFall.getInstance().getBoards().get(player).setLine(10, "§dCentre ➭ §f" + distanceCalcul(location, game.getMap().getCenter()) + " blocs");
		else if(States.isState(States.WAIT) && location.getY() <= 110) {
			player.teleport(new Location(Bukkit.getWorld("world"), -7.5, 120, -7.5, -90.5f, 1.5f));
			player.sendMessage("§cVous ne pouvez pas sortir de la zone d'attente.");
		}else if(States.isState(States.GAME) && Teams.getPlayerTeam(player).equals(Teams.SPEC) && player.getGameMode().equals(GameMode.SPECTATOR)) {
			
			if(player.getLocation().getY() <= 80 || distanceCalcul(location, game.getMap().getCenter()) >= 250) {			
				player.sendMessage("§cVous ne pouvez pas sortir des limites de la carte !");
				player.teleport(game.getMap().getCenter());
			}
		}else if(States.isState(States.NODAMAGE))
			e.setCancelled(true);
		
	}
	
	private double distanceCalcul(Location startLocation, Location endLocation) {
		
		double result = 0.0d;
		
		result = arrondi(Math.sqrt(Math.pow(endLocation.getZ() - startLocation.getZ(), 2) + Math.pow(endLocation.getX() - startLocation.getX(), 2)), 0);
		
		return result;
	}
	
	private double arrondi(double A, int B) {

		return (double) ( (int) (A * Math.pow(10, B) + .5)) / Math.pow(10, B);
	}

}
