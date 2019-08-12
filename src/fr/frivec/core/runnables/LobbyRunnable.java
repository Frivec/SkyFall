package fr.frivec.core.runnables;

import java.text.SimpleDateFormat;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.frivec.SkyFall;
import fr.frivec.core.SkyFallPlayer;
import fr.frivec.core.game.States;
import fr.frivec.core.game.Teams;
import fr.frivec.core.kits.model.KitManager;
import fr.frivec.core.maps.SfMap;
import fr.frivec.utils.Sounds;
import fr.frivec.utils.creators.NMSUtils;
import fr.frivec.utils.scoreboard.ScoreboardSign;

public class LobbyRunnable extends BukkitRunnable {
	
	private static LobbyRunnable instance;
	
	public static int timer = 120;
	public static boolean forceStart = false;
	public static boolean start = false;
	
	public LobbyRunnable() {
		instance = this;
		SkyFall.getInstance().getBossbar().setTitle("§bDémarrage dans " + new SimpleDateFormat("mm:ss").format(timer * 1000));
		SkyFall.getInstance().getBossbar().setColor(BarColor.GREEN);
		for(Player pl : SkyFall.getInstance().getGame().getPlayers()) 
			SkyFall.getInstance().getBossbar().addPlayer(pl);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		
		if(timer == 120 || timer == 60 || timer == 30 || timer == 15 || timer == 10 || timer == 5 || timer == 4 || timer == 3 || timer == 2 || timer == 1) {
			Bukkit.broadcastMessage(SkyFall.getInstance().getFileManager().getLine("messages.wait.timerMessage", null, null, timer));
			for(Player player : SkyFall.getInstance().getGame().getPlayers()) 
				new Sounds(player).playSound(Sound.BLOCK_NOTE_PLING);
		}
		
		if(timer == 0) {
			
			if(SkyFall.getInstance().getMapsLoaded().size() == 1) {
				SkyFall.getInstance().getGame().setMap(SkyFall.getInstance().getMapsLoaded().get(0));
				SkyFall.getInstance().getGame().getMap().loadLocations();
			}else {
				
				SfMap winMap = null;
				
				for(SfMap maps : SkyFall.getInstance().getMapsLoaded()) {
					
					if(winMap == null)
						winMap = maps;
					else {
						
						if(winMap.getVotes() < maps.getVotes()) {
							
							winMap = maps;
							
						}
						
					}
					
				}
				
				Bukkit.broadcastMessage("§6La carte gagnante est §a" + winMap.getWorld().getName() + "§a !");
				SkyFall.getInstance().getGame().setMap(winMap);
				winMap.loadLocations();
				
			}
			
			for(Player player : SkyFall.getInstance().getGame().getPlayers()) {
				
				if(SkyFallPlayer.getPlayerByUUID(player.getUniqueId()).getKit() == null) {
					
					SkyFallPlayer.getPlayerByUUID(player.getUniqueId()).setKit(KitManager.getKitByKitManager(KitManager.GUERRIER));
					player.sendMessage("§cVous n'avez pas choisi de kit. Le kit par défaut vous a été attribué.");
					player.sendMessage("§aVous recevez le kit §6Guerrier !");
					
				}
				
				NMSUtils.sendTitle(player, "§cSkyFall", "§cDémarrage de la partie !", 25);
				player.getInventory().clear();
				SkyFallPlayer.getPlayerByUUID(player.getUniqueId()).getKit().giveKit(player);
				player.setGameMode(GameMode.SURVIVAL);
				Teams.removePlayerTeam(player);
				Teams.setPlayerTeam(player, Teams.PLAYERS);
			}
			
			SkyFall.getInstance().getGame().getMap().spawnPlayers();
			
			for(Entry<Player, ScoreboardSign> entrys : SkyFall.getInstance().getBoards().entrySet()) {
				entrys.getValue().setLine(1, "--------------");
				entrys.getValue().setLine(2, "§9Serveur ➭ §fSkyFall-Démo");
				entrys.getValue().setLine(3, "§r ");
				entrys.getValue().setLine(4, "§5Mode ➭ §fSolo");
				entrys.getValue().setLine(5, "§8 ");
				entrys.getValue().setLine(6, "§6Joueur(s) en vie ➭ §f" + SkyFall.getInstance().getGame().getPlayers().size());
				entrys.getValue().setLine(7, "§b ");
				entrys.getValue().setLine(8, "§aTué(s) ➭  §f" + SkyFallPlayer.getPlayerByUUID(entrys.getKey().getUniqueId()).getKills());
				entrys.getValue().setLine(9, "§c ");
				entrys.getValue().setLine(10, "§dCentre ➭ §f0.0");
				entrys.getValue().setLine(11, "§l ");
				entrys.getValue().setLine(12, "§4Durée ➭ §f" + new SimpleDateFormat("mm:ss").format(GameRunnable.timer * 1000));
				entrys.getValue().setLine(13, "§f--------------");
				
			}
			
			SkyFall.getInstance().getBossbar().hide();
			
			States.setState(States.GAME);
						
			if(!GameRunnable.start) {
				GameRunnable.start = true;
				new GameRunnable().runTaskTimer(SkyFall.getInstance(), 0, 20);
			}
			
			if(!NoDamageRunnable.start) {
				NoDamageRunnable.start = true;
				new NoDamageRunnable().runTaskTimer(SkyFall.getInstance(), 0, 20);
			}
				
			stop();
		}
		
		SkyFall.getInstance().getBossbar().setTitle("§bDémarrage dans " + new SimpleDateFormat("mm:ss").format(timer * 1000));
		for(Player pl : SkyFall.getInstance().getGame().getPlayers()) 
			SkyFall.getInstance().getBossbar().addPlayer(pl);
		
		timer--;
		
	}
	
	public void stop() {
		this.cancel();
	}
	
	public static LobbyRunnable getInstance() {
		return instance;
	}

}
