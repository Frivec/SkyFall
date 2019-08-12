package fr.frivec.listeners.player;

import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import fr.frivec.SkyFall;
import fr.frivec.core.SkyFallPlayer;
import fr.frivec.core.game.States;
import fr.frivec.core.game.Teams;
import fr.frivec.utils.creators.ItemCreator;
import fr.frivec.utils.scoreboard.ScoreboardSign;

public class PlayerDeathListener implements Listener {
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		
		if(States.isState(States.WAIT) || States.isState(States.NODAMAGE) || States.isState(States.FINISH))
			e.setCancelled(true);

	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		
		final Player player = e.getEntity();
		final Player killer = player.getKiller();
		
		if(killer != null) {
			
			final SkyFallPlayer killerPlayer = SkyFallPlayer.getPlayerByUUID(killer.getUniqueId());
			
			killer.sendMessage("§6+ 1.50 Coins ! (kill)");
			Bukkit.broadcastMessage(SkyFall.getInstance().getFileManager().getLine("messages.game.playerKilled", player, killer));
			killerPlayer.getStats().setKills(killerPlayer.getStats().getKills() + 1);
			killerPlayer.getStats().setCoinsWon(killerPlayer.getStats().getCoinsWon() + 1.5);
			
			for(Entry<Player, ScoreboardSign> entrys : SkyFall.getInstance().getBoards().entrySet())
				if(entrys.getKey().equals(killer))
					entrys.getValue().setLine(8, "§aTué(s) ➭  §f" + killerPlayer.getStats().getKills());
			
		}else
			Bukkit.broadcastMessage(SkyFall.getInstance().getFileManager().getLine("messages.game.playerDeath", player, null));
		
		player.sendMessage(SkyFall.getInstance().getFileManager().getLine("messages.game.playerDead"));
		SkyFall.getInstance().getGame().getPlayers().remove(player);
		SkyFall.getInstance().getBoards().get(player).destroy();
		
		for(Player pl : SkyFall.getInstance().getGame().getPlayers())
			ScoreboardSign.updateScoreboard(pl);
		
		States.checkEnd();
		Teams.removePlayerTeam(player);
		Teams.setPlayerTeam(player, Teams.SPEC);
		
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		
		final Player player = e.getPlayer();
		
		System.out.println("player respawn !");
		
		player.setGameMode(GameMode.SPECTATOR);
		
		player.getInventory().setItem(8, new ItemCreator(Material.COMPASS, 1).setDisplayName("§aTéléportation !").setLores(new String[] {"§5§oTéléportez vous sur des joueurs en vie."}).build());
		
		player.teleport(SkyFall.getInstance().getGame().getMap().getCenter());
		
	}

}
