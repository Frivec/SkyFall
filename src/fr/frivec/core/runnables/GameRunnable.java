package fr.frivec.core.runnables;

import java.text.SimpleDateFormat;
import java.util.Map.Entry;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.frivec.SkyFall;
import fr.frivec.utils.Sounds;
import fr.frivec.utils.creators.NMSUtils;
import fr.frivec.utils.scoreboard.ScoreboardSign;

public class GameRunnable extends BukkitRunnable {
	
	private static GameRunnable instance;
	
	public static int timer = 0;
	private int apocalypseTimer;
	public static boolean start = false;
	
	private Random random;
	
	public GameRunnable() {
		instance = this;
		this.random = new Random();
		this.apocalypseTimer = random.nextInt(120) + 60;
	}
	
	@Override
	public void run() {
		
		if(apocalypseTimer == 0 && SkyFall.getInstance().getGame().getMap().getIslandsDestroyes() < 2) {
			
			for(Player player : SkyFall.getInstance().getGame().getPlayers()) {
				NMSUtils.sendTitle(player, "§cApocalypse Time !", "", 25); 
				new Sounds(player).playSound(Sound.ENTITY_WITHER_SPAWN);
			}
			
			SkyFall.getInstance().getGame().getMap().getWorld().setTime(15000);
			SkyFall.getInstance().getGame().getMap().destroyIslands();
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(SkyFall.getInstance(), new Runnable() {
				
				@Override
				public void run() {
					
					SkyFall.getInstance().getGame().getMap().getWorld().setTime(6000);
					apocalypseTimer = random.nextInt(120) + 60;
					
				}
			}, 20*15);
		}
		
		for(Entry<Player, ScoreboardSign> entrys : SkyFall.getInstance().getBoards().entrySet()) 
			entrys.getValue().setLine(12, "§4Durée ➭ §f" + new SimpleDateFormat("mm:ss").format(GameRunnable.timer * 1000));
		
		timer++;
		apocalypseTimer--;
		
	}
	
	public static GameRunnable getInstance() {
		return instance;
	}
	
	public void stop() {
		start = false;
		this.cancel();
	}

}
