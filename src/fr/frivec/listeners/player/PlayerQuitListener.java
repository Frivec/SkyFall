package fr.frivec.listeners.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.frivec.SkyFall;
import fr.frivec.core.database.utils.DataUtils;
import fr.frivec.core.game.States;
import fr.frivec.core.game.Teams;
import fr.frivec.utils.scoreboard.ScoreboardSign;

public class PlayerQuitListener implements Listener {
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		
		final Player player = e.getPlayer();
		final SkyFall main = SkyFall.getInstance();
		
		e.setQuitMessage("");
		
		if(States.isState(States.WAIT)) {
			
			if(main.getGame().getPlayers().contains(player)) {
				main.getGame().getPlayers().remove(player);
				
				SkyFall.getInstance().getBossbar().removePlayer(player);
			
				Bukkit.broadcastMessage(main.getFileManager().getLine("messages.wait.quit", player, null, 0));
			}
			
		}else if(States.isState(States.GAME)) {
			
			if(main.getGame().getPlayers().contains(player)) {
				
				main.getGame().getPlayers().remove(player);
				
				player.setHealth(0.0);
				
				Bukkit.broadcastMessage(main.getFileManager().getLine("messages.game.quit", player, null, 0));
				States.checkEnd();
				
			}
			
		}else {
			
			if(main.getGame().getPlayers().contains(player))
				main.getGame().getPlayers().remove(player);
			
		}
		
		for(Player pl : main.getGame().getPlayers())
			ScoreboardSign.updateScoreboard(pl);
		
		Teams.removePlayerTeam(player);
		
		if(main.hasDatabase())
		
			DataUtils.updateStats(player);
		
	}

}
