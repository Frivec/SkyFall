package fr.frivec.core.game;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.frivec.SkyFall;
import fr.frivec.core.SkyFallPlayer;

public enum States {
	
	WAIT(true),
	NODAMAGE(false),
	GAME(false),
	FINISH(false);
	
	private boolean canJoin;
	private static States currentState;
   
    States(boolean canJoin){
        this.canJoin = canJoin;
    }
   
    public boolean canJoin(){
        return canJoin;
    }
   
    public static void setState(States state){
        States.currentState = state;
        System.out.println("States has been changed ! " + States.getState());
    }
   
    public static boolean isState(States state){
        return States.currentState == state;
    }
   
    public static States getState(){
        return currentState;
    }
    
    public static void checkEnd() {
    	
    	if(States.isState(States.GAME)) {
    		
    		if(SkyFall.getInstance().getGame().getPlayers().size() == 1) {
    			
    			States.setState(States.FINISH);
    			
    			final Player winner = SkyFall.getInstance().getGame().getPlayers().get(0);
    			final List<String> kickMessage = new ArrayList<>();
    			
    			kickMessage.add("§c---------------");
    			kickMessage.add("§bMerci d'avoir joué en §aSkyFall §b!");
    			kickMessage.add("§3A bientôt !");
    			kickMessage.add("§c---------------");
    			
    			SkyFallPlayer.getPlayerByUUID(winner.getUniqueId()).setWinner(true);
    			Bukkit.broadcastMessage(SkyFall.getInstance().getFileManager().getLine("messages.finish.winMessage", winner, null, 0));
    			
    			//TODO Fireworks
    			
    			Bukkit.getScheduler().scheduleSyncDelayedTask(SkyFall.getInstance(), new Runnable() {
					
					@Override
					public void run() {
						
						for(Player player : Bukkit.getOnlinePlayers()) {
							
							final StatsData stats = SkyFallPlayer.getPlayerByUUID(player.getUniqueId()).getStats();
							
							if(stats != null) {
								player.sendMessage("§a§m------------------------------");
								player.sendMessage("§6Fin de partie. Statistiques:");
								player.sendMessage("§bTués: " + stats.getKills());
								player.sendMessage("§eGains: " + stats.getCoinsWon());
								player.sendMessage("");
								player.sendMessage("§aMerci d'avoir joué !");
								player.sendMessage("§a§m------------------------------");
							}
							
						}
						
						SkyFall.getInstance().getServer().shutdown();
						
					}
				}, 20*5);
    			
    			Bukkit.getScheduler().scheduleSyncDelayedTask(SkyFall.getInstance(), new Runnable() {
					
					@Override
					public void run() {
						
						for(Player player : SkyFall.getInstance().getGame().getPlayers())
							player.kickPlayer(String.join("\n", kickMessage));
						
						Bukkit.getServer().shutdown();
						
					}
				}, 20*10);
    			
    		}else if(SkyFall.getInstance().getGame().getPlayers().size() == 0) {
    			
    			final List<String> kickMessage = new ArrayList<>();
    			
    			kickMessage.add("§c---------------");
    			kickMessage.add("§bMerci d'avoir joué en §aSkyFall §b!");
    			kickMessage.add("§3A bientôt !");
    			kickMessage.add("§c---------------");
    			
    			Bukkit.broadcastMessage(SkyFall.getInstance().getFileManager().getLine("messages.finish.noPlayers"));
    			
    			Bukkit.getScheduler().scheduleSyncDelayedTask(SkyFall.getInstance(), new Runnable() {
					
					@Override
					public void run() {
						
						for(Player player : Bukkit.getOnlinePlayers()) {
							
							player.kickPlayer(String.join("\n", kickMessage));
							
						}
						
						SkyFall.getInstance().getServer().shutdown();
						
					}
				}, 20*2);
    			
    		}
    		
    	}
    	
    }
 
}
