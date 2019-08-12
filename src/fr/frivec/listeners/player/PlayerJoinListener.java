package fr.frivec.listeners.player;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import fr.frivec.SkyFall;
import fr.frivec.core.SkyFallPlayer;
import fr.frivec.core.game.States;
import fr.frivec.core.game.Teams;
import fr.frivec.core.kits.model.Kit;
import fr.frivec.core.runnables.LobbyRunnable;
import fr.frivec.utils.Sounds;
import fr.frivec.utils.creators.ItemCreator;
import fr.frivec.utils.creators.NMSUtils;
import fr.frivec.utils.scoreboard.ScoreboardSign;

public class PlayerJoinListener implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		
		final Player player = e.getPlayer();
		final SkyFall main = SkyFall.getInstance();
		
		e.setJoinMessage("");
		
		//Can player join ?
		if(!States.isState(States.WAIT)) {
			player.sendMessage(main.getFileManager().getLine("messages.game.join"));
			player.setGameMode(GameMode.SPECTATOR);
			Teams.setPlayerTeam(player, Teams.SPEC);
				
		}else {
				
			if(!main.getGame().getPlayers().contains(player))
				main.getGame().getPlayers().add(player);
				
			if(main.getGame().getPlayers().size() >= 6 && !LobbyRunnable.forceStart && !LobbyRunnable.start) {
				LobbyRunnable.start = true;
				LobbyRunnable.timer = 120;
				new LobbyRunnable().runTaskTimer(SkyFall.getInstance(), 0, 20);
			}else if(main.getGame().getPlayers().size() < 10 && !LobbyRunnable.forceStart && LobbyRunnable.start && LobbyRunnable.timer != 10)
				LobbyRunnable.timer = 10;
			else if(main.getGame().getPlayers().size() < 6 && !LobbyRunnable.forceStart && LobbyRunnable.start) {
				Bukkit.broadcastMessage("§cIl n'y a plus assez de joueurs. Annulation du démarrage !");
				LobbyRunnable.getInstance().stop();
				LobbyRunnable.start = false;
			}
				
			Bukkit.broadcastMessage(SkyFall.getInstance().getFileManager().getLine("messages.wait.join", player, null, 0));
				
			player.sendMessage("§6§m----------------------");
			player.sendMessage("§aBienvenue sur cette version 'Remake' du SkyFall d'§bEpicube §a!");
			player.sendMessage("§6Vous pouvez considérer ce jeu comme un fan-made :)");
			player.sendMessage("§3Le jeu original a été imaginé et créé par §aMewSoul §3!");
			player.sendMessage("§aCette version du jeu a été développée entièrement par Frivec.");
			player.sendMessage("§6§m----------------------");
				
			final ItemStack kitsSelector = new ItemCreator(Material.NETHER_STAR, 1).setDisplayName(main.getFileManager().getLine("items.kitSelector.name")).setLores(main.getFileManager().getStringList("items.kitSelector.description")).build();
			final ItemStack voteStart = new ItemCreator(Material.SLIME_BALL, 1).setDisplayName("§aCommencer la partie").setLores(new String[] {"§5§oVotez pour commencer la partie."}).build();
			ItemStack voteMap = null;
						
			if(main.getMapsLoaded().size() != 1)
				voteMap = new ItemCreator(Material.PAPER, 1).setDisplayName(main.getFileManager().getLine("items.voteMap.name")).setLores(main.getFileManager().getStringList("items.voteMap.description")).build();
			else
				voteMap = new ItemCreator(Material.PAPER, 1).setDisplayName("§6Carte actuelle: " + main.getMapsLoaded().get(0).getWorld().getName()).setLores(main.getFileManager().getStringList("items.voteMap.description")).build();
				
			player.teleport(new Location(Bukkit.getWorld("world"), -7.5, 120, -7.5, -90.5f, 1.5f));
			Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "effect " + player.getName() + " clear");
			
			player.setLevel(0);
			
			player.getInventory().clear();
			player.getInventory().setItem(0, voteStart);
			player.getInventory().setItem(3, kitsSelector);
			player.getInventory().setItem(5, voteMap);
			
			player.setHealth(20.0);
			player.setFoodLevel(20);
			
			NMSUtils.sendTitle(player, main.getFileManager().getLine("messages.wait.title"), main.getFileManager().getLine("messages.wait.subtitle"), 25);
			
			Teams.setPlayerTeam(player, Teams.WAITPLAYERS);
			
			new SkyFallPlayer(player);
			
			for(Player pl : main.getGame().getPlayers()) {
				new Sounds(pl).playSound(Sound.BLOCK_NOTE_PLING);
				ScoreboardSign.updateScoreboard(pl);
				pl.showPlayer(player);
				player.showPlayer(pl);
			}
					
			final ScoreboardSign scoreboardSign = new ScoreboardSign(player, "§a§lSkyFall");
			scoreboardSign.create();
			scoreboardSign.setLine(1, "------------------");
			scoreboardSign.setLine(2, "§e");
			scoreboardSign.setLine(3, "§9Statut: En Attente");
			scoreboardSign.setLine(4, "§f");
			scoreboardSign.setLine(5, "§6Joueur(s) en jeu: §f" + main.getGame().getPlayers().size());
			scoreboardSign.setLine(6, "§a");
			scoreboardSign.setLine(7, "§4Durée: §fX");
			scoreboardSign.setLine(8, "§b");
			scoreboardSign.setLine(9, "§f------------------");
			main.getBoards().put(player, scoreboardSign);
			
			SkyFall.getInstance().getBossbar().addPlayer(player);
			player.setGameMode(GameMode.ADVENTURE);
			
			new BukkitRunnable() {
				
				Kit kit = SkyFallPlayer.getPlayerByUUID(player.getUniqueId()).getKit();
				
				@Override
				public void run() {
					
					kit = SkyFallPlayer.getPlayerByUUID(player.getUniqueId()).getKit();
					
					if(kit != null)
						NMSUtils.sendActionBar(player, main.getFileManager().getLine("messages.wait.kits.actionBarKit", player, kit, 0));
					else 
						NMSUtils.sendActionBar(player, main.getFileManager().getLine("messages.wait.kits.actionBarKitNull", player, null, 0));
						
					if(!States.isState(States.WAIT))
						this.cancel();
						
				}
			}.runTaskTimer(SkyFall.getInstance(), 0, 10);
				
		}
	}

}
